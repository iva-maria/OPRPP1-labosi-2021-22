package hr.fer.oprpp1.custom.collections;

/**
 * The {@code Tester} interface represents a model of an object which accepts some other object and
 * determines whether it is acceptable or not.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public interface Tester<T> {
	
	/**
	 * Determines whether the provided {@code object} is acceptable or not.
	 * 
	 * @param obj the object which needs to have its acceptance determined.
	 * @return {@code true} if the provided {@code object} is acceptable, {@code false} otherwise.
	 */
	boolean test(T obj);
	
}