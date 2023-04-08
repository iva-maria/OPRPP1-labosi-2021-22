package hr.fer.oprpp1.hw06.math;

import java.util.Arrays;

/**
 * Class {@code ComplexRootedPolynomial} represents a read-only implementation 
 * of a complex polynomial model in the form f(z)=z0*(z-z1)*(z-z2)*...*(z-zn).
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class ComplexRootedPolynomial {
	
	/**
	 * Constant z0 of the polynomial.
	 */
	private final Complex constant;
	
	/**
	 * Roots z1, ..., zn of the polynomial.
	 */
	private final Complex[] roots;
	
	/**
	 * Creates a new {@code ComplexRootedPolynomial} instance and initializes
	 * its {@code constant} and {@code roots} to the provided values.
	 * 
	 * @param constant constant of the polynomial.
	 * @param roots array of polynomial roots.
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant = constant;
		this.roots = Arrays.copyOf(roots, roots.length);
	}
	
	/**
	 * Computes the value of the polynomial at the provided point {@code z}.
	 * 
	 * @param z the point at which the value of the polynomial is to be calculated.
	 * @return the value of the polynomial at the provided point.
	 */
	public Complex apply(Complex z) {
		Complex product = this.constant;
		for(Complex root : roots) product = product.multiply(z.sub(root));
		
		return product;
	}
	
	/**
	 * Converts this polynomial representation to the {@code ComplexPolynomial} type.
	 * 
	 * @return {@code ComplexPolynomial} representation of the current instance.
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial polynom = new ComplexPolynomial(this.constant);
		
		for(Complex root : roots) polynom = polynom.multiply(new ComplexPolynomial(root.negate(), Complex.ONE));
		
		return polynom;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("(").append(constant.toString()).append(")");
		for(Complex root : roots) sb.append("*(z-(").append(root.toString()).append("))");

		return sb.toString();
	}
	
	/**
	 * Finds index of the closest root for given complex number {@code z}
	 * that is within the provided {@code treshold}.
	 * 
	 * @param z complex number for which the roots are determined.
	 * @param treshold maximum distance allowed for a certain root from {@code z.}
	 * @return index of the closest root of {@code z} that is within {@code treshold};
	 * {@code -1} if no such root exists.
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index = 0;
		double distance = z.sub(roots[0]).module();
		
		for(int i = 1, rootsLength = this.roots.length; i < rootsLength; i++) {
			double newDistance = z.sub(roots[i]).module();
			if(newDistance < distance) {
				distance = newDistance;
				index = i;
			}
		}
		
		if(distance < treshold) return index;
		return -1;
	}

}
