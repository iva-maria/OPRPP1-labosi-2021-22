package hr.fer.oprpp1.hw04.db;

/**
 * Enumeration {@code QueryTokenType} is used for
 * representing different types of token which can
 * be created by the {@code QueryLexer}.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public enum QueryTokenType {
	
	/**
	 * Field value token.
	 */
	FIELD_VALUE,
	
	/**
	 * Operator token.
	 */
	COMPARISON_OPERATOR,
	
	/**
	 * String token.
	 */
	STRING_LITERAL,
	
	/**
	 * End-of-file token.
	 */
	EOF,
	
	/**
	 * Logical operator token.
	 */
	LOGICAL_OPERATOR
	
}
