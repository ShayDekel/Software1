package il.ac.tau.cs.sw1.hw6;

import java.util.Arrays;

public class Polynomial {

	private double[] coefficients;
	
	/*
	 * Creates the zero-polynomial with p(x) = 0 for all x.
	 */
	public Polynomial() {
		coefficients = new double[] {0};
	}

	/*
	 * Creates a new polynomial with the given coefficients.
	 */
	public Polynomial(double[] coefficients) {
		this.coefficients = Arrays.copyOf(coefficients, coefficients.length);
	}

	/*
	 * Adds this polynomial to the given one
	 *  and returns the sum as a new polynomial.
	 */
	public Polynomial adds(Polynomial polynomial) {

		double[] newCoefficients = new double[Math.max(coefficients.length, polynomial.coefficients.length)];
		for (int i = 0; i < newCoefficients.length; i++) {
			if (i <= this.getDegree() && i <= polynomial.getDegree()) { // Check that both polynomial are not finished
				newCoefficients[i] = this.getCoefficient(i) + polynomial.getCoefficient(i);
			} else {
				if (i > this.getDegree()) { // If we finished this
					newCoefficients[i] = polynomial.getCoefficient(i);
				} else { // If we finished polynomial
					newCoefficients[i] = this.getCoefficient(i);
				}
			}
		}

		return new Polynomial(newCoefficients);
	}

	/*
	 * Multiplies a to this polynomial and returns 
	 * the result as a new polynomial.
	 */
	public Polynomial multiply(double a) {

		double[] newCoefficients = new double[coefficients.length];
		for (int i = 0; i < coefficients.length; i++) {
			newCoefficients[i] = coefficients[i] * a;
		}

		return new Polynomial(newCoefficients);

	}

	/*
	 * Returns the degree (the largest exponent) of this polynomial.
	 */
	public int getDegree() {

		return coefficients.length - 1;
	}

	/*
	 * Returns the coefficient of the variable x 
	 * with degree n in this polynomial.
	 */
	public double getCoefficient(int n) {

		return coefficients[n];
	}
	
	/*
	 * set the coefficient of the variable x 
	 * with degree n to c in this polynomial.
	 * If the degree of this polynomial < n, it means that that the coefficient of the variable x 
	 * with degree n was 0, and now it will change to c. 
	 */
	public void setCoefficient(int n, double c) {

		if (n > this.getDegree()) { // If degree of this polynomial < n
			double[] newCoefficients = new double[n + 1]; // Create new coefficients array with the correct new length
			for (int i = 0; i < coefficients.length; i++) {
				newCoefficients[i] = coefficients[i];
			}
			newCoefficients[n] = c;
		} else {
			coefficients[n] = c;
		}
	}
	
	/*
	 * Returns the first derivation of this polynomial.
	 *  The first derivation of a polynomal a0x0 + ...  + anxn is defined as 1 * a1x0 + ... + n anxn-1.
	
	 */
	public Polynomial getFirstDerivation() {

		double[] newCoefficients = new double[coefficients.length - 1];
		for (int i = 1; i < coefficients.length; i++) {
			newCoefficients[i - 1] = coefficients[i];
		}

		return new Polynomial(newCoefficients);
	}
	
	/*
	 * given an assignment for the variable x,
	 * compute the polynomial value
	 */
	public double computePolynomial(double x) {

		double result = 0;
		for (int i = 0; i < coefficients.length; i++) {
			result += coefficients[i] * Math.pow(x, i);
		}

		return result;
	}
	
	/*
	 * given an assignment for the variable x,
	 * return true iff x is an extrema point (local minimum or local maximum of this polynomial)
	 * x is an extrema point if and only if The value of first derivation of a polynomal at x is 0
	 * and the second derivation of a polynomal value at x is not 0.
	 */
	public boolean isExtrema(double x) {

		Polynomial firstDerivation = this.getFirstDerivation();
		Polynomial secondDerivation = firstDerivation.getFirstDerivation();

		return (firstDerivation.computePolynomial(x) == 0 && secondDerivation.computePolynomial(x) != 0);
	}
}
