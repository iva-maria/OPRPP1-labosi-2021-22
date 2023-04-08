package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FieldValueGettersTest {

	private StudentRecord student = new StudentRecord("0036522537", "Ivanković", "Iva", 5);
	
	@Test
	public void testGetJMBAG() {
		assertEquals("0036522537", FieldValueGetters.JMBAG.get(student));
	}
	
	@Test
	public void testGetLastName() {
		assertEquals("Ivanković", FieldValueGetters.LAST_NAME.get(student));
	}
	
	@Test
	public void testGetFirstName() {
		assertEquals("Iva", FieldValueGetters.FIRST_NAME.get(student));
	}
	
}
