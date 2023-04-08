package hr.fer.oprpp1.hw02.prob1;

/**
 * Enumeration {@code LexerState} defines the current working state of the lexer.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public enum LexerState {
	/**
	 * Standard lexer working state.
	 */
	BASIC,
	
	/**
	 * Extended lexer working state; treats all continuous symbol sequences as words; no escaping.
	 */
	EXTENDED
}
