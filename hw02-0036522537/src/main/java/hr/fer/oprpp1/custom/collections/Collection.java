package hr.fer.oprpp1.custom.collections;

/**
 * Interface {@code Collection} represents a general collection of objects.
 * 
 * @author Iva Maria Ivanković 
 * @version 1.0
 */
public interface Collection {
	
	/**
	 * Determines whether the collection is empty with the help of {@code size()} method.
	 * 
	 * @return {@code true} if the collection contains no objects, {@code false} otherwise.
	 */
	default boolean isEmpty() {
		return this.size() == 0;
	}
	
	/**
	 * Determines the size of the collection.
	 * 
	 * @return the number of objects in the collection.
	 */
	int size();
	
	/**
	 * Adds a given object into the collection.
	 * 
	 * @param value object which is to be added to the collection.
	 */
	void add(Object value);
		
	/**
	 * Checks whether the collection contains given object.
	 * 
	 * @param value the object for which we want to know whether it is in the collection.
	 * @return {@code true} if given object exists in the collection, {@code false} otherwise.
	 */
	boolean contains(Object value);
	
	/**
	 * Checks whether the collection contains given object, as determined by {@code equals()} method,
	 * and removes it if it does.
	 * 
	 * @param value the object which we want to remove from the collection.
	 * @return {@code true} only if the collection had contained given value which was then
	 * removed from it, {@code false} otherwise.
	 */
	boolean remove(Object value);
	
	/**
	 * Allocates new array of equal size as current collection and fills it with current collection's 
	 * contents.
	 * 
	 * @return new array filled with contents of the current collection.
	 * @throws UnsupportedOperationException if the requested operation is not supported.
	 */
	Object[] toArray();
	
	/**
	 * Calls {@code Processor.process()} for each element of the current collection.
	 * 
	 * @param processor instance of the {@code Processor} class which performs the {@code process()} method
	 * on every object in the current collection.
	 */
	default void forEach(Processor processor) {
		ElementsGetter getter = this.createElementsGetter();
		getter.processRemaining(processor);
	}
	
	/**
	 * Adds all elements of the given collection to the current collection. The given collection remains
	 * unchanged.
	 * 
	 * @param other collection from which the elements will be added to the current collection.
	 */
	default void addAll(Collection other) {
		
		/**
		 * Local class {@code AddAllProcessor} is used to add all elements of the given collection to the
		 * current collection.
		 * 
		 * @author Iva Maria Ivanković
		 * @version 1.0
		 */
		class AddAllProcessor implements Processor {
			
			/**
			 * Adds a given element into the current collection by calling method {@code add()}.
			 * 
			 * @param value object that needs to be added to the collection.
			 * 
			 */
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		
		other.forEach(new AddAllProcessor());
	}
	
	/**
	 * Removes all elements from the current collection.
	 */
	void clear();
	
	/**
	 * Creates a new instance of {@code ElementsGetter} class in order to fetch elements of the backing array.
	 * 
	 * @return instance of {@code ElementsGetter} class for use in the context of the current collection.
	 */
	ElementsGetter createElementsGetter();
	
	default void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter getter = col.createElementsGetter();
		getter.processRemaining(value -> {
			if(tester.test(value)) {
				this.add(value);
			}
		});
	}
	
}
