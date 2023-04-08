package hr.fer.oprpp1.custom.collections;

/**
 * The {@code Processor} interface represents the model of an object capable of performing
 * some action on a passed object.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public interface Processor {
	
	/**
	 * Performs an operation on the passed object.
	 * 
	 * @param value object upon which the operation is to be performed
	 */
	void process(Object value);
	
}
