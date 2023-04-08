package hr.fer.oprpp1.custom.collections;

/**
 * Interface {@code List} represents a general list collection.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public interface List<T> extends Collection<T> {
	
	/**
	 * Returns the object that is stored in backing array at position {@code index}.
	 * 
	 * @param index the position of the object we want to retrieve.
	 * @return the object that exists at the given position {@code index} in the backing array.
	 * 
	 * @throws IndexOutOfBoundsException when the index is not between 0 and {@code size}-1.
	 */
	T get(int index);
	
	/**
	 * Inserts the given {@code value} at the given {@code position} in the backing array.
	 * 
	 * @param value object which is to be added to the collection.
	 * @param position the index at which the object needs to be placed.
	 * 
	 * @throws NullPointerException when the provided value is {@code null}.
	 * @throws IndexOutOfBoundsException when the provided {@code position} is not between 0 and {@code size}-1.
	 */
	void insert(T value, int position);
	
	/**
	 * Searches the collection for the provided {@code value} by using the {@code equals()} method.
	 * 
	 * @param value the object that needs to be found.
	 * @return index of the first occurrence of the provided value, -1 if {@code value} doesn't exist
	 * in the collection or if the provided value was {@code null}.
	 */
	int indexOf(Object value);
	
	/**
	 * Removes the element at the provided index.
	 * 
	 * @param index the index of the object that needs to be removed from the collection.
	 * @throws IndexOutOfBoundsException when the provided {@code index} is not between 0 and {@code size}-1.
	 */
	void remove(int index);
}
