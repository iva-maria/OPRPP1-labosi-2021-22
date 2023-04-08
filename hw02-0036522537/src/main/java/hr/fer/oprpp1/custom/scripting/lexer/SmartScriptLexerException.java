package hr.fer.oprpp1.custom.scripting.lexer;

public class SmartScriptLexerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new instance of {@code SmartScriptParserException} class.
	 */
	public SmartScriptLexerException() {
		super();
	}
	 /**
	  * Creates a new instance of {@code SmartScriptParserException} class
	  * with a message to display.
	  * 
	  * @param msg a message to display when the exception is thrown.
	  */
	public SmartScriptLexerException(String msg) {
		super(msg);
	}
}
