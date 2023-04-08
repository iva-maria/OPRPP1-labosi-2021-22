package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ComparisonOperatorsTest {
	
	@Test
	public void testLessTrue() {
		assertTrue(ComparisonOperators.LESS.satisfied("Iva", "Maria"));
	}
	
	@Test
	public void testLessFalse() {
		assertFalse(ComparisonOperators.LESS.satisfied("Maria", "Iva"));
		assertFalse(ComparisonOperators.LESS.satisfied("Iva", "Iva"));
	}
	
	@Test
	public void testLessOrEqualsTrue() {
		assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("Iva", "Maria"));
		assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("Iva", "Iva"));
	}
	
	@Test
	public void testLessOrEqualsFalse() {
		assertFalse(ComparisonOperators.LESS_OR_EQUALS.satisfied("Maria", "Iva"));
	}
	
	@Test
	public void testGreaterTrue() {
		assertTrue(ComparisonOperators.GREATER.satisfied("Maria", "Iva"));
	}
	
	@Test
	public void testGreaterFalse() {
		assertFalse(ComparisonOperators.GREATER.satisfied("Iva", "Maria"));
		assertFalse(ComparisonOperators.GREATER.satisfied("Iva", "Iva"));
	}
	
	@Test
	public void testGreaterOrEqualsTrue() {
		assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("Maria", "Iva"));
		assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("Iva", "Iva"));
	}
	
	@Test
	public void testGreaterOrEqualsFalse() {
		assertFalse(ComparisonOperators.GREATER_OR_EQUALS.satisfied("Iva", "Maria"));
	}
	
	@Test
	public void testEqualsTrue() {
		assertTrue(ComparisonOperators.EQUALS.satisfied("Iva", "Iva"));
	}
	
	@Test
	public void testEqualsFalse() {
		assertFalse(ComparisonOperators.EQUALS.satisfied("Iva", "Maria"));
	}
	
	@Test
	public void testNotEqualsTrue() {
		assertTrue(ComparisonOperators.NOT_EQUALS.satisfied("Iva", "Maria"));
	}
	
	@Test
	public void testNotEqualsFalse() {
		assertFalse(ComparisonOperators.NOT_EQUALS.satisfied("Iva", "Iva"));
	}
	
	@Test
	public void testLikeTrue() {
		assertTrue(ComparisonOperators.LIKE.satisfied("", ""));
		assertTrue(ComparisonOperators.LIKE.satisfied("Iva", "Iva"));
		assertTrue(ComparisonOperators.LIKE.satisfied("Iva", "*Iva"));
		assertTrue(ComparisonOperators.LIKE.satisfied("Iva", "Iva*"));
		assertTrue(ComparisonOperators.LIKE.satisfied("Iva", "I*va"));
		assertTrue(ComparisonOperators.LIKE.satisfied("Iva", "*va"));
		assertTrue(ComparisonOperators.LIKE.satisfied("Iva", "Iv*"));
		assertTrue(ComparisonOperators.LIKE.satisfied("Iva", "I*a"));
		assertTrue(ComparisonOperators.LIKE.satisfied("Maria", "*ria"));
		assertTrue(ComparisonOperators.LIKE.satisfied("Maria", "Mar*"));
		assertTrue(ComparisonOperators.LIKE.satisfied("Maria", "M*a"));
	}
	
	@Test
	public void testLikeFalse() {
		assertFalse(ComparisonOperators.LIKE.satisfied("Iva", "iva"));
		assertFalse(ComparisonOperators.LIKE.satisfied("Iva", "I*aa"));
	}
	
	@Test
	public void testMoreThanOneWildCard() {
		assertThrows(IllegalArgumentException.class, () -> ComparisonOperators.LIKE.satisfied("Iva", "*v*"));
	}
	
}
