package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.collections.SimpleHashtable.TableEntry;

public class SimpleHashtableTest {
	
	private SimpleHashtable<Object, Object> emptyHashtable;
	private SimpleHashtable<String, Integer> examMarks;
	
	@BeforeEach
	public void setUp() {
		
		emptyHashtable = new SimpleHashtable<>();
		
		examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
	}
	
	@Test
	public void testIllegalInitialNumberOfSlots() {
		assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<>(0));
	}
	
	@Test
	public void testTableEntryProvidedNullKey() {
		assertThrows(NullPointerException.class, () -> new TableEntry<>(null, "test"));
	}
	
	@Test
	public void testPutProvidedNullKey() {
		assertThrows(NullPointerException.class, () -> examMarks.put(null, 5));
	}
	
	@Test
	public void testPutWithoutOverwriting() {
		examMarks.put("Test", 4);
		assertEquals(5, examMarks.size());
	}
	
	@Test
	public void testPutWithOverwriting() {
		examMarks.put("Kristina", 1);
		assertEquals(4, examMarks.size());
	}
	
	@Test
	public void testGetNull() {
		assertNull(examMarks.get(null));
	}
	
	@Test
	public void testGetExistingEntry() {
		assertEquals(5, examMarks.get("Ivana"));
	}
	
	@Test
	public void testGetNonExistingEntry() {
		assertNull(examMarks.get("Test"));
	}
	
	@Test
	public void testSizeEmptyHashtable() {
		assertEquals(0, emptyHashtable.size());
	}
	
	@Test
	public void testSizeNonEmptyHashtable() {
		assertEquals(4, examMarks.size());
	}
	
	@Test
	public void testContainsKeyNull() {
		assertFalse(examMarks.containsKey(null));
	}
	
	@Test
	public void testContainsKeyExistingKey() {
		assertTrue(examMarks.containsKey("Ivana"));
	}
	
	@Test
	public void testContainsKeyNonExistingKey() {
		assertFalse(examMarks.containsKey("Ivan"));
	}
	
	@Test
	public void testContainsValueExistingValue() {
		assertTrue(examMarks.containsValue(5));
	}
	
	@Test
	public void testContainsValueNonExistingValue() {
		assertFalse(examMarks.containsValue(3));
	}
	
	@Test
	public void testRemoveNullKey() {
		assertNull(emptyHashtable.remove(null));
	}
	
	@Test
	public void testRemoveExistingKey() {
		examMarks.remove("Ivana");
		assertEquals(3, examMarks.size());
	}
	
	@Test
	public void testRemoveNonExistingKey() {
		assertNull(examMarks.remove("Test"));
	}
	
	@Test
	public void testIsEmptyEmptyHashtable() {
		assertTrue(emptyHashtable.isEmpty());
	}
	
	@Test
	public void testIsEmptyNonEmptyHashtable() {
		assertFalse(examMarks.isEmpty());
	}
	
	@Test
	public void testToArrayEmptyHashtable() {
		TableEntry<Object, Object>[] array = emptyHashtable.toArray();
		assertEquals(0, array.length);
	}
	
	@Test
	public void testToArrayNonEmptyHashtable() {
		TableEntry<String, Integer>[] array = examMarks.toArray();
		assertEquals(4, array.length);
	}
	
	@Test
	public void testIteratorHasNextModifiedHashtable() {
		Iterator<TableEntry<String, Integer>> iterator = examMarks.iterator();
		
		examMarks.put("Test", 4);
		assertThrows(ConcurrentModificationException.class, () -> iterator.hasNext());
	}
	
	@Test
	public void testIteratorRemoveModifedHashtable() {
		Iterator<TableEntry<String, Integer>> iterator = examMarks.iterator();
		
		examMarks.put("Test", 5);
		assertThrows(ConcurrentModificationException.class, () -> iterator.remove());
	}
	
}
