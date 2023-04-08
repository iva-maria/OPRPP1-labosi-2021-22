package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Enumeration {@code SmartScriptTokenType} defines all types of tokens 
 * that are allowed in the {@code SmartScriptLexer}.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public enum SmartScriptTokenType {
	
	/**
	 * No more characters left to tokenize.
	 */
	EOF,
	
	/**
	 * Beginning of a tag token.
	 */
	TAG_BEGINNING,
	
	/**
	 * Ending of a tag token.
	 */
	TAG_END,
	
	/**
	 * Identifier token.
	 */
	VARIABLE,
	
	/**
	 * Constant int token.
	 */
	INTEGER,
	
	/**
	 * Constant double token.
	 */
	DOUBLE,
	
	/**
	 * String in TEXT lexer state token.
	 */
	STRING_TEXT,
	
	/**
	 * String in TAG lexer state token.
	 */
	STRING_TAG,
	
	/**
	 * Function token.
	 */
	FUNCTION,
	
	/**
	 * Operator token.
	 */
	OPERATOR
}
