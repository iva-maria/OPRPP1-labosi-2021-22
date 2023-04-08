package hr.fer.oprpp1.custom.collections;

import java.util.Objects;

/**
 * Class {@code Dictionary} represents a collection for storing key-value pairs of objects.
 * It serves as an adaptor in the Adapter pattern.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 *
 * @param <K> type of a key object in the dictionary.
 * @param <V> type of a value object in the dictionary.
 */
public class Dictionary<K, V> {
	
	/**
	 * The collection of elements represented as {@code ArrayIndexedCollection} which serves
	 * as an adaptee in the Adapter pattern. The key-value pairs are stored in this collection.
	 */
	private ArrayIndexedCollection<Entry<K, V>> entries;
	
	/**
	 * Default constructor.
	 */
	public Dictionary() {
		this.entries = new ArrayIndexedCollection<>();
	}
	
	/**
	 * Class {@code Entry<K, V>} is used for representing one 
	 * of the key-value pairs of objects in the dictionary.
	 * 
	 * @author Iva Maria Ivanković
	 * @version 1.0
	 *
	 * @param <K> type of the  {@code key} object in the entry.
	 * @param <V> type of the {@code value} object in the entry.
	 */
	private static class Entry<K, V> {
		
		/**
		 * The key of the current entry.
		 */
		private K key;
		
		/**
		 * The value of the current entry.
		 */
		private V value;
		
		/**
		 * Constructor which creates a new dictionary entry and sets the 
		 * {@code key} and {@code value} to the provided values.
		 * 
		 * @param key the key of the entry.
		 * @param value the value of the entry.
		 * @throws NullPointerException if the provided {@code key} is {@code null}.
		 */
		protected Entry(K key, V value) {
			if(key == null) throw new NullPointerException("The provided key must not be null.");
			
			this.key = key;
			this.value = value;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public int hashCode() {
			return Objects.hash(this.key, this.value);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean equals(Object obj) {
			if(this == obj) return true;
			if(!(obj instanceof Entry<?, ?>)) return false;
			
			Entry<?, ?> other = (Entry<?, ?>)obj;
			return Objects.equals(this.key, other.key) && Objects.equals(this.value, other.value);
		}
		
	}
	
	/**
	 * Determines whether the backing {@code ArrayIndexedCollection} is empty.
	 * 
	 * @return {@code true} if the array contains no entries, {@code false} otherwise.
	 */
	public boolean isEmpty() {
		return this.entries.isEmpty();
	}
	
	/**
	 * Determines the size of the dictionary, calculated as the size of the backing array.
	 * 
	 * @return the size of the current dictionary.
	 */
	public int size() {
		return this.entries.size();
	}
	
	/**
	 * Removes all elements from the current dictionary.
	 */
	public void clear() {
		this.entries.clear();
	}
	
	/**
	 * Inserts a pair consisted of the given {@code key} and {@code value} as a new entry
	 * in the current dictionary. If an entry for the provided {@code key} already exists
	 * within the dictionary, it overwrites the {@code value}.
	 * 
	 * @param key the key of the new entry.
	 * @param value the value of the new entry.
	 * @return oold value of the entry if it existed, {@code null} otherwise.
	 * @throws NullPointerException if the provided {@code key} is {@code null}.
	 */
	public V put(K key, V value) {
		if(key == null) throw new NullPointerException("The provided key must not be null.");
		
		Entry<K, V> oldEntry = this.getEntry(key);
		
		if(oldEntry == null) {
			Entry<K, V> newEntry = new Entry<>(key, value);
			this.entries.add(newEntry);
			return null;
		} else {
			V oldValue = oldEntry.value;
			oldEntry.value = value;
			return oldValue;
		}
	}
	
	/**
	 * Fetches the value of the entry with the provided {@code key}, if it exists. 
	 * 
	 * @param key the key of the entry.
	 * @return the value of the entry with the provided {@code key} if it exists, {@code null} otherwise.
	 * @throws NullPointerException if the provided {@code key} is {@code null}.
	 */
	public V get(Object key) {
		if(key == null) throw new NullPointerException("The provided key must not be null.");
		
		@SuppressWarnings("unchecked")
		Entry<K, V> entry = this.getEntry((K)key);
		
		if(entry == null) {
			return null;
		} else {
			return entry.value;
		}
		
	}
	
	/**
	 * Fetches the entry with the provided {@code key} if it exists in the dictionary.
	 * 
	 * @param key the key of the entry that is to be fetched.
	 * @return the entry with the provided {@code key} if it exists, {@code null} otherwise.
	 */
	public Entry<K, V> getEntry(K key) {
		ElementsGetter<Entry<K, V>> getter = this.entries.createElementsGetter();
		
		while(getter.hasNextElement()) {
			Entry<K, V> entry = getter.getNextElement();
			
			if(entry.key.equals(key)) return entry;
		}
		
		return null;
	}
	
	/**
	 * Removes the entry with the provided {@code key} from the dictionary.
	 * 
	 * @param key the key of the entry which is to be removed from the dictionary.
	 * @return the value of the removed entry if it existed in the dictionary, {@code null} otherwise.
	 * @throws NullPointerException if the provided {@code key} is {@code null}.
	 */
	public V remove(K key) {
		if(key == null) throw new NullPointerException("The provided key must not be null.");
		
		V valueToRemove = this.get(key);
		if(valueToRemove == null) {
			return null;
		} else {
			Entry<K, V> entryToRemove = new Entry<>(key, valueToRemove);
			if(this.entries.remove(entryToRemove) == true) {
				return valueToRemove;
			} else {
				return null;
			}
		}
	}

}
