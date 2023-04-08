package hr.fer.oprpp1.custom.collections;

/**
 * The {@code ObjectStack} class represents an implementation of a stack-like collection. 
 * It serves as an adaptor in the Adapter design pattern.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class ObjectStack {

	/**
	 * The collection which server as an adaptee in the Adapter design pattern.
	 */
	private ArrayIndexedCollection adapteeCollection;
	
	/**
	 * Default constructor.
	 */
	public ObjectStack() {
		this.adapteeCollection = new ArrayIndexedCollection();
	}
	
	/**
	 * Determines whether the backing adaptee collection is empty.
	 * 
	 * @return {@code true} if the collection contains no objects, {@code false} otherwise.
	 */
	public boolean isEmpty() {
		return this.adapteeCollection.isEmpty();
	}
	
	/**
	 * Determines the size of the backing adaptee collection.
	 * 
	 * @return the number of objects in the adaptee collection.
	 */
	public int size() {
		return this.adapteeCollection.size();
	}
	
	/**
	 * Adds the provided {@code value} to the end of the adaptee collection, i. e. "pushes" it onto the top of the stack.
	 * 
	 * @param value the value which is to be pushed onto the stack.
	 */
	public void push(Object value) {
		this.adapteeCollection.add(value);
	}
	
	/**
	 * Removes last value pushed onto the stack and returns its value.
	 * 
	 * @return the object which was last put onto the top of the stack.
	 * @throws EmptyStackException when there is nothing to pop from the stack because the stack is empty.
	 */
	public Object pop() {
		if(this.isEmpty()) throw new EmptyStackException("The stack is empty. There is nothing to pop.");
		
		Object popped = this.peek();
		this.adapteeCollection.remove(this.size() - 1);
		return popped;
	}
	
	/**
	 * Retrieves last element that was put on the top of the stack. 
	 * 
	 * @return the object which was last put onto the top of the stack.
	 * @throws EmptyStackException when the stack is empty.
	 */
	public Object peek() {
		if(this.isEmpty()) throw new EmptyStackException("The stack is empty.");
		
		return this.adapteeCollection.get(this.size() - 1);
	}
	
	/**
	 * Removes all objects from the stack.
	 */
	public void clear() {
		this.adapteeCollection.clear();
	}
	
}
