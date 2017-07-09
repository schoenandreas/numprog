package ode;

import java.util.Arrays;

/**
 * Das Einschrittverfahren von Heun
 * 
 * @author braeckle
 * 
 */
public class Heun implements Einschrittverfahren {

	@Override
	/**
	 * {@inheritDoc} 
	 * Nutzen Sie dabei geschickt den Expliziten Euler.
	 */
	public double[] nextStep(double[] y_k, double t, double delta_t, ODE ode) {
		// TODO: diese Methode ist zu implementieren
		return Arrays.copyOf(y_k, y_k.length);
	}

}
