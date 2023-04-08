package hr.fer.oprpp1.hw02.prob1;

/**
 * Class {@code Lexer} represents an implementation of a simple lexical analyzer.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class Lexer {
	
	/**
	 * The input text.
	 */
	private char[] data;
	
	/**
	 * Current token.
	 */
	private Token token;
	
	/**
	 * Index of the first character that has not yet been "tokenized".
	 */
	private int currentIndex;
	
	/**
	 * The current state of the lexer.
	 */
	private LexerState state;
	
	/**
	 * Creates a new instance of the {@code Lexer} class and tokenizes the provided {@code text}.
	 * 
	 * @param text the text that needs to be tokenized.
	 * @throws NullPointerException when the provided {@code text} is {@code null}.
	 */
	public Lexer(String text) {
		if(text == null) throw new NullPointerException("The provided text cannot be null.");
		
		this.data = text.toCharArray();
		this.token = null;
		this.currentIndex = 0;
		this.state = LexerState.BASIC;
	}
	
	/**
	 * Generates new token, depending on which character is the first non-tokenized, and returns it.
	 * 
	 * @return newly generated {@code Token} instance.
	 * @throws LexerException
	 */
	public Token nextToken() {
		if(this.token != null && this.token.getType() == TokenType.EOF) throw new LexerException("No more tokens after EOF.");
		
		if(this.isEOF(this.currentIndex)) return this.token;
		this.skipWhitespaces();
		if(this.isEOF(this.currentIndex)) return this.token;
		
		if(this.state == LexerState.BASIC) {
			createBasicToken();
			return this.token;
		} else if(this.state == LexerState.EXTENDED) {
			createExtendedToken();
			return this.token;
		}
		
		return null;
	}
	
	/**
	 * Fetches the last generated token.
	 * 
	 * @return last generated token.
	 */
	public Token getToken() {
		return this.token;
	}
	
	/**
	 * Sets the working state of the current lexer.
	 * 
	 * @param state the state of the lexer, can be {@code BASIC} or {@code EXTENDED}.
	 * @throws NullPointerException when the provided state is {@code null}.
	 */
	public void setState(LexerState state) {
		if(state == null) throw new NullPointerException("Lexer state cannot be null.");
		this.state = state;
	}
	
	/**
	 * Creates a token in basic lexer state.
	 */
	private void createBasicToken() {
		if(this.isAlpha(this.currentIndex)) {
			createWordToken();
		} else if(this.isNumeric(this.currentIndex)) {
			createNumberToken();
		} else if(!(this.isAlpha(this.currentIndex) || this.isNumeric(this.currentIndex))) {
			createSymbolToken();
		}
	}
	
	/**
	 * Determines whether the current character is the end of the input.
	 * 
	 * @param index the index of the character that needs to be checked.
	 * @return {@code true} if the character is an EOF, {@code false} otherwise.
	 */
	private boolean isEOF(int index) {
		if(index < this.data.length) {
			return false;
		}
		this.token = new Token(TokenType.EOF, null);
		return true;
	}
	
	/**
	 * Determines whether the current character is a letter or an escaped symbol or digit.
	 * 
	 * @param index the index of the character that needs to be checked.
	 * @return {@code true} if the character is a letter or an escaped symbol or digit, {@code false} otherwise.
	 */
	private boolean isAlpha(int index) {
		if(Character.isLetter(this.data[index]) || this.data[this.currentIndex] == '\\') return true;
		return false;
	}
	
	/**
	 * Determines whether the current character is a non-escaped digit.
	 * 
	 * @param index the index of the character that needs to be checked.
	 * @return {@code true} if the character is a non-escaped digit, {@code false} otherwise.
	 */
	private boolean isNumeric(int index) {
		if(Character.isDigit(this.data[index])) return true;
		return false;
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
	
	/**
	 * Creates a word token.
	 */
	private void createWordToken() {
		StringBuilder sb = new StringBuilder();
		
		for(int i = currentIndex, dataLength = this.data.length; i < dataLength; i++, this.currentIndex++) {
			if(this.isWhitespace(this.data[i])) {
				break;
			} else if(this.data[i] == '\\' && this.isEOF(this.currentIndex + 1)) {
				throw new LexerException("Invalid escape sequence.");
			} else if(this.data[i] == '\\' && Character.isLetter(this.data[i+1])) {
				throw new LexerException("Invalid escape sequence.");
			} else if(Character.isDigit(this.data[i]) && this.data[i-1] != '\\') {
				this.currentIndex--;
				break;
			} else if(Character.isLetter(this.data[i])){
				sb.append(this.data[i]);
			} else if(this.data[i] == '\\' && !(this.isWhitespace(this.data[i+1]) || Character.isLetter(this.data[i+1]))) {
				sb.append(this.data[i+1]);
			}
		}
		
		this.currentIndex++;
		this.token = new Token(TokenType.WORD, sb.toString());
	}
	
	/**
	 * Creates a number token.
	 */
	private void createNumberToken() {
		StringBuilder sb = new StringBuilder();
		
		for(int i = currentIndex, dataLength = this.data.length; i < dataLength; i++, this.currentIndex++) {
			if(this.isWhitespace(this.data[i])) {
				break;
			} else if(Character.isLetter(this.data[i])) {
				break;
			} else if(Character.isDigit(this.data[i]) && !(Character.isDigit(this.data[i-1]))) {
				sb.append(this.data[i]);
			} else if(Character.isDigit(this.data[i]) && this.data[i-1] != '\\') {
				sb.append(this.data[i]);
			} else {
				this.currentIndex--;
			}
		}
		
		try {
			this.token = new Token(TokenType.NUMBER, Long.parseLong(sb.toString()));
		} catch(NumberFormatException e) {
			throw new LexerException("The number is too big.");
		}
	}
	
	/**
	 * Creates a symbol token.
	 */
	private void createSymbolToken() {

		if((this.data[this.currentIndex] == '\\' && this.isEOF(this.currentIndex + 1)) 
				|| (this.data[this.currentIndex] == '\\' && Character.isLetter(this.data[currentIndex+1]))) {
			throw new LexerException("Invalid escape sequence.");
		}
		
		this.token = new Token(TokenType.SYMBOL, Character.valueOf(this.data[this.currentIndex]));
		this.currentIndex++;
	}
	
	/**
	 * Creates a token in extended lexer state.
	 */
	private void createExtendedToken() {
		StringBuilder sb = new StringBuilder();
		
		for(int i = currentIndex, dataLength = this.data.length; i < dataLength; i++, this.currentIndex++) {
			if(this.isWhitespace(this.data[i])) {
				break;
			} else if(this.data[i] == '#') {
				this.setState(LexerState.BASIC);
				break;
			} else {
				sb.append(this.data[i]);
			}
		}
		
		this.token = new Token(TokenType.WORD, sb.toString());
	}
	
}
