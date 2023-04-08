package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class QueryParserTest {

	private QueryParser parser;
	
	//direct queries
	private String query1 = "jmbag = \"0000000010\"";
	private String query2 = "jmbag=\"0000000005\"";
	
	//multiple queries
	private String query3 = "jmbag=\"0000000017\"	AND	firstName = \"Goran\"";
	
	//wrong logical operator
	private String query4 = "jmbag=\"0000000017\"	firstName = \"Goran\"";
	private String query5 = "jmbag=\"0000000017\"	amd	firstName = \"Goran\"";
	
	//something missing from query; wrong format
	private String query6 = "jmbag=\"0000000017\"	AND	\"Goran\"";
	private String query7 = "jmbag=\"0000000017\"	AND	firstName \"Goran\"";
	private String query8 = "jmbag=\"0000000017\"	AND	firstName = ";
	
	@Test
	public void testIsDirectQuery1True() {
		parser = new QueryParser(query1);
		assertTrue(parser.isDirectQuery());
	}
	
	@Test
	public void testIsDirectQuery2True() {
		parser = new QueryParser(query2);
		assertTrue(parser.isDirectQuery());
	}
	
	@Test
	public void testIsDirectQuery3False() {
		parser = new QueryParser(query3);
		assertFalse(parser.isDirectQuery());
	}
	
	@Test
	public void testGetQueriedJMBAG() {
		parser = new QueryParser(query1);
		assertEquals("0000000010", parser.getQueriedJMBAG());
	}
	
	@Test
	public void testGetQueriedJMBAGThrows() {
		parser = new QueryParser(query3);
		assertThrows(IllegalStateException.class, () -> parser.getQueriedJMBAG());
	}
	
	@Test
	public void testMultipleQueriesNoAndThrows() {
		assertThrows(QueryParserException.class, () -> new QueryParser(query4));
	}
	
	@Test
	public void testMultipleQueriesWrongAndThrows() {
		assertThrows(QueryParserException.class, () -> new QueryParser(query5));
	}
	
	@Test
	public void testMultipleQueriesNoAttributeNameThrows() {
		assertThrows(QueryParserException.class, () -> new QueryParser(query6));
	}
	
	@Test
	public void testMultipleQueriesNoComparisonOperatorThrows() {
		assertThrows(QueryParserException.class, () -> new QueryParser(query7));
	}
	
	@Test
	public void testMultipleQueriesNoStringLiteralThrows() {
		assertThrows(QueryParserException.class, () -> new QueryParser(query8));
	}
	
}
