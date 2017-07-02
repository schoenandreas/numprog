
import java.util.Arrays;

/**
 * Die Klasse Newton-Polynom beschreibt die Newton-Interpolation. Die Klasse
 * bietet Methoden zur Erstellung und Auswertung eines Newton-Polynoms, welches
 * uebergebene Stuetzpunkte interpoliert.
 *
 * @author braeckle
 *
 */
public class NewtonPolynom implements InterpolationMethod {

    /**
     * Stuetzstellen xi
     */
    double[] x;

    /**
     * Koeffizienten/Gewichte des Newton Polynoms p(x) = a0 + a1*(x-x0) +
     * a2*(x-x0)*(x-x1)+...
     */
    double[] a;

    /**
     * die Diagonalen des Dreiecksschemas. Diese dividierten Differenzen werden
     * fuer die Erweiterung der Stuetzstellen benoetigt.
     */
    double[] f;

    /**
     * leerer Konstruktor
     */
    public NewtonPolynom() {
    }

    ;

	/**
	 * Konstruktor
	 * 
	 * @param x
	 *            Stuetzstellen
	 * @param y
	 *            Stuetzwerte
	 */
	public NewtonPolynom(double[] x, double[] y) {
        this.init(x, y);
    }

    /**
     * {@inheritDoc} Zusaetzlich werden die Koeffizienten fuer das
     * Newton-Polynom berechnet.
     */
    @Override
    public void init(double a, double b, int n, double[] y) {
        x = new double[n + 1];
        double h = (b - a) / n;

        for (int i = 0; i < n + 1; i++) {
            x[i] = a + i * h;
        }
        computeCoefficients(y);
    }

    /**
     * Initialisierung der Newtoninterpolation mit beliebigen Stuetzstellen. Die
     * Faelle "x und y sind unterschiedlich lang" oder "eines der beiden Arrays
     * ist leer" werden nicht beachtet.
     *
     * @param x Stuetzstellen
     * @param y Stuetzwerte
     */
    public void init(double[] x, double[] y) {
        this.x = Arrays.copyOf(x, x.length);
        computeCoefficients(y);
    }

    /**
     * computeCoefficients belegt die Membervariablen a und f. Sie berechnet zu
     * uebergebenen Stuetzwerten y, mit Hilfe des Dreiecksschemas der
     * Newtoninterpolation, die Koeffizienten a_i des Newton-Polynoms. Die
     * Berechnung des Dreiecksschemas soll dabei lokal in nur einem Array der
     * Laenge n erfolgen (z.B. spaltenweise Berechnung). Am Ende steht die
     * Diagonale des Dreiecksschemas in der Membervariable f, also f[0],f[1],
     * ...,f[n] = [x0...x_n]f,[x1...x_n]f,...,[x_n]f. Diese koennen spaeter bei
     * der Erweiterung der Stuetzstellen verwendet werden.
     *
     * Es gilt immer: x und y sind gleich lang.
     */
    private void computeCoefficients(double[] y) {
        //n Stuetzstellen -> Polynom hat Grad n - 1
        int n = x.length;

        //Initialiserung von a und f
        this.a = new double[n];
        this.f = new double[n];

        //lokales Array
        double[] coeff = new double[n];

        //1. Spalte
        for (int i = 0; i < n; i++) {
            coeff[i] = y[i];
        }
        this.a[0] = coeff[0];
        this.f[0] = coeff[n - 1];

        //n - 1 Spalten
        for (int k = 1; k < n; k++) {

            for (int i = 0; i < n - k; i++) {
                double coeff_new;

                coeff_new = (coeff[i + 1] - coeff[i])
                        / (this.x[i + k] - this.x[i]);

                coeff[i] = coeff_new;
            }
            
            this.a[k] = coeff[0];
            this.f[k] = coeff[n - k];
        }

    }

    /**
     * Gibt die Koeffizienten des Newton-Polynoms a zurueck
     */
    public double[] getCoefficients() {
        return a;
    }

    /**
     * Gibt die Dividierten Differenzen der Diagonalen des Dreiecksschemas f
     * zurueck
     */
    public double[] getDividedDifferences() {
        return f;
    }

    /**
     * addSamplintPoint fuegt einen weiteren Stuetzpunkt (x_new, y_new) zu x
     * hinzu. Daher werden die Membervariablen x, a und f vergoessert und
     * aktualisiert . Das gesamte Dreiecksschema muss dazu nicht neu aufgebaut
     * werden, da man den neuen Punkt unten anhaengen und das alte
     * Dreiecksschema erweitern kann. Fuer diese Erweiterungen ist nur die
     * Kenntnis der Stuetzstellen und der Diagonalen des Schemas, bzw. der
     * Koeffizienten noetig. Ist x_new schon als Stuetzstelle vorhanden, werden
     * die Stuetzstellen nicht erweitert.
     *
     * @param x_new neue Stuetzstelle
     * @param y_new neuer Stuetzwert
     */
    public void addSamplingPoint(double x_new, double y_new) {
        //Array erweitern
        this.x = Arrays.copyOf(x, x.length + 1);
        this.a = Arrays.copyOf(a, a.length + 1);
        this.f = Arrays.copyOf(f, f.length + 1);

        int n = x.length;

        this.x[n - 1] = x_new;

        double coeff = y_new;

        for (int k = 1; k < n; k++) {

            double coeff_new = (coeff - this.f[k - 1])
                    / (x_new - this.x[n - k - 1]);

            coeff = this.f[k];

            this.f[k] = coeff_new;
        }
        this.a[n - 1] = this.f[n - 1];
    }

    /**
     * {@inheritDoc} Das Newton-Polynom soll effizient mit einer Vorgehensweise
     * aehnlich dem Horner-Schema ausgewertet werden. Es wird davon ausgegangen,
     * dass die Stuetzstellen nicht leer sind.
     */
    @Override
    public double evaluate(double z) {
        int n = x.length;

        //gemaess der Horner-Schema-aenlichen Formel
        double faktor = this.a[n - 1];
        double summand = this.a[n - 2];

        for (int i = 1; i < n; i++) {
            faktor = summand + ((z - this.x[n - i]) * faktor);
        }

        double result = faktor;

        return result;
    }

}
