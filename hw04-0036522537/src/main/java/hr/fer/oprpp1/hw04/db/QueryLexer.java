package hr.fer.oprpp1.hw04.db;

/**
 * Class {@code QueryLexer} represents a lexer implementation
 * for creating tokens of queries for the student database.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class QueryLexer {
	
	/**
	 * The input text.
	 */
	private char[] data;
	
	/**
	 * Most recently created token.
	 */
	private QueryToken token;
	
	/**
	 * Index of the first character to be tokenized.
	 */
	private int currentIndex;
	
	/**
	 * Logical operator used to connect conditional expressions in the query.
	 */
	private static final String LOGICAL_OPERATOR = "AND";
	
	/**
	 * Constructor which adds all characters of the provided
	 * query to the {@code data} array.
	 * 
	 * @param query
	 */
	public QueryLexer(String query) {
		this.data = query.toCharArray();
	}
	
	/**
	 * Fetches the most recently created token.
	 * 
	 * @return
	 */
	public QueryToken getToken() {
		return token;
	}
	
	/**
	 * Creates a new token and returns it.
	 * 
	 * @return new token in the query.
	 */
	public QueryToken nextToken() {
		skipWhiteSpaces();
		createToken();

		return this.token;
	}
	
	/**
	 * Creates a new token.
	 */
	public void createToken() {
		if(currentIndex >= data.length) {
			createEOFToken();
		} else if(isOperator()) {
			createComparisonOperatorToken();
		} else if(Character.isLetter(data[currentIndex])) {
			createFieldToken();
		} else if(data[currentIndex] == '"') {
			createStringLiteralToken();
		} else {
			throw new QueryLexerException("Cannot create token.");
		}
	}
	
	/**
	 * Creates an EOF type token.
	 */
	public void createEOFToken() {
		token = new QueryToken(QueryTokenType.EOF, null);
	}
	
	/**
	 * Creates an attribute token.
	 */
	public void createFieldToken() {
		StringBuilder sb = new StringBuilder();
		
		while(Character.isLetter(data[currentIndex])) {
			sb.append(data[currentIndex]);
			currentIndex++;
		}
		
		if(sb.toString().equalsIgnoreCase("AND")) {
			token = new QueryToken(QueryTokenType.LOGICAL_OPERATOR, LOGICAL_OPERATOR);
		} else {
			token = new QueryToken(QueryTokenType.FIELD_VALUE, sb.toString());
		}
	}
	
	/**
	 * Creates an operator type token.
	 */
	public void createComparisonOperatorToken() {
		if(data[currentIndex] == '<' || data[currentIndex] == '>') {
			if(data[currentIndex + 1] == '=') {
				token = new QueryToken(QueryTokenType.COMPARISON_OPERATOR, String.valueOf(data[currentIndex] + data[currentIndex + 1]));
				currentIndex +=2;
			} else {
				token = new QueryToken(QueryTokenType.COMPARISON_OPERATOR, String.valueOf(data[currentIndex]));
				currentIndex++;
			}
		} else if(data[currentIndex] == '=') {
			token = new QueryToken(QueryTokenType.COMPARISON_OPERATOR, String.valueOf(data[currentIndex]));
			currentIndex++;
		} else if(data[currentIndex] == '!' && data[currentIndex] == '=') {
			token = new QueryToken(QueryTokenType.COMPARISON_OPERATOR, String.valueOf(data[currentIndex] + data[currentIndex + 1]));
			currentIndex += 2;
		} else {
			token = new QueryToken(QueryTokenType.COMPARISON_OPERATOR, "LIKE");
			currentIndex += 4;
		}
	}
	
	/**
	 * Creates a string token.
	 */
	public void createStringLiteralToken() {
		StringBuilder sb = new StringBuilder();
		
		currentIndex++;
		while(data[currentIndex] != '"') {
			sb.append(data[currentIndex]);
			currentIndex++;
		}
		currentIndex++;
		
		token = new QueryToken(QueryTokenType.STRING_LITERAL, sb.toString());
	}
	
	/**
	 * Skips all whitespaces in the {@code data} array
	 * until it gets to a non-whitespace character.
	 */
	public void skipWhiteSpaces() {
		for(int i = currentIndex, dataLength = this.data.length; i < dataLength; i++, this.currentIndex++) {
			if(i == dataLength || !this.isWhitespace(this.data[i])) break;
		}
	}
	
	/**
	 * Checks whether the provided character is a whitespace.
	 * 
	 * @param c character to be checked.
	 * @return {@code true} if the provided character is neither {@code ' '}, {@code '\n'}, {@code '\r'} nor {@code '\t'}, {@code false} otherwise.
	 */
	public boolean isWhitespace(char c) {
		if(c == ' ' || c == '\n' || c == '\r' || c == '\t') return true;
		return false;
	}
	
	/**
	 * Checks whether the current character is one of the comparison
	 * operators supported by the database.
	 * 
	 * @return {@code true} if the current character is an operator, {@code false} otherwise.
	 */
	public boolean isOperator() {
		if(data[currentIndex] == '<'
				|| data[currentIndex] == '<' && data[currentIndex + 1] == '='
				|| data[currentIndex] == '>'
				|| data[currentIndex] == '>' && data[currentIndex + 1] == '='
				|| data[currentIndex] == '='
				|| data[currentIndex] == '!' && data[currentIndex + 1] == '='
				|| String.valueOf(data[currentIndex]).equalsIgnoreCase("L")
					&& String.valueOf(data[currentIndex + 1]).equalsIgnoreCase("I")
					&& String.valueOf(data[currentIndex + 2]).equalsIgnoreCase("K")
					&& String.valueOf(data[currentIndex + 3]).equalsIgnoreCase("E")
				) return true;
		return false;
	}
	
}
