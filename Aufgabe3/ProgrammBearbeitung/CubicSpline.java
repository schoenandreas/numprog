
import java.util.Arrays;

/**
 * Die Klasse CubicSpline bietet eine Implementierung der kubischen Splines. Sie
 * dient uns zur effizienten Interpolation von aequidistanten Stuetzpunkten.
 *
 * @author braeckle
 *
 */
public class CubicSpline implements InterpolationMethod {

    /**
     * linke und rechte Intervallgrenze x[0] bzw. x[n]
     */
    double a, b;

    /**
     * Anzahl an Intervallen
     */
    int n;

    /**
     * Intervallbreite
     */
    double h;

    /**
     * Stuetzwerte an den aequidistanten Stuetzstellen
     */
    double[] y;

    /**
     * zu berechnende Ableitunge an den Stuetzstellen
     */
    double yprime[];

    /**
     * {@inheritDoc} Zusaetzlich werden die Ableitungen der stueckweisen
     * Polynome an den Stuetzstellen berechnet. Als Randbedingungen setzten wir
     * die Ableitungen an den Stellen x[0] und x[n] = 0.
     */
    @Override
    public void init(double a, double b, int n, double[] y) {
        this.a = a;
        this.b = b;
        this.n = n;
        h = ((double) b - a) / (n);

        this.y = Arrays.copyOf(y, n + 1);

        /* Randbedingungen setzten */
        yprime = new double[n + 1];
        yprime[0] = 0;
        yprime[n] = 0;

        /* Ableitungen berechnen. Nur noetig, wenn n > 1 */
        if (n > 1) {
            computeDerivatives();
        }
    }

    /**
     * getDerivatives gibt die Ableitungen yprime zurueck
     */
    public double[] getDerivatives() {
        return yprime;
    }

    /**
     * Setzt die Ableitungen an den Raendern x[0] und x[n] neu auf yprime0 bzw.
     * yprimen. Anschliessend werden alle Ableitungen aktualisiert.
     */
    public void setBoundaryConditions(double yprime0, double yprimen) {
        yprime[0] = yprime0;
        yprime[n] = yprimen;
        if (n > 1) {
            computeDerivatives();
        }
    }

    /**
     * Berechnet die Ableitungen der stueckweisen kubischen Polynome an den
     * einzelnen Stuetzstellen. Dazu wird ein lineares System Ax=c mit einer
     * Tridiagonalen Matrix A und der rechten Seite c aufgebaut und geloest.
     * Anschliessend sind die berechneten Ableitungen y1' bis yn-1' in der
     * Membervariable yprime gespeichert.
     *
     * Zum Zeitpunkt des Aufrufs stehen die Randbedingungen in yprime[0] und
     * yprime[n]. Speziell bei den "kleinen" Faellen mit Intervallzahlen n = 2
     * oder 3 muss auf die Struktur des Gleichungssystems geachtet werden. Der
     * Fall n = 1 wird hier nicht beachtet, da dann keine weiteren Ableitungen
     * berechnet werden muessen.
     */
    public void computeDerivatives() {
        /* 
         *n Intervalle
         *n + 1 Stützstellen
         *die Aeussersten wurde in init() initialisiert (yprime0 und yprimeN)
         *m ist daher n - 1
         */
        int m = this.n - 1;
        
        //Initialisierung der Tridiagonalen Matrix mit 1,4,1
        double[] diagonal = new double[m];
        double[] upperAndLower = new double[m];

        Arrays.fill(diagonal, 4.0);
        Arrays.fill(upperAndLower, 1.0);
        TridiagonalMatrix triMatrix
                = new TridiagonalMatrix(upperAndLower, diagonal, upperAndLower);
        
        double faktor = 3 / this.h;
        
        //Initialisierung b des Gleichungssystems triMatrix * result = b
        double[] b = new double[m];
        
        //Grenzfaelle
        b[0] = faktor * this.y[2] - faktor * this.y[0] - this.yprime[0];
        b[n - 2] = faktor * this.y[this.n] - faktor * this.y[n - 2] - this.yprime[n];
        
        //m - 2 verbleibende Ableitungen
        for (int i = 1; i < m - 1; i++){
            b[i] = this.y[i + 2] - this.y[i];
        }
        
        //Resultat berechnen und yprime kopieren (yprime1 bis yprimeN-1
        double[] result = triMatrix.solveLinearSystem(b);
        
        for (int i = 0; i < result.length; i++){
            this.yprime[i + 1] = result[i];
        }
    }

    /**
     * {@inheritDoc} Liegt z ausserhalb der Stuetzgrenzen, werden die
     * aeussersten Werte y[0] bzw. y[n] zurueckgegeben. Liegt z zwischen den
     * Stuetzstellen x_i und x_i+1, wird z in das Intervall [0,1] transformiert
     * und das entsprechende kubische Hermite-Polynom ausgewertet.
     */
    @Override
    public double evaluate(double z) {
        /* TODO: diese Methode ist zu implementieren */
        return 0.0;
    }
}
