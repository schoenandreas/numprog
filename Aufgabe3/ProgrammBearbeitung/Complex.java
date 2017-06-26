package dft;

/**
 * Klasse zum Darstellen komplexer Zahlen
 *
 * @author Sebastian Rettenberger
 */
public class Complex {
	/** Realteil der Zahl */
	private double real;
	/** Imaginaerteil der Zahl */
	private double imaginary;

	public Complex() {
		this(0, 0);
	}

	public Complex(double real) {
		this(real, 0);
	}

	public Complex(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Addiert zwei komplexe Zahlen.
	 *
	 * @return "this + other"
	 */
	public Complex add(Complex other) {
		double real;
                double imag;
                
                real = this.getReal() + other.getReal();
                imag = this.getImaginaer() + other.getImaginaer();
                
		return new Complex(real, imag);
	}

	/**
	 * Substrahiert zwei komplexe Zahlen
	 *
	 * @return "this - other"
	 */
	public Complex sub(Complex other) {
		double real;
                double imag;
                
                real = this.getReal() - other.getReal();
                imag = this.getImaginaer() - other.getImaginaer();
		return new Complex(real, imag);
	}

	/**
	 * Multipliziert zwei komplexe Zahlen
	 *
	 * @return "this * other"
	 */
	public Complex mul(Complex other) {
		double real;
                double imag;
                
                real = (this.getReal() * other.getReal())
                        - (this.getImaginaer() * other.getImaginaer());
                imag = (this.getReal() * other.getImaginaer())
                        + (this.getImaginaer() * other.getReal());
                
		return new Complex(real, imag);
	}

	/**
	 * Potzenziert die Zahl mit einem ganzzahligen Exponenten
	 *
	 * @return "this ^ n"
	 */
	public Complex power(int n) {
		return Complex.fromPolar(Math.pow(getRadius(), n), n * getPhi());
	}

	/**
	 * Gibt die komplex konjugierte Zahl zurueck
	 */
	public Complex conjugate() {
		return new Complex(real, -imaginary);
	}

	/**
	 * @return String representation of the number
	 */
	public String toString() {
		return real + "+" + imaginary + "i";
	}

	/**
	 * Gibt den Realteil der Zahl zurueck
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Gibt den Imaginaerteil der Zahl zurueck
	 */
	public double getImaginaer() {
		return imaginary;
	}

	/**
	 * Berechnet den Radius der Zahl (fuer Polarkoordinaten)
	 */
	public double getRadius() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * Berechnet den Winkel der Zahl (fuer Polarkoordinaten)
	 */
	public double getPhi() {
		if (real > 0)
			return Math.atan(imaginary / real);

		if (real < 0) {
			if (imaginary >= 0)
				return Math.atan(imaginary / real) + Math.PI;
			return Math.atan(imaginary / real) - Math.PI;
		}

		// real == 0
		if (imaginary > 0)
			return Math.PI / 2;

		return -Math.PI / 2;
	}

	/**
	 * Erstellt eine neue komplexe Zahl, gegeben durch den Radius r und den
	 * Winkel phi.
	 */
	public static Complex fromPolar(double r, double phi) {
		double real;
                double imag;
                
                real = r * Math.cos(phi);
                imag = r * Math.sin(phi);
            
		return new Complex();
	}
}