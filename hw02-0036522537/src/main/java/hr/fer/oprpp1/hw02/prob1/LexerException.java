package hr.fer.oprpp1.hw02.prob1;

/**
 * The {@code LexerException} represents an instance of an exception which is thrown
 * when the user wishes to do an illegal action while tokenizing.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class LexerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor which creates an instance of the {@code LexerException} with no message to display.
	 */
	public LexerException() {
		super();
	}
	
	/**
	 * Constructor which creates an instance of the {@code LexerException} class with a message to display.
	 * 
	 * @param message
	 */
	public LexerException(String message) {
		super(message);
	}
}
