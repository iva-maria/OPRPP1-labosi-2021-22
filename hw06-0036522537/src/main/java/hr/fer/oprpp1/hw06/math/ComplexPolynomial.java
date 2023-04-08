package hr.fer.oprpp1.hw06.math;

import java.util.Arrays;

/**
 * Class {@code ComplexPolynomial} represents a read-only implementation of a complex 
 * polynomial model in the form f(z)=zn*z^n + zn-1*z^(n-1) + ... + z2*z^2 + z1*z +z0.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class ComplexPolynomial {

	/**
	 * Coefficients of the polynomial.
	 */
	private final Complex[] factors;
	
	/**
	 * Constructor which creates a new instance and initializes
	 * its {@code roots} to the provided value.
	 * 
	 * @param factors array of complex polynomial coefficients.
	 */
	public ComplexPolynomial(Complex ...factors) {
		this.factors = Arrays.copyOf(factors, factors.length);
	}
	
	/**
	 * Determines the order of the current polynomial.
	 * 
	 * @return order of the current polynomial.
	 */
	public short order() {
		return (short)(this.factors.length - 1);
	}
	
	/**
	 * Multiplies the current polynomial with the provided polynomial.
	 * 
	 * @param p the multiplier.
	 * @return result of multiplication of the two polynomials.
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] newFactors = new Complex[this.order() + p.order() + 1];
		
		for(int i = 0, newFactorsLength = newFactors.length; i < newFactorsLength; i++) newFactors[i] = Complex.ZERO;
		
		for(int i = 0, thisLength = this.factors.length; i < thisLength; i++) {
			for(int j = 0, pLength = p.factors.length; j < pLength; j++) {
				newFactors[i + j] = newFactors[i + j].add(this.factors[i].multiply(p.factors[j]));
			}
		}
		
		return new ComplexPolynomial(newFactors);
	}
	
	/**
	 * Computes the first derivative of the current polynomial.
	 * 
	 * @return value of the first derivative of the current polynomial.
	 */
	public ComplexPolynomial derive() {
		Complex[] newFactors = new Complex[this.order()];
		
		for(int i = 1, thisLength = this.factors.length; i < thisLength; i++) {
			newFactors[i - 1] = this.factors[i].multiply(new Complex(i, 0));
		}
		
		return new ComplexPolynomial(newFactors);
	}
	
	/**
	 * Computes value of the polynomial expression for the provided value {@code z}.
	 * 
	 * @param z value for which the polynomial expression is to be calculated.
	 * @return value of the polynomial at the point {@code z}.
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;
		
		for(int i = 0, n = this.factors.length - 1; i < n; i++) {
			result = result.add(z.power(i).multiply(this.factors[i]));
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(int i = this.factors.length - 1; i > 0; i--) {
			sb.append("(").append(this.factors[i].toString()).append(")").append("*z^").append(i).append("+");
		}
		sb.append("(").append(this.factors[0].toString()).append(")");
		
		return sb.toString();
	}

}
