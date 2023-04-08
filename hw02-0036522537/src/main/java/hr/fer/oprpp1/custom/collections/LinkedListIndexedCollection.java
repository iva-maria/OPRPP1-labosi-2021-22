package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * The {@code LinkedListIndexedCollection} class represents a linked list-backed collection of objects. Duplicate
 * elements are allowed; storage of {@code null} references is not allowed.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class LinkedListIndexedCollection implements List {
	
	private static class ListNode {
		
		/**
		 * Pointer to the previous list node.
		 */
		private ListNode previous;
		
		/**
		 * Pointer to the next list node.
		 */
		private ListNode next;
		
		/**
		 * Reference for {@code value} storage.
		 */
		private Object value;
		
		/**
		 * The default constructor which sets all variables to {@code null}.
		 */
		private ListNode() {
			this.previous = null;
			this.next = null;
			this.value = null;
		}
		
		/**
		 * Constructor which sets the {@code value} variable to the provided value.
		 * 
		 * @param value the value that the {@code value} variable is to be set to.
		 */
		private ListNode(Object value) {
			this.value = value;
		}
	}
	
	/**
	 * Current size of the collection, determined as the number of nodes in the list.
	 */
	private int size;
	
	/**
	 * Reference to the first node of the linked list.
	 */
	private ListNode first;
	
	/**
	 * Reference to the last node of the linked list.
	 */
	private ListNode last;
	
	/**
	 * Represents the number of modifications made on the current collection.
	 */
	private long modificationCount = 0;
	
	/**
	 * Default constructor. Creates an empty instance of the {@code LinkedListIndexedCollection} class.
	 */
	public LinkedListIndexedCollection() {
		this.first = null;
		this.last = null;
		this.size = 0;
	}
	
	/**
	 * Constructor that creates a new instance of the {@code LinkedListIndexedCollection} class and copies all
	 * elements from the given class into the newly created collection.
	 * 
	 * @param other the collection from which the elements are to be copied to the newly created collection.
	 */
	public LinkedListIndexedCollection(Collection other) {
		this.addAll(other);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * Adds the given object into the collection at the end of the collection (at the largest index).
	 * 
	 * @param value object that is to be added to the collection.
	 * @throws NullPointerException when the provided object is {@code null}.
	 */
	public void add(Object value) {
		if(value == null) throw new NullPointerException("The provided value cannot be null.");
		
		ListNode newNode = new ListNode(value);
		if(this.first == null) {
			this.first = newNode;
			this.last = newNode;
		} else {
			this.last.next = newNode;
			newNode.previous = this.last;
			this.last = newNode;
		}
		
		this.size++;
		this.modificationCount++;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(Object value) {
		if(this.indexOf(value) != -1) return true;
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Object get(int index) {
		if(index >= this.size || index < 0) throw new IndexOutOfBoundsException("The provided index is not between 0 and size}-1.");
		
		ListNode currentNode;
		if(index <= (this.size - 1) / 2) {
			currentNode = this.first;
			for(int i = 0; i < index; i++) {
				currentNode = currentNode.next;
			}
		} else {
			currentNode = this.last;
			for(int i = this.size - 1; i > index; i--) {
				currentNode = currentNode.previous;
			}
		}
		
		return currentNode;
	}
	
	/**
	 * Retrieves the node that is stored in the linked list at the position {@code index}.
	 * 
	 * @param index the index of the node that needs to be retrieved.
	 * @return the list node at the required position {@code index}.
	 * @throws IndexOutOfBoundsException when the provided {@code index} is not between 0 and {@code size}-1.
	 */
	private ListNode getNode(int index) {
		if(index >= this.size || index < 0) throw new IndexOutOfBoundsException("The provided index is not between 0 and size}-1.");
		
		ListNode currentNode;
		if(index <= (this.size - 1) / 2) {
			currentNode = this.first;
			for(int i = 0; i < index; i++) {
				currentNode = currentNode.next;
			}
		} else {
			currentNode = this.last;
			for(int i = this.size - 1; i > index; i--) {
				currentNode = currentNode.previous;
			}
		}
		
		return currentNode;
	}
	
	/**
	 * Inserts the provided {@code value} at the provided {@code position} as a new node in the linked list.
	 * 
	 * @param value the object that needs to be added to the collection.
	 * @param position the index at which the object needs to be added.
	 * @throws IndexOutOfBoundsException when the provided position is not between 0 and {@code size}.
	 */
	public void insert(Object value, int position) {
		if(value == null) throw new NullPointerException("The provided value cannot be null.");
		if(position > size || position < 0) throw new IndexOutOfBoundsException("The provided position is not between 0 and size.");
		
		ListNode newNode = new ListNode(value);
		if(this.first == null) {
			this.first = newNode;
			this.last = newNode;			
		} else if(position == 0) {
			newNode.next = this.first;
			this.first.previous = newNode;
			this.first = newNode;
		} else if(position == size) {
			this.last.next = newNode;
			newNode.previous = this.last;
			this.last = newNode;
		} else {
			ListNode currentNode = this.getNode(position);
			currentNode.previous.next = newNode;
			newNode.previous = currentNode.previous;
			currentNode.previous = newNode;
			newNode.next = currentNode;
		}
		
		this.size++;
		this.modificationCount++;
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
	 * Removes the element at the provided index by changing the references of the neighboring nodes.
	 * 
	 * @param index the index of the object that needs to be removed from the collection.
	 * @throws IndexOutOfBoundsException when the provided {@code index} is not between 0 and {@code size}-1.
	 */
	public void remove(int index) {
		if(index >= this.size || index < 0) throw new IndexOutOfBoundsException("The provided index is not between 0 and size-1.");
		
		ListNode nodeToRemove = new ListNode();
		if(this.first != null && this.last != null && this.first == this.last) {
			this.first = null;
			this.last = null;
		} else if(index == 0 && this.first != null) {
			nodeToRemove = this.first;
			this.first.next.previous = null;
			this.first = this.first.next;
		} else if(index == this.size - 1 && this.last != null) {
			nodeToRemove = this.last;
			this.last.previous.next = null;
			this.last = this.last.previous;
		} else {
			nodeToRemove = this.getNode(index);
			nodeToRemove.previous.next = nodeToRemove.next;
			nodeToRemove.next.previous = nodeToRemove.previous;
		}
		nodeToRemove.previous = null;
		nodeToRemove.next = null;
		nodeToRemove.value = null;
		
		this.size--;
		this.modificationCount++;
	}
	
	/**
	 * Removes all elements from the collection by "forgetting" about the current linked list.
	 */
	public void clear() {
		this.first = null;
		this.last = null;
		this.size = 0;
		
		this.modificationCount++;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int indexOf(Object value) {
		if(value == null) return -1;
		
		ListNode currentNode = this.first;
		for(int i = 0; i < size && currentNode != null; i++) {
			if(currentNode.value.equals(value)) return i;
			currentNode = currentNode.next;
		}
		
		return -1;
	}
	
	/**
	 * The {@code LinkedListElementsGetter} class represents an object whose purpose is to fetch elements
	 * of the {@code LinkedListIndexedCollection} instance, one by one, on demand.
	 * 
	 * @author Iva Maria Ivanković
	 * @version 1.0
	 */
	private static class LinkedListElementsGetter implements ElementsGetter {
		
		/**
		 * Backing collection whose elements are to be fetched.
		 */
		private LinkedListIndexedCollection linkedListCollection;
		
		/**
		 * Index of the first non-fetched node.
		 */
		private ListNode nextNonFetchedNode;
		
		/**
		 * The number of modifications made on the backing collection at the moment of instancing the {@code LinkedListElementsGetter}.
		 */
		private long savedModificationCount;
		
		/**
		 * Creates an instance of the {@code LinkedListElementsGetter} class for the provided collection and sets
		 * the {@code nextNonFetchedNode} to its default value.
		 * 
		 * @param collection the collection whose elements are to be fetched using the {@code LinkedListElementsGetter} class.
		 * @throws NullPointerException when the provided collection is {@code null}.
		 */
		public LinkedListElementsGetter(LinkedListIndexedCollection collection) {
			if(collection == null) throw new NullPointerException("The provided collection cannot be null.");
			this.linkedListCollection = collection;
			this.nextNonFetchedNode = linkedListCollection.first;
			this.savedModificationCount = collection.modificationCount;
		}
		
		/**
		 * {@inheritDoc}
		 * 
		 * @return
		 */
		@Override
		public boolean hasNextElement() {
			if(this.savedModificationCount != this.linkedListCollection.modificationCount) throw new ConcurrentModificationException("The collection has been modified.");
			return this.nextNonFetchedNode != null;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object getNextElement() {
			if(this.savedModificationCount != this.linkedListCollection.modificationCount) throw new ConcurrentModificationException("The collection has been modified.");
			if(!this.hasNextElement()) throw new NoSuchElementException("There are no elements left to fetch.");
			Object nextNode = this.nextNonFetchedNode.value;
			this.nextNonFetchedNode = this.nextNonFetchedNode.next;
			return nextNode;
		}
	}
	
	/**
	 * Creates a new instance of {@code ElementsGetter} class in order to fetch elements of the backing array of the {@code LinkedListIndexedCollection}.
	 * 
	 * @return instance of {@code ElementsGetter} class for use in the context of the current {@code LinkedListIndexedCollection}.
	 */
	@Override
	public ElementsGetter createElementsGetter() {
		return new LinkedListElementsGetter(this);
	}
		
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] toArray() {
		Object[] newArray = new Object[this.size];
		
		ListNode currentNode = this.first;
		for(int i = 0; i < this.size; i++) {
			newArray[i] = currentNode.value;
			currentNode = currentNode.next;
		}
		
		return newArray;
	}
	
	/**
	 * Check whether the provided object is the same as the current collection, i.e. if it has the same value of {@code size}
	 * and the same {@code elements} array.
	 * 
	 * @param obj the object for which we want to determine equality with the current collection.
	 * @return {@code true} if the provided object is equal to the current collection, {@code false} otherwise.
	 */
	public boolean equals(Object obj) {
		if(!(obj instanceof LinkedListIndexedCollection)) return false;
		LinkedListIndexedCollection other = (LinkedListIndexedCollection) obj;
		return this.size == other.size 
				&& Objects.equals(this.first, other.first)
				&& Objects.equals(this.last, other.last);
	}
	
	/**
	 * Calculates the hash code for the current collection.
	 * 
	 * @return the hash code value for the current collection.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.size, this.first, this.last);
	}
	
}
