package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Class {@code SmartScriptLexer} represents an implementation of a simple lexical analyzer.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class SmartScriptLexer {
	
	/**
	 * The input text.
	 */
	private char[] data;
	
	/**
	 * Current token.
	 */
	private SmartScriptToken token;
	
	/**
	 * Index of the first character that has not yet been "tokenized".
	 */
	private int currentIndex;
	
	/**
	 * The current state of the lexer.
	 */
	private SmartScriptLexerState state;
	
	/**
	 * Creates a new instance of the {@code SmartScriptLexer} class and tokenizes the provided {@code text}.
	 * 
	 * @param text the text that needs to be tokenized.
	 * @throws NullPointerException when the provided {@code text} is {@code null}.
	 */
	public SmartScriptLexer(String text) {
		if(text == null) throw new NullPointerException("The provided text cannot be null.");
		
		this.data = text.toCharArray();
		this.state = SmartScriptLexerState.TEXT;
	}
	
	/**
	 * Fetches the working state of the current lexer.
	 * 
	 * @return the working state of the current lexer.
	 */
	public SmartScriptLexerState getState() {
		return this.state;
	}
	
	/**
	 * Sets the working state of the current lexer.
	 * 
	 * @param state the state of the lexer, can be {@code TEXT} or {@code TAG}.
	 */
	public void setState(SmartScriptLexerState state) {
		if(state == null) throw new NullPointerException("The provided state cannot be null.");
		
		this.state = state;
	}
	
	/**
	 * Fetches the last generated token.
	 * 
	 * @return last generated token.
	 */
	public SmartScriptToken getToken() {
		return this.token;
	}
	
	/**
	 * Generates new token, depending on which character is the first non-tokenized, and returns it.
	 * 
	 * @return newly generated {@code SmartScriptToken} instance.
	 * @throws SmartScriptLexerException when the state of the lexer is invalid.
	 */
	public SmartScriptToken nextToken() {
		if(this.token != null) {
			if(this.token.getType() == SmartScriptTokenType.EOF) throw new SmartScriptLexerException("There are no more tokenizable characters after EOF.");
		}
		
		if(this.currentIndex >= this.data.length) {
			this.token = new SmartScriptToken(SmartScriptTokenType.EOF, null);
		} else if(this.state == SmartScriptLexerState.TEXT) {
			if(this.data[this.currentIndex] == '{' && this.data[this.currentIndex+1] == '$') {
				this.currentIndex += 2;
				this.token = new SmartScriptToken(SmartScriptTokenType.TAG_BEGINNING, "{$");
			} else {
				createTokenInTextState();
			}
		} else if(this.state == SmartScriptLexerState.TAG) {
			skipWhitespaces();
			if(!(this.data[this.currentIndex] == '$' && this.data[this.currentIndex] == '}')) {
				createTokenInTagState();
			}
		} else {
			throw new SmartScriptLexerException("Invalid state of the lexer.");
		}
		
		//System.out.println("TOKEN: " + this.token.getValue() + " TYPE: " + this.token.getType());
		return this.token;
	}
	
	/**
	 * Creates new token in TEXT working state.
	 */
	public void createTokenInTextState() {
		StringBuilder sb = new StringBuilder();

		int dataLength = this.data.length;
		while(this.currentIndex < dataLength && !(this.data[this.currentIndex] == '{' && this.data[this.currentIndex+1] == '$')) {
			if(this.data[this.currentIndex] == '\\') {
				if(this.data[this.currentIndex+1] == '\\' || this.data[this.currentIndex+1] == '{') {
					this.currentIndex++;
				} else {
					throw new SmartScriptLexerException("Illegal escaping in TEXT working state.");
				}
			}

			sb.append(this.data[this.currentIndex]);
			this.currentIndex++;
		}
		
		this.token = new SmartScriptToken(SmartScriptTokenType.STRING_TEXT, sb.toString());
	}
	
	/**
	 * Creates new token in TAG working state.
	 */
	public void createTokenInTagState() {
		if(this.isVariable(this.currentIndex)) {
			createVariableToken();
		} else if(this.isFunction(this.currentIndex)) {
			createFunctionToken();
		} else if(this.isNumeric(this.currentIndex)) {
			createNumberToken();
		} else if(this.isOperator(this.currentIndex)) {
			createOperatorToken();
		} else if(this.isStringBeginning(this.currentIndex)) {
			createStringToken();
		} else if(this.data[this.currentIndex] == '$' && this.data[this.currentIndex+1] == '}') {
			this.currentIndex += 2;
			this.token = new SmartScriptToken(SmartScriptTokenType.TAG_END, "$}");
		} else {
			throw new SmartScriptLexerException("Invalid syntax inside tags because of " + String.valueOf(this.data[this.currentIndex]) + " at position " + this.currentIndex);
		}
	}
	
	/**
	 * Checks if current character(s) should be interpreted as a variable identifier.
	 * 
	 * @param index the index of the character that needs to be checked.
	 * @return {@code true} if the character is a variable, {@code false} otherwise.
	 */
	public boolean isVariable(int index) {
		if(Character.isLetter(this.data[index]) || this.data[index] == '=') return true;
		return false;
	}
	
	/**
	 * Creates a variable token.
	 */
	public void createVariableToken() {
		if(this.data[this.currentIndex] == '=') {
			this.token = new SmartScriptToken(SmartScriptTokenType.VARIABLE, "=");
			this.currentIndex++;
		} else {
			StringBuilder sb = new StringBuilder();
			
			//ovo provjeriti!!!
			sb.append(this.data[this.currentIndex]);
			this.currentIndex++;

			while(Character.isLetter(this.data[this.currentIndex]) || Character.isDigit(this.data[this.currentIndex]) || this.data[this.currentIndex] == '_') {
				sb.append(this.data[this.currentIndex]);
				this.currentIndex++;
			}
			
			this.token = new SmartScriptToken(SmartScriptTokenType.VARIABLE, sb.toString());
		}
	}
	
	/**
	 * Checks if current character(s) should be interpreted as a function identifier.
	 * 
	 * @param index the index of the character that needs to be checked.
	 * @return {@code true} if the character is a function, {@code false} otherwise.
	 */
	public boolean isFunction(int index) {
		if(this.data[index] == '@' && Character.isLetter(this.data[index+1])) return true;
		return false;
	}
	
	/**
	 * Creates a function token.
	 */
	public void createFunctionToken() {
		StringBuilder sb = new StringBuilder();
		
		if(this.data[this.currentIndex] == '@') sb.append(this.data[this.currentIndex]);
		this.currentIndex++;
		
		while(Character.isLetter(this.data[this.currentIndex]) || Character.isDigit(this.data[this.currentIndex]) || this.data[this.currentIndex] == '_') {
			sb.append(this.data[this.currentIndex]);
			this.currentIndex++;
		}
		
		this.token = new SmartScriptToken(SmartScriptTokenType.FUNCTION, sb.toString());
	}
	
	/**
	 * Checks if current character(s) should be interpreted as a number.
	 * 
	 * @param index the index of the character that needs to be checked.
	 * @return {@code true} if the character is a number, {@code false} otherwise.
	 */
	public boolean isNumeric(int index) {
		if(Character.isDigit(this.data[index]) || (this.data[index] == '-' && Character.isDigit(this.data[index+1]))) return true;
		return false;
	}
	
	/**
	 * Creates a number token.
	 */
	public void createNumberToken() {
		boolean dotNotation = false;
		int noOfDots = 0;
		
		StringBuilder sb = new StringBuilder();
		
		while(Character.isDigit(this.data[this.currentIndex]) || this.data[this.currentIndex] == '-' || (this.data[currentIndex] == '.' && !dotNotation)) {
			if(this.data[this.currentIndex] == '.') {
				dotNotation = true;
				noOfDots++;
			}
			
			sb.append(this.data[this.currentIndex]);
			this.currentIndex++;
		}
		
		if(!dotNotation) {
			this.token = new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.parseInt(sb.toString()));
		} else if(noOfDots == 1) {
			this.token = new SmartScriptToken(SmartScriptTokenType.DOUBLE, Double.parseDouble(sb.toString()));
		} else {
			throw new SmartScriptLexerException("Invalid syntax for a number.");
		}
	}
	
	/**
	 * Checks if current character(s) should be interpreted as an operator identifier.
	 * 
	 * @param index the index of the character that needs to be checked.
	 * @return {@code true} if the character is a variable, {@code false} otherwise.
	 */
	public boolean isOperator(int index) {
		if(this.data[index] == '+' || this.data[index] == '-' || this.data[index] == '*' || this.data[index] == '/' || this.data[index] == '^') return true;
		return false;
	}
	
	/**
	 * Creates an operator token.
	 */
	public void createOperatorToken() {
		this.token = new SmartScriptToken(SmartScriptTokenType.OPERATOR, String.valueOf(this.data[this.currentIndex]));
		this.currentIndex++;
	}
	
	/**
	 * Checks if current character(s) should be interpreted as a string identifier.
	 * 
	 * @param index the index of the character that needs to be checked.
	 * @return {@code true} if the character is a variable, {@code false} otherwise.
	 */
	public boolean isStringBeginning(int index) {
		if(this.data[index] == '"') return true;
		return false;
	}
	
	/**
	 * Creates a string token.
	 */
	public void createStringToken() {
		if(this.data[this.currentIndex] == '"') this.currentIndex++;
		
		StringBuilder sb = new StringBuilder();
		int dataLength = this.data.length;
		
		while(this.currentIndex < dataLength && this.data[this.currentIndex] != '"') {
			while(this.data[this.currentIndex] == '\\') {
				if(this.data[this.currentIndex+1] == 'n') {
					sb.append('\n');
					this.currentIndex += 2;
				} else if(this.data[this.currentIndex+1] == 'r') {
					sb.append('\r');
					this.currentIndex += 2;
				} else if(this.data[this.currentIndex+1] == 't') {
					sb.append('\t');
					this.currentIndex += 2;
				} else {
					break;
				}
			}
			
			if(this.data[this.currentIndex] == '\\') {
				if(this.data[this.currentIndex+1] == '\\' || this.data[this.currentIndex+1] == '\"') {
					this.currentIndex++;
				} else {
					throw new SmartScriptLexerException("Invalid escaping.");
				} 
			}
			
			sb.append(this.data[this.currentIndex]);
			this.currentIndex++;
		}	
			
		if(this.data[this.currentIndex] == '"') this.currentIndex++;
		
		this.token = new SmartScriptToken(SmartScriptTokenType.STRING_TAG, sb.toString());
	}
	
	/**
	 * Ignores all whitespace characters while creating a token.
	 */
	private void skipWhitespaces() {
		for(int i = currentIndex, dataLength = this.data.length; i < dataLength; i++, this.currentIndex++) {
			if(i == dataLength || !this.isWhitespace(this.data[i])) break;
		}
	}
	
	/**
	 * Checks whether the character at the current position is a whitespace.
	 * 
	 * @param c the character that needs to be checked.
	 * @return {@code true} if the character is a whitespace, {@code false} otherwise.
	 */
	private boolean isWhitespace(char c) {
		if(c == ' ' || c == '\n' || c == '\r' || c == '\t') return true;
		return false;
	}
	
}
