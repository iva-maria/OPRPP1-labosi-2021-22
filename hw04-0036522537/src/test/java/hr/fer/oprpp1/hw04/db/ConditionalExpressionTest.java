package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConditionalExpressionTest {

	private StudentRecord student;
	private ConditionalExpression expressionJmbag;
	private ConditionalExpression expressionLastName;
	private ConditionalExpression expressionFirstName;
	
	@BeforeEach
	public void setUp() {
		student = new StudentRecord("0036522537", "Ivanković", "Iva", 5);
		expressionJmbag = new ConditionalExpression(FieldValueGetters.JMBAG, "0036522537", ComparisonOperators.EQUALS);
		expressionLastName = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Ivanković", ComparisonOperators.LIKE);
		expressionFirstName = new ConditionalExpression(FieldValueGetters.FIRST_NAME, "I*a", ComparisonOperators.LIKE);
	}
	
	@Test
	public void testConditionalExpressionSatisfiedTrue() {
		assertTrue(expressionJmbag.getComparisonOperator().satisfied(expressionJmbag.getFieldValueGetter().get(student), expressionJmbag.getStringLiteral()));
		assertTrue(expressionLastName.getComparisonOperator().satisfied(expressionLastName.getFieldValueGetter().get(student), expressionLastName.getStringLiteral()));
		assertTrue(expressionFirstName.getComparisonOperator().satisfied(expressionFirstName.getFieldValueGetter().get(student), expressionFirstName.getStringLiteral()));
	}
	
	@Test
	public void testConditionalExpressionSatisfiedFalse() {
		assertFalse(expressionJmbag.getComparisonOperator().satisfied(expressionJmbag.getFieldValueGetter().get(student), "0036522538"));
		assertFalse(expressionLastName.getComparisonOperator().satisfied(expressionLastName.getFieldValueGetter().get(student), "Maria"));
		assertFalse(expressionFirstName.getComparisonOperator().satisfied(expressionFirstName.getFieldValueGetter().get(student), "Ivaa*"));
	}
	
}
