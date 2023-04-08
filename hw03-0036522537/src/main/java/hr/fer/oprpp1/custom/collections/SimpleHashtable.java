package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Objects;

import static java.lang.Math.abs;

/**
 * Class {@code SimpleHashtable} represents a table for storing objects in key-value pairs.  
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 *
 * @param <K> type of a key object in the table.
 * @param <V> type of a value object in the table.
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
	
	/**
	 * A backing array that is used for representation of table slots.
	 */
	private TableEntry<K, V>[] table;
	
	/**
	 * The number of the current {@code SimpleHashtable} instance, calculated as the number
	 * of key-value pairs stored in the table.
	 */
	private int size;
	
	/**
	 * A constant which the number of slots in the table is set to when the constructor
	 * function is called with no provided value for the number of slots.
	 */
	static final int DEFAULT_NUMBER_OF_SLOTS = 16;
	
	/**
	 * A constant which represents the maximum allowed number of occupied slots.
	 */
	static final double OVERFILL_QUOTIENT = 0.75;
	
	/**
	 * A constant factor by which the capacity of the current table is multiplied when 
	 * the number of occupied slots surpasses the {@code OVERFILL_QUOTIENT}.
	 */
	static final int RESIZE_COEFFICIENT = 2;
	
	/**
	 * A variable that serves as a counter of modifications made on the current
	 * hashtable since its creation.
	 */
	private int modificationCount;
	
	/**
	 * Default constructor which creates a new {@code SimpleHashtable} instance
	 * and sets its number of slots, {@code size}, to the default value.
	 */
	public SimpleHashtable() {
		this(DEFAULT_NUMBER_OF_SLOTS);
	}
	
	/**
	 * A constructor which creates a new {@code SimpleHashtable} instance and sets its
	 * number of slots, {@code size}, to the provided value.
	 * 
	 * @param numberOfSlots the number of slots which the created table should have.
	 * @throws IllegalArgumentException when the provided {@code numberOfSlots} is less than 1.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int numberOfSlots) {
		if(numberOfSlots < 1) throw new IllegalArgumentException("The initial number of slots cannot be smaller than 1.");
		
		this.table = (TableEntry<K, V>[])new TableEntry[nextPowerOfTwo(numberOfSlots)];
		this.size = 0;
		this.modificationCount = 0;
	}
	
	/**
	 * Class {@code TableEntry<K, V>} is used for representing one 
	 * of the key-value pairs of objects in the table.
	 * 
	 * @author Iva Maria Ivanković
	 * @version 1.0
	 *
	 * @param <K> type of the  {@code key} object in the entry.
	 * @param <V> type of the {@code value} object in the entry.
	 */
	public static class TableEntry<K, V> {
		
		/**
		 * The key of the current entry.
		 */
		private K key;
		
		/**
		 * The value of the current entry.
		 */
		private V value;
		
		/**
		 * Reference to the next {@code TableEntry} in the same slot of the table as the current entry.
		 */
		private TableEntry<K, V> next;
		
		/**
		 * Constructor which creates a new {@code TableEntry} instance and sets its
		 * {@code key} and {@code value} properties to the provided values.
		 * 
		 * @param key the key of the entry.
		 * @param value the value of the entry.
		 * @throws NullPointerException when the provided {@code key} is {@code null}.
		 */
		protected TableEntry(K key, V value) {
			if(key == null) throw new NullPointerException("The provided key must not be null.");
			
			this.key = key;
			this.value = value;
			this.next = null;
		}
		
		/**
		 * Constructor which creates a new {@code TableEntry} instance and sets its
		 * {@code key}, {@code value} and {@code next} properties to the provided values.
		 * 
		 * @param key the key of the entry.
		 * @param value the value of the entry.
		 * @param next reference to the next entry in the same slot.
		 * @throws NullPointerException when the provided {@code key} is {@code null}.
		 */
		protected TableEntry(K key, V value, TableEntry<K,V> next) {
			if(key == null) throw new NullPointerException("The provided key must not be null.");
			
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
		/**
		 * Fetches the key of the current entry.
		 * 
		 * @return the key of the current entry.
		 */
		public K getKey() {
			return this.key;
		}
		
		/**
		 * Fetches the value of the current entry.
		 * 
		 * @return the value of the current entry.
		 */
		public V getValue() {
			return this.value;
		}
		
		/**
		 * Sets the value of the current entry to the provided {@code value}.
		 * 
		 * @param value the value which the value of the current entry is to be set to.
		 */
		public void setValue(V value) {
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
			if(!(obj instanceof TableEntry<?, ?>)) return false;
			
			TableEntry<?, ?> other = (TableEntry<?, ?>)obj;
			return Objects.equals(this.key, other.key) && Objects.equals(this.value, other.value);
		}
		
	}
	
	/**
	 * Inserts a new {@code TableEntry} with the provided {@code key} and {@code value}
	 * into the current table. If an entry with the provided {@code key} already exists,
	 * its {@code value} will be overwritten.
	 * 
	 * @param key the key of the new entry.
	 * @param value the value of the new entry.
	 * @return old value for the provided key if it existed, {@code null} otherwise.
	 * @throws NullPointerException when the provided {@code key} is {@code null}.
	 */
	public V put(K key, V value) {
		if(key == null) throw new NullPointerException("The provided key must not be null.");
		
		int slot = this.slot(key);
		
		if(this.containsKey(key)) { //postoji zapis, pronadi ga i zamijeni, vrati staru vrijednost
			TableEntry<K, V> entry = this.table[slot];
			
			while(entry != null) {
				if(entry.key.equals(key)) {
					V oldValue = entry.value;
					entry.setValue(value);
					
					return oldValue;
				}
				
				entry = entry.next;
			}
		} else { //ne postoji zapis
			TableEntry<K, V> entry = this.table[slot];
			
			if(entry == null) { //ne postoji nista u trenutnom slotu, kreiraj novi zapis i vrati null
				this.table[slot] = new TableEntry<K, V>(key, value, null);
				this.size++;
				this.modificationCount++;
				
				return null;
			} else {			
				while(entry.next != null) entry = entry.next; //postoji nesto u trenutnom slotu, dodaj ga na kraj i vrati null
				
				entry.next = new TableEntry<K, V>(key, value, null);
				this.size++;
				this.modificationCount++;
				
				return null;
			}
		}
		
		return null;
	}
	
	/**
	 * Fetches the value for the provided {@code key} from the table.
	 * 
	 * @param key the key for which we want to know the value.
	 * @return the value for the provided key if it exists, {@code null} otherwise.
	 */
	public V get(Object key) {
		if(key == null) return null;
		
		int slot = this.slot(key);
		TableEntry<K, V> entry = this.table[slot];
		
		while(entry != null) {
			if(entry.key.equals(key)) return entry.value;
			entry = entry.next;
		}
		
		return null;
	}
	
	/**
	 * Determines the size of the current table, calculated as the number of
	 * key-value pairs stored in its slots.
	 * 
	 * @return the number of key-value pairs stored in the table.
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Checks if an entry with the provided {@code key} exists in the table.
	 * 
	 * @param key the key for which we want to know if it is contained in the table.
	 * @return {@code true} if there is an entry with the provided key in the table, {@code false} otherwise.
	 */
	public boolean containsKey(Object key) {
		if(key == null) return false;
		
		if(this.get(key) != null) return true;
		return false;
	}
	
	/**
	 * Checks if an entry with the provided {@code value} exists in the table.
	 * 
	 * @param value the value for which we want to know if it is contained in the table.
	 * @return {@code true} if there is an entry with the provided value in the table, {@code false} otherwise.
	 */
	public boolean containsValue(Object value) {
		for(TableEntry<K, V> entry : this.table) {
			while(entry != null) {
				if(entry.value != null) {
					if(entry.value.equals(value)) return true;
				} else {
					if(value == null) return true;
				}
				
				entry = entry.next;
			}
		}
		
		return false;
	}
	
	/**
	 * Removes an entry with the provided {@code key} from the table.
	 * 
	 * @param key the key of the entry which we want to remove.
	 * @return the value of the removed entry if it existed, {@code null} otherwise.
	 */
	public V remove(Object key) {
		if(key == null) return null;
		if(!(this.containsKey(key))) return null;
		
		V value = null;
		int slot = this.slot(key);
		
		TableEntry<K, V> entry = this.table[slot];
		if(entry.key.equals(key)) { //na pocetku slota
			value = entry.value;
			
			this.table[slot] = entry.next;
			this.size--;
			this.modificationCount++;
			
			return value;

		} else { //nije na pocetku slota
			TableEntry<K, V> previous;
			
			while(entry.next != null) {
				previous = entry;
				entry = entry.next;
				
				if(entry.key.equals(key)) {
					value = entry.value;
					previous.next = entry.next;
					
					this.size--;
					this.modificationCount++;
					break;
				}
			}
			
			return value;
		}
	}
	
	/**
	 * Indicates whether there are any entries in the table.
	 * 
	 * @return {@code true} if there are no entries in the table, {@code false} otherwise.
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("[");
		
		for(TableEntry<K, V> entry : this.table) {
			while(entry != null) {
				sb.append(entry.key.toString());
				sb.append("=");
				
				if(entry.value == null) {
					sb.append((Object)null);
				} else {
					sb.append(entry.value.toString());
				}
				
				entry = entry.next;
				if(entry != null) sb.append(", ");
			}
		}
		
		sb.append("]");
		
		return sb.toString();
	}
	
	/**
	 * Creates a new array and stores all entries from the table into that array,
	 * in order of their appearance in the table (by slot and by position in the slot).
	 * 
	 * @return an array of table entries.
	 */
	public TableEntry<K, V>[] toArray() {
		
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] array = (TableEntry<K, V>[])new TableEntry[this.size];
		
		int index = 0;
		for(TableEntry<K, V> entry : this.table) {
			while(entry != null) {
				array[index++] = entry;
				entry = entry.next;
			}
		}
		
		return array;
	}
	
	/**
	 * Removes all entries from the current table.
	 */
	public void clear() {
		for(int i = 0; i < this.table.length; i++) {
			this.table[i] = null;
		}
		this.size = 0;
		this.modificationCount++;
	}
	
	/**
	 * Calculates the smallest power of two that is bigger than or equal to the provided {@code number}.
	 * 
	 * @param number the number for which we want to calculate the next power of two.
	 * @return the calculated power of two.
	 */
	public int nextPowerOfTwo(int number) {		
		int numberOfSlots = (int) Math.pow(2, Math.ceil(Math.log(number) / Math.log(2)));	
		return numberOfSlots;
	}
	
	/**
	 * Determines the slot in the current table that the provided {@code key} belongs to.
	 * 
	 * @param key the provided key.
	 * @return slot of the current table for the provided key.
	 */
	public int slot(Object key) {
		int slot = abs(key.hashCode() % this.table.length);
		return slot;
	}
	
	/**
	 * Checks whether the current table is filled over the percentage {@code OVERFILL_QUOTIENT}.
	 * If it is, the capacity is multiplied by the {@code RESIZE_COEFFICIENT}.
	 */
	@SuppressWarnings("unchecked")
	public void checkOverfill() {
		if(this.size / (this.table.length) >= OVERFILL_QUOTIENT) {
			TableEntry<K, V>[] oldTable = this.toArray();
	        this.table = (TableEntry<K, V>[])new TableEntry[this.table.length * RESIZE_COEFFICIENT];
	        
	        for(TableEntry<K, V> entry : oldTable) {
	        	int slot = this.slot(entry.key);
	        	TableEntry<K, V> currentEntry = this.table[slot];
	        	
	        	if(currentEntry == null) {
	        		this.table[slot] = new TableEntry<K, V>(entry.key, entry.value);
	        	} else {
	        		TableEntry<K, V> tableElement = this.table[slot];
	        		while(tableElement.next != null) tableElement = tableElement.next;
	        		
	        		tableElement.next = new TableEntry<K, V>(entry.key, entry.value);
	        	}
	        }
	        
	        this.modificationCount++;
		}
	}
	
	/**
	 * Class {@code IteratorImpl} represents an implementation of an {@code Iterator}
	 * for the {@code SimpleHashtable} class.
	 * 
	 * @author Iva Maria Ivanković
	 * @version 1.0
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		/**
		 * Last fetched entry from the table.
		 */
		private TableEntry<K, V> currentEntry;
		
		/**
		 * Next entry in the table to be fetched.
		 */
		private TableEntry<K, V> nextEntry;
		
		/**
		 * The slot that the iterator is currently at.
		 */
		private int currentSlot;
		
		/**
		 * The number of modifications which were made on the hashtable
		 * at the time of creation of the iterator.
		 */
		private int savedModificationCount;
		
		/**
		 * Default constructor.
		 */
		public IteratorImpl() {
			this.currentEntry = null;
			this.nextEntry = null;
			this.currentSlot = -1;
			this.savedModificationCount = modificationCount;
			
			while(true) {
				this.currentSlot++;
				if(this.currentSlot >= table.length) break;
				
				this.nextEntry = table[this.currentSlot];
				
				if(this.nextEntry != null) break;
			}
		}
		
		/**
		 * Determines whether there are more entries in the table to be iterated over.
		 * 
		 * @return {@code true} if there are more entries, {@code false} otherwise.
		 */
		@Override
		public boolean hasNext() {
			if(this.savedModificationCount != modificationCount) throw new ConcurrentModificationException("The hashtable has been modified.");
			
			if(this.nextEntry != null) return true;
			return false;
		}

		/**
		 * Fetches the next entry in the iteration.
		 * 
		 * @return the next entry in the iteration if it exists, {@code null} otherwise.
		 */
		@Override
		public TableEntry<K, V> next() {
			this.currentEntry = this.nextEntry;
			
			if (this.nextEntry != null) this.nextEntry = this.nextEntry.next;

			if(this.nextEntry == null) {
	            while (true) {
	            	this.currentSlot++;
	                if (this.currentSlot >= table.length) break;
	
	                this.nextEntry = table[this.currentSlot];
	                if(this.nextEntry != null) break;
	            }
			}
			
			return this.currentEntry;
		}
		
		/**
		 * Removes the current entry in the iteration from the table.
		 */
		@Override
		public void remove() {
			if(this.savedModificationCount != modificationCount) throw new ConcurrentModificationException("The hashtable has been modified."); 
			
			if(this.currentEntry == null) throw new IllegalStateException("The remove() method cannot be called twice after next() method.");
			
			SimpleHashtable.this.remove(this.currentEntry.key);
			this.currentEntry = null;
			
			this.savedModificationCount = modificationCount;
		}
		
	}

	/**
	 * Creates an iterator for {@code TableEntry} instances.
	 * 
	 * @return new iterator for the current hashtable.
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		Iterator<TableEntry<K, V>> iterator = new IteratorImpl();
		return iterator;
	}
}
