package hr.fer.oprpp1.hw04.db;

/**
 * Class {@code QueryToken} represents a token to be used in parsing
 * queries for the student database.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class QueryToken {

	/**
	 * Type of the current token.
	 */
	private QueryTokenType type;
	
	/**
	 * Value of the current token.
	 */
	private String value;
	
	/**
	 * Constructor which sets the token {@code type} and {@code value}
	 * properties to the provided values.
	 * 
	 * @param type the type of the new token.
	 * @param value the value of the new token.
	 */
	public QueryToken(QueryTokenType type, String value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Fetches the type of the current token.
	 * 
	 * @return type of the current token.
	 */
	public QueryTokenType getType() {
		return type;
	}
	
	/**
	 * Fetches the value of the current token.
	 * 
	 * @return value of the current token.
	 */
	public String getValue() {
		return value;
	}
	
}
