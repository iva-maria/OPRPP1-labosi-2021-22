package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;

/**
 * The {@code ArrayIndexedCollection} class represents an implementation of a resizable array-backed
 * collection of objects. Existence of duplicate elements is allowed; storage of {@code null} references
 * is not allowed.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class ArrayIndexedCollection extends Collection {
	
	/**
	 * An array of references to objects stored in the current collection.
	 */
	private Object[] elements;
	
	/**
	 * Current size of the collection, determined as the number of elements actually stored in the
	 * {@code elements} array. Can be lower than or equal to the length of that array.
	 */
	private int size;
	
	/**
	 * The default capacity of the {@code elements} array. It is used when there is no argument provided
	 * while creating an instance of the {@code ArrayIndexedCollection} class.
	 */
	static final int DEFAULT_CAPACITY = 16;
	
	/**
	 * The factor that the capacity of the reallocated array will be multiplied by to make room for more objects.
	 */
	static final int RESIZE_COEFFICIENT = 2;
	
	/**
	 * Default constructor. Creates an instance of the {@code ArrayIndexedCollection} class with the capacity
	 * of its {@code elements} array set to the default value.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * Creates an instance of the {@code ArrayIndexedCollection} class with the capacity of its {@code elements}
	 * array set to the given value.
	 * 
	 * @param initialCapacity number that the capacity of the {@code elements} array is to be set to.
	 * @throws IllegalArgumentException when the provided capacity is less than 1.
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) throw new IllegalArgumentException("The initial capacity cannot be less than 1.");
		this.elements = new Object[initialCapacity];
		this.size = 0;
	}
	
	/**
	 * Creates an instance of the {@code ArrayIndexedCollection} class and copies all elements from the given 
	 * collection to the current collection. If the size of the given collection is greater than the value of
	 * the {@code DEFAULT_SIZE} parameter, the size of the current collection is reset to the size of the given
	 * collection.  
	 * 
	 * @param other an instance of the {@code ArrayIndexedCollection} class from which the objects are to be
	 * copied into the current collection.
	 * 
	 * @throws NullPointerException when the given collection is {@code null}.
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, DEFAULT_CAPACITY);
	}
	
	/**
	 * Creates an instance of the {@code ArrayIndexedCollection} class and copies all elements from the given 
	 * collection to the current collection. If the size of the given collection is greater than the value of
	 * the {@code initialCapacity} argument, the size of the current collection is reset to the size of the given
	 * collection. 
	 * 
	 * @param other an instance of the {@code ArrayIndexedCollection} class from which the objects are to be
	 * copied into the current collection.
	 * @param initialCapacity number which the capacity of the {@code elements} array of the current collection
	 * is to be set to.
	 * 
	 * @throws NullPointerException when the given collection is {@code null}.
	 * @throws IllegalArgumentException when the provided capacity is less than 1.
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		if(other == null) throw new NullPointerException("The given collection cannot be null.");
		if (initialCapacity < 1) throw new IllegalArgumentException("The initial capacity cannot be less than 1.");
		
		if(other.size() > initialCapacity) {
			this.elements = new Object[other.size()];
		} else {
			this.elements = new Object[initialCapacity];
		}
		
		this.addAll(other);
		this.size = other.size();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * Adds the given object into the collection. The reference to the object is added into the first empty place in
	 * the {@code elements} array. If the {@code elements} array is full, it gets reallocated by doubling its size.
	 * 
	 * @param value object which is to be added to the collection.
	 * 
	 * @throws NullPointerException when the provided object is null.
	 */
	@Override
	public void add(Object value) {
		if(value == null) throw new NullPointerException("The provided value cannot be null.");
		
		if(this.elements.length <= this.size) {
			this.elements = Arrays.copyOf(this.elements, this.elements.length * RESIZE_COEFFICIENT);
		}
		
		this.elements[size++] = value;
	}
		
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(Object value) {
		if(this.indexOf(value) == -1) return false;
		return true;
	}
	
	/**
	 * Returns the object that is stored in backing array at position {@code index}.
	 * 
	 * @param index the position of the object we want to retrieve.
	 * @return the object that exists at the given position {@code index} in the backing array.
	 * 
	 * @throws IndexOutOfBoundsException when the index is not between 0 and {@code size}-1.
	 */
	public Object get(int index) {
		if(index >= this.size || index < 0) throw new IndexOutOfBoundsException("There is no object at that position.");
		return this.elements[index];
	}
	
	/**
	 * Inserts the given {@code value} at the given {@code position} in the backing array by shifting the elements
	 * at indexes larger than {@code position} one place towards the end.
	 * 
	 * @param value object which is to be added to the collection.
	 * @param position the index at which the object needs to be placed.
	 * 
	 * @throws NullPointerException when the provided value is {@code null}.
	 * @throws IndexOutOfBoundsException when the provided {@code position} is not between 0 and {@code size}-1.
	 */
	public void insert(Object value, int position) {
		if(value == null) throw new NullPointerException("The provided value cannot be null.");
		if(position >= this.size || position < 0) throw new IndexOutOfBoundsException("The object cannot be placed to that position.");
		
		if(this.elements.length <= this.size) {
			this.elements = Arrays.copyOf(this.elements, this.elements.length * 2);
		}
		
		for(int i = this.size; i > position; i--) {
			this.elements[i] = this.elements[i - 1];
		}
		this.elements[position] = value;
		this.size++;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean remove(Object value) {
		if(this.indexOf(value) == -1) return false;
		
		this.remove(this.indexOf(value));
		return true;
	}
	
	/**
	 * Removes the element at the provided index by shifting the elements that came after it one place closer to the
	 * beginning of the list.
	 * 
	 * @param index the index of the object that needs to be removed from the collection.
	 * @throws IndexOutOfBoundsException when the provided {@code index} is not between 0 and {@code size}-1.
	 */
	public void remove(int index) {
		if(index >= this.size || index < 0) throw new IndexOutOfBoundsException("The provided index is not between 0 and size-1.");
		
		for(int i = index; i < this.size; i++) {
			this.elements[i] = this.elements[i + 1];
		}
		this.size--;
		this.elements[size] = null;
	}	
	
	public void clear() {
		for(int i = 0; i < this.size; i++) {
			this.elements[i] = null;
		}
		this.size = 0;
	}
	
	/**
	 * Searches the collection for the provided {@code value} by using the {@code equals()} method.
	 * 
	 * @param value the object that needs to be found.
	 * @return index of the first occurrence of the provided value, -1 if {@code value} doesn't exist
	 * in the collection or if the provided value was {@code null}.
	 */
	public int indexOf(Object value) {
		for(int i = 0; i < this.size; i++) {
			if(this.elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(this.elements, this.size);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void forEach(Processor processor) {
		for(int i = 0; i < this.size; i++) {
			processor.process(this.elements[i]);
		}
	}
	
}
