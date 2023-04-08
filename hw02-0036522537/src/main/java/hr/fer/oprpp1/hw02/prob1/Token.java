package hr.fer.oprpp1.hw02.prob1;

/**
 * Class {@code Token} represents an instance of a token, a lexical unit which groups 
 * one or more symbols from the input text.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class Token {
	
	/**
	 * The type of the current token.
	 */
	private TokenType type;
	
	/**
	 * The value of the current token.
	 */
	private Object value;
	
	/**
	 * Creates a new {@code Token} instance and sets its {@code type} and {@code value} to the provided values.
	 * 
	 * @param type the provided type for the token.
	 * @param value the provided value for the token.
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Fetches the type of the current token and returns it.
	 * 
	 * @return the type of the current token.
	 */
	public TokenType getType() {
		return this.type;
	}
	
	/**
	 * Fetches the value of the current token and returns it.
	 * 
	 * @return the value of the current token.
	 */
	public Object getValue() {
		return this.value;
	}
	
}
