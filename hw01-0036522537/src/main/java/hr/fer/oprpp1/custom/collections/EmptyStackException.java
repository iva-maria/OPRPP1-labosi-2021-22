package hr.fer.oprpp1.custom.collections;

/**
 * The {@code EmptyStackException} represents an instance of an exception which is thrown
 * when the user wishes to access - for example, pop - elements of an empty stack.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class EmptyStackException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public EmptyStackException() {
		super();
	}
	
	/**
	 * Constructor which creates an instance of the {@code EmptyStackException} class with a message to display.
	 * 
	 * @param message
	 */
	public EmptyStackException(String message) {
		super(message);
	}
}
