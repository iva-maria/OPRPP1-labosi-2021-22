package hr.fer.oprpp1.custom.collections;

/**
 * The {@code ElementsGetter} interface represents an object whose purpose is to fetch elements
 * of the collection, one by one, on demand.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public interface ElementsGetter<T> {

	/**
	 * Determines whether there are more elements to fetch from the current collection.
	 * 
	 * @return {@code true} if there are 1 or more elements left to fetch, {@code false} otherwise.
	 */
	boolean hasNextElement();
	
	/**
	 * Fetches the next element from the current collection.
	 * 
	 * @return the first object that is not fetched yet.
	 * @throws NoSuchElementException when there are no more elements left to be fetched.
	 */
	T getNextElement();
	
	/**
	 * Calls {@code Processor.process()} for each of the remaining non-fetched elements of the current collection.
	 * 
	 * @param processor instance of the {@code Processor} class which performs the {@code process()} method
	 * on every remaining object in the current collection.
	 */
	default void processRemaining(Processor<? super T> p) {
		while(this.hasNextElement()) {
			p.process(this.getNextElement());
		}
	}
}
