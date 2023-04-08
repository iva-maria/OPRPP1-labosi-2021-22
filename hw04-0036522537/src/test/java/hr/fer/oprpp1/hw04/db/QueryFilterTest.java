package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QueryFilterTest {
	
	private QueryFilter filter;
	
	private List<ConditionalExpression> singleExpressionList = new ArrayList<>();
	private List<ConditionalExpression> multipleExpressionList = new ArrayList<>();
	
	ConditionalExpression expression1 = new ConditionalExpression(FieldValueGetters.JMBAG, "0000000017", ComparisonOperators.LESS_OR_EQUALS);
	ConditionalExpression expression2 = new ConditionalExpression(FieldValueGetters.LAST_NAME, "I*", ComparisonOperators.LIKE);
	ConditionalExpression expression3 = new ConditionalExpression(FieldValueGetters.FIRST_NAME, "I*", ComparisonOperators.LIKE);
	
	StudentRecord record1 = new StudentRecord("0000000010", "Ivanković", "Iva", 5);
	StudentRecord record2 = new StudentRecord("0000000017", "Ivanković", "Maria", 4);
	StudentRecord record3 = new StudentRecord("0036522537", "Mandić", "Srećko", 3);
	
	@BeforeEach
	public void setUp() {
		singleExpressionList.add(expression1);
		
		multipleExpressionList.add(expression2);
		multipleExpressionList.add(expression3);
	}
	
	@Test
	public void testSingleExpression() {
		filter = new QueryFilter(singleExpressionList);
		
		assertTrue(filter.accepts(record1));
		assertTrue(filter.accepts(record2));
		assertFalse(filter.accepts(record3));
	}
	
	@Test
	public void testMultipleExpression() {
		filter = new QueryFilter(multipleExpressionList);
		
		assertTrue(filter.accepts(record1));
		assertFalse(filter.accepts(record2));
	}
	
}
