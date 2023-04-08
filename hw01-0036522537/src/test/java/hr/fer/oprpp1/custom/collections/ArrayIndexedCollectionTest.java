package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class ArrayIndexedCollectionTest {
	
	private ArrayIndexedCollection nullCollection;
	private ArrayIndexedCollection emptyCollection;
	private ArrayIndexedCollection oneElementCollection;
	private ArrayIndexedCollection twoToTenElementsCollection;
	private ArrayIndexedCollection constantCollection;
	
	private final Random random = new Random();
	
	@BeforeEach
	void setUp() {
		nullCollection = null;
		
		emptyCollection = new ArrayIndexedCollection();
		
		oneElementCollection = new ArrayIndexedCollection();
		int randomNumber1 = random.nextInt(4);
		switch(randomNumber1) {
			case 0:
				oneElementCollection.add(random.nextInt(1000));
				break;
			case 1:
				oneElementCollection.add(random.nextDouble(1000));
				break;
			case 2:
				oneElementCollection.add(random.nextBoolean());
				break;
			case 3:
				oneElementCollection.add("random string");
				break;
		}
		
		twoToTenElementsCollection = new ArrayIndexedCollection();
		int randomNumber2 = random.nextInt(2, 10);
		for(int i = 0; i < randomNumber2; i++) {
			int randomNumber3 = random.nextInt(4);
			switch(randomNumber3) {
				case 0:
					twoToTenElementsCollection.add(random.nextInt(1000));
					break;
				case 1:
					twoToTenElementsCollection.add(random.nextDouble(1000));
					break;
				case 2:
					twoToTenElementsCollection.add(random.nextBoolean());
					break;
				case 3:
					twoToTenElementsCollection.add("random string");
					break;
			}
		}
		
		constantCollection = new ArrayIndexedCollection();
		constantCollection.add(0);
		constantCollection.add(1);
		constantCollection.add(2);
		constantCollection.add(3);
		constantCollection.add(4);
		constantCollection.add(5);
	}
	
	@Test
	public void emptyCollectionDefaultConstructorDefaultCapacityNumberOfElements() {
		int emptyCollectionSize = new ArrayIndexedCollection().size();
		assertEquals(0, emptyCollectionSize);
	}
	
	@Test
	public void emptyCollectionConstructorProvidedCapacityNumberOfElements() {
		int emptyCollectionSize = new ArrayIndexedCollection(3).size();
		assertEquals(0, emptyCollectionSize);
	}
	
	@Test
	public void emptyCollectionConstructorInvalidProvidedCapacityException() {
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(0));
	}
	
	@Test
	public void emptyCollectionConstructorProvidedCollectionAndDefaultCapacityNumberOfElements() {
		int emptyCollectionSize = new ArrayIndexedCollection(emptyCollection).size();
		assertEquals(0, emptyCollectionSize);
	}
	
	@Test
	public void emptyCollectionConstructorProvidedNullCollectionAndDefaultCapacityException() {
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(nullCollection));
	}
	
	@Test
	public void emptyCollectionConstructorProvidedCollectionAndCapacityNumberOfElements() {
		int emptyCollectionSize = new ArrayIndexedCollection(emptyCollection, 3).size();
		assertEquals(0, emptyCollectionSize);
	}
	
	@Test
	public void emptyCollectionConstructorProvidedNullCollectionAndCapacityException() {
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(nullCollection, 3));
	}
	
	@Test
	public void emptyCollectionConstructorProvidedCollectionAndInvalidCapacityException() {
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(emptyCollection, 0));
	}
	
	@Test
	public void addNullException() {
		assertThrows(NullPointerException.class, () -> emptyCollection.add(null));
	}
	
	@Test
	public void addSizeIncrease() {
		oneElementCollection.add(1);
		assertEquals(2, oneElementCollection.size());
	}
	
	@Test
	public void getIndexTooSmallException() {
		assertThrows(IndexOutOfBoundsException.class, () -> oneElementCollection.get(-1));
	}
	
	@Test
	public void getIndexTooLargeException() {
		assertThrows(IndexOutOfBoundsException.class, () -> oneElementCollection.get(3));
	}
	
	@Test
	public void clearSizeZero() {
		twoToTenElementsCollection.clear();
		assertEquals(0, twoToTenElementsCollection.size());
	}
	
	@Test
	public void insertProvidedNullCollectionAndPositionException() {
		assertThrows(NullPointerException.class, () -> twoToTenElementsCollection.insert(null, 1));
	}
	
	@Test
	public void insertProvidedCollectionAndInvalidPositionException() {
		assertThrows(IndexOutOfBoundsException.class, () -> twoToTenElementsCollection.insert("something", -1));
	}
	
	@Test
	public void insertSizeIncrease() {
		int startingSize = twoToTenElementsCollection.size();
		twoToTenElementsCollection.insert("something", 1);
		assertEquals(startingSize + 1, twoToTenElementsCollection.size());
	}
	
	@Test
	public void indexOfExistentElement() {
		assertEquals(0, constantCollection.indexOf(0));
	}
	
	@Test
	public void indexOfNonExistentElementException() {
		assertEquals(-1, constantCollection.indexOf(6));
	}
	
	@Test
	public void indexOfElementInEmptyCollection() {
		assertEquals(-1, emptyCollection.indexOf(0));
	}
	
	@Test
	public void indexOfProvidedNullElementException() {
		assertEquals(-1, twoToTenElementsCollection.indexOf(null));
	}
	
	@Test
	public void removeProvidedNullObjectException() {
		assertFalse(twoToTenElementsCollection.remove(null));
	}
	
	@Test
	public void removeProvidedInvalidIndexException() {
		assertThrows(IndexOutOfBoundsException.class, () -> twoToTenElementsCollection.remove(-1));
	}
	
	@Test
	public void removeNonExistentElementException() {
		assertFalse(constantCollection.remove(Integer.valueOf(6)));
	}
	
	@Test
	public void removeOnlyElement() {
		oneElementCollection.remove(0);
		assertEquals(0, oneElementCollection.size());
	}
	
}
