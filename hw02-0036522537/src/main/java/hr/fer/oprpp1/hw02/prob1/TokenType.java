package hr.fer.oprpp1.hw02.prob1;

/**
 * Enumeration {@code TokenType} defines all types of tokens that are allowed.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public enum TokenType {
	/**
	 * No more characters that can be tokenized.
	 */
	EOF,
	/**
	 * A string of letters and/or other characters that are escaped.
	 */
	WORD,
	/**
	 * A sequence of digits.
	 */
	NUMBER,
	/**
	 * A sequence of non-alphanumeric characters.
	 */
	SYMBOL
}
