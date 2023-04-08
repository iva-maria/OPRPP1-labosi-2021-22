package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.*;

public class DictionaryTest {
	
	private Dictionary<Object, Object> emptyDictionary;
	private Dictionary<Object, Object> nonEmptyDictionaryContainingObjects;
	private Dictionary<Integer, String> nonEmptyDictionaryContainingIntegerKeysStringValues;
	
	private final Random random = new Random();

	@BeforeEach
	public void setUp() {
		 
		emptyDictionary = new Dictionary<>();
		
		nonEmptyDictionaryContainingObjects = new Dictionary<Object, Object>();
		nonEmptyDictionaryContainingObjects.put(1, "test");
		nonEmptyDictionaryContainingObjects.put("test?", "test.");
		nonEmptyDictionaryContainingObjects.put(-1, random.nextBoolean());
		nonEmptyDictionaryContainingObjects.put('t', random.nextInt());
		nonEmptyDictionaryContainingObjects.put(17.5, random.nextDouble());
		
		nonEmptyDictionaryContainingIntegerKeysStringValues = new Dictionary<Integer, String>();
		nonEmptyDictionaryContainingIntegerKeysStringValues.put(1, "one");
		nonEmptyDictionaryContainingIntegerKeysStringValues.put(2, "two");
		nonEmptyDictionaryContainingIntegerKeysStringValues.put(3, "three");
		nonEmptyDictionaryContainingIntegerKeysStringValues.put(4, "four");
		nonEmptyDictionaryContainingIntegerKeysStringValues.put(5, "five");
	}
	
	@Test
	public void testEmptyDictionaryDefaultConstructorNumberOfElements() {
		assertEquals(0, emptyDictionary.size());
	}
	
	@Test
	public void testIsEmptyEmptyDictionary() {
		assertTrue(emptyDictionary.isEmpty());
	}
	
	@Test
	public void testSizeOfNonEmptyDictionaries() {
		assertEquals(5, nonEmptyDictionaryContainingObjects.size());
		assertEquals(5, nonEmptyDictionaryContainingIntegerKeysStringValues.size());
	}
	
	@Test
	public void testClearNonEmptyDictionary() {
		nonEmptyDictionaryContainingObjects.clear();
		assertTrue(nonEmptyDictionaryContainingObjects.isEmpty());
		
		nonEmptyDictionaryContainingIntegerKeysStringValues.clear();
		assertTrue(nonEmptyDictionaryContainingIntegerKeysStringValues.isEmpty());
	}
	
	@Test
	public void testPutNullKey() {
		assertThrows(NullPointerException.class, () -> nonEmptyDictionaryContainingObjects.put(null, "test"));
	}
	
	@Test
	public void testNewEntryWithoutOverwriting() {
		nonEmptyDictionaryContainingIntegerKeysStringValues.put(6, "six");
		assertEquals(6, nonEmptyDictionaryContainingIntegerKeysStringValues.size());
	}
	
	@Test
	public void testNewEntryWithOverwriting() {
		nonEmptyDictionaryContainingIntegerKeysStringValues.put(5, "pet");
		assertEquals(5, nonEmptyDictionaryContainingIntegerKeysStringValues.size());
	}
	
	@Test
	public void testGetNullKey() {
		assertThrows(NullPointerException.class, () -> nonEmptyDictionaryContainingObjects.get(null));
	}
	
	@Test
	public void testGetExistingEntry() {
		assertEquals("one", nonEmptyDictionaryContainingIntegerKeysStringValues.get(1));
	}
	
	@Test
	public void testGetNonExistingEntry() {
		assertNull(nonEmptyDictionaryContainingIntegerKeysStringValues.get(6));
	}
	
	@Test
	public void testRemoveNullKey() {
		assertThrows(NullPointerException.class, () -> nonEmptyDictionaryContainingObjects.remove(null));
	}
	
	@Test
	public void testRemoveExistingEntry() {
		assertEquals("four", nonEmptyDictionaryContainingIntegerKeysStringValues.remove(4));
	}
	
	@Test
	public void testRemoveNonExistingEntry() {
		assertNull(nonEmptyDictionaryContainingIntegerKeysStringValues.remove(7));
	}
	
}
