package hr.fer.oprpp1.custom.scripting.parser;

public class SmartScriptParserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new instance of {@code SmartScriptParserException} class.
	 */
	public SmartScriptParserException() {
		super();
	}
	 /**
	  * Creates a new instance of {@code SmartScriptParserException} class
	  * with a message to display.
	  * 
	  * @param msg a message to display when the exception is thrown.
	  */
	public SmartScriptParserException(String msg) {
		super(msg);
	}

}
