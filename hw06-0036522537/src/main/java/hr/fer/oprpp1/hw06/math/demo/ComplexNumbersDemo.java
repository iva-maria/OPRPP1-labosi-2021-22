package hr.fer.oprpp1.hw06.math.demo;

import hr.fer.oprpp1.hw06.math.Complex;
import hr.fer.oprpp1.hw06.math.ComplexPolynomial;
import hr.fer.oprpp1.hw06.math.ComplexRootedPolynomial;

public class ComplexNumbersDemo {

	public static void main(String[] args) {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
				new Complex(2,0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
				);
		ComplexPolynomial cp = crp.toComplexPolynom();
		System.out.println(crp);
		System.out.println(cp);
		System.out.println(cp.derive());
	}

}
