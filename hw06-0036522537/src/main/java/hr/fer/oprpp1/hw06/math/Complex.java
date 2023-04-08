package hr.fer.oprpp1.hw06.math;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.plugins.tiff.ExifParentTIFFTagSet;
import javax.swing.plaf.basic.BasicBorders.SplitPaneBorder;
import javax.xml.parsers.SAXParser;

/**
 * Class {@code Complex} represents a read-only implementation of a complex number.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class Complex {
	
	/**
	 * Complex number 0.
	 */
	public static final Complex ZERO = new Complex(0,0);
	
	/**
	 * Complex number 1.
	 */
	public static final Complex ONE = new Complex(1,0);
	
	/**
	 * Complex number -1.
	 */
	public static final Complex ONE_NEG = new Complex(-1,0);
	
	/**
	 * Complex number i.
	 */
	public static final Complex IM = new Complex(0,1);
	
	/**
	 * Complex number -i.
	 */
	public static final Complex IM_NEG = new Complex(0,-1);
	
	/**
	 * The real part of the complex number.
	 */
	private final double re;
	
	/**
	 * The imaginary part of the complex number.
	 */
	private final double im;

	
	/**
	 * Default constructor which sets the real and imaginary parts to 0.
	 */
	public Complex() {
		this(0, 0);
	}
	
	/**
	 * Constructor which sets the real and imaginary parts to the provided values.
	 * 
	 * @param re real part of the complex number.
	 * @param im imaginary part of the complex number.
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	/**
	 * Calculates the module of current complex number, determined as a 
	 * distance of that number from the center of a polar coordinate system.
	 * 
	 * @return module of current complex number.
	 */
	public double module() {
		return Math.sqrt(Math.pow(this.re, 2)+ Math.pow(this.im, 2));
	}
	
	/**
	 * Multiplies current complex number with a provided complex number.
	 * 
	 * @param c the provided multiplier.
	 * @return result of multiplication of current complex number and the
	 * provided complex number. 
	 */
	public Complex multiply(Complex c) {
		return new Complex(this.re * c.re - this.im * c.im, this.re * c.im + this.im * c.re);
	}
	
	/**
	 * Divides current complex number by a provided complex number.
	 * 
	 * @param c the provided divisor.
	 * @return result of division of current complex number and the
	 * provided complex number.
	 */
	public Complex divide(Complex c) {
		double denominator = Math.pow(c.re, 2) + Math.pow(c.im, 2);
		if(denominator == 0) throw new ArithmeticException("You cannot divide by zero.");
		
		return new Complex((this.re * c.re + this.im * c.im) / denominator,
						(this.re * c.im + this.im * c.re) / denominator);
	}
	
	/**
	 * Adds the provided complex number to the current complex number.
	 * 
	 * @param c the provided complex number.
	 * @return the result of addition of the provided complex number to 
	 * the current complex number.
	 */
	public Complex add(Complex c) {
		return new Complex(this.re + c.re, this.im + c.im);
	}
	
	/**
	 * Subtracts the provided complex number from the current complex number.
	 * 
	 * @param c the provided complex number.
	 * @return the result of subraction of the provided complex number from
	 * the current complex number.
	 */
	public Complex sub(Complex c) {
		return new Complex(this.re - c.re, this.im - c.im);
	}
	
	/**
	 * Negates the value of the current complex number.
	 * 
	 * @return negation of the current complex number.
	 */
	public Complex negate() {
		return new Complex(-this.re, -this.im);
	}
	
	/**
	 * Raises the current complex number to the power
	 * of the provided integer.
	 * 
	 * @param n the power that the current complex number
	 * is to be raised to.
	 * @return result of raising the current complex number
	 * to the provided power.
	 */
	public Complex power(int n) {
		if(n < 0) throw new IllegalArgumentException("Exponent should be a non-negative integer.");
		
		return new Complex(Math.pow(this.module(), n) * Math.cos(n * Math.atan2(this.im, this.re)), 
						Math.pow(this.module(), n) * Math.sin(n * Math.atan2(this.im, this.re)) );
	}
	
	/**
	 * Calculates {@code n} n-th roots of the current complex number
	 * using De Moivre's formula.
	 * 
	 * @param n number and degree of roots that need to be calculated.
	 * @return {@code n} n-th roots of the current complex number.
	 */
	public List<Complex> root(int n) {
		if(n < 1) throw new IllegalArgumentException("Exponent should be a positive integer.");
		
		List<Complex> roots = new ArrayList<>();
		for(int i = 0; i < n; i++) {
			roots.add(new Complex(Math.pow(this.module(), 1.0 / n) * Math.cos((Math.atan2(this.im, this.re) + 2 * Math.PI * i) / n),
					Math.pow(this.module(), 1.0 / n) * Math.sin((Math.atan2(this.im, this.re) + 2 * Math.PI * i) / n)));
		}
		
		return roots;
	}
	
	@Override
	public String toString() {		
		StringBuilder sb = new StringBuilder();	
		sb.append(String.format("%.1f", this.re));
		
		if(this.im >= 0) {
			sb.append("+");
		} else {
			sb.append("-");
		}
		sb.append("i").append(String.format("%.1f", this.im >= 0 ? this.im : -this.im));
		
		return sb.toString();
	}
	
	public static Complex parse(String s) {
		
        String regexRealAndImaginary = "^(([-+])?(\\d+|\\d*\\.?\\d+))\\s*(([-+])\\s*i(\\d*\\.?\\d*))$";
        String regexReal = "^([-+])?(\\d+|\\d*\\.?\\d+)$";
        String regexImaginary = "^([-+])?i(\\d*\\.?\\d*)$";

        if (s.matches(regexRealAndImaginary)) {
            s = s.replaceAll("\\s", "");

            if(s.equalsIgnoreCase("0+i0") || s.equalsIgnoreCase("0-i0")
            		|| s.equalsIgnoreCase("+0+i0") || s.equalsIgnoreCase("+0-i0")
            		|| s.equalsIgnoreCase("-0+i0") || s.equalsIgnoreCase("-0-i0")) {
            	return Complex.ZERO;
            } else if(s.equalsIgnoreCase("1+i0") || s.equalsIgnoreCase("+1+i0")
            		|| s.equalsIgnoreCase("1-i0") || s.equalsIgnoreCase("+1-i0")) {
            	return Complex.ONE;
            } else if(s.equalsIgnoreCase("-1+i0") || s.equalsIgnoreCase("-1-i0")) {
            	return Complex.ONE_NEG;
            } else if(s.equalsIgnoreCase("0+i") || s.equalsIgnoreCase("0+i1")
            		|| s.equalsIgnoreCase("+0+i") || s.equalsIgnoreCase("+0+i1")
            		|| s.equalsIgnoreCase("-0+i") || s.equalsIgnoreCase("-0+i1")) {
            	return Complex.IM;
            } else if(s.equalsIgnoreCase("0-i") || s.equalsIgnoreCase("0-i1")
            		|| s.equalsIgnoreCase("+0-i") || s.equalsIgnoreCase("+0-i1")
            		|| s.equalsIgnoreCase("-0-i") || s.equalsIgnoreCase("-0-i1")) {
            	return Complex.IM_NEG;
            } else {
                boolean negativeReal = false;
                if (s.indexOf("+") == 0) {
                    s = s.substring(1);
                }
                if (s.indexOf("-") == 0) {
                    s = s.substring(1);
                    negativeReal = true;
                }

                int operatorIndex;
                if (s.contains("+")) {
                    operatorIndex = s.indexOf("+");
                } else {
                    operatorIndex = s.indexOf("-");
                }

                double real = !negativeReal ? Double.parseDouble(s.substring(0, operatorIndex)) : Double.parseDouble("-" + s.substring(0, operatorIndex));

                s = s.replaceFirst("i", "");
                double imaginary = s.substring(operatorIndex).equals("+") ? 1 : s.substring(operatorIndex).equals("-") ? -1 : Double.parseDouble(s.substring(operatorIndex));

                return new Complex(real, imaginary);
            }
        } else if (s.matches(regexReal)) {
        	if(s.equalsIgnoreCase("0") || s.equalsIgnoreCase("+0") || s.equalsIgnoreCase("-0")) {
        		return Complex.ZERO;
        	} else if(s.equalsIgnoreCase("1") || s.equalsIgnoreCase("+1")) {
        		return Complex.ONE;
        	} else if(s.equalsIgnoreCase("-1")) {
        		return Complex.ONE_NEG;
        	} else {
        		return new Complex(Double.parseDouble(s), 0);
        	}
        } else if (s.matches(regexImaginary)) {
        	if(s.equalsIgnoreCase("i0") || s.equalsIgnoreCase("+i0") || s.equalsIgnoreCase("-i0")) {
        		return Complex.ZERO;
        	} else if(s.equalsIgnoreCase("i") || s.equalsIgnoreCase("+i")) {
        		return Complex.IM;
        	} else if(s.equalsIgnoreCase("-i")) {
        		return Complex.IM_NEG;
        	} else {
        		return new Complex(0., Double.parseDouble(s.replaceFirst("i", "")));
            }
        } else {
            throw new IllegalArgumentException("Invalid complex number format.");
        }
	}
	
}
