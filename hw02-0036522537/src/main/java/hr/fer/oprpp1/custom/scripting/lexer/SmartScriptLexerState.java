package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Enumeration {@code SmartScriptLexerState} defines the current working state of the lexer.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public enum SmartScriptLexerState {
	
	/**
	 * State of working outside tags.
	 */
	TEXT,
	
	/**
	 * State of working between tags.
	 */
	TAG
}
