package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Class {@code QueryParser} represents an implementation of a parser
 * for a query statement on the {@code StudentDatabase} database.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class QueryParser {
	
	/**
	 * A {@code QueryLexer} instance for the current parser.
	 */
	private QueryLexer lexer;
	
	/**
	 * List of conditional expressions obtained from the query.
	 */
	private List<ConditionalExpression> conditionalExpressions;
	
	/**
	 * Constructor which creates a new {@code QueryParser} instance and
	 * tries to parse the provided query.
	 * 
	 * @param query the query which needs to be parsed.
	 * @throws QueryParserException if one of the possible
	 * errors happened during parsing.
	 */
	public QueryParser(String query) {
		this.lexer = new QueryLexer(query);
		this.conditionalExpressions = new ArrayList<>();
		
		try {
			parseQuery();
		} catch(Exception e) {
			throw new QueryParserException("An error ocurred during parsing of the query.");
		}
	}
	
	/**
	 * Check whether the provided query is direct.
	 * 
	 * @return {@code true} if there is only one conditional expression in the query, {@code false} otherwise.
	 */
	public boolean isDirectQuery() {
		if(conditionalExpressions.size() != 1) return false;
		
		ConditionalExpression expression = conditionalExpressions.get(0);
		return expression.getFieldValueGetter() == FieldValueGetters.JMBAG && expression.getComparisonOperator() == ComparisonOperators.EQUALS;
	}
	
	/**
	 * Retrieves the JMBAG string literal of the direct query.
	 * 
	 * @return JMBAG string literal of the direct query.
	 * @throws IllegalStateException if the query is not direct.
	 */
	public String getQueriedJMBAG() {
		if(!this.isDirectQuery()) throw new IllegalStateException("The query was not a direct one.");
		
		return conditionalExpressions.get(0).getStringLiteral();
	}
	
	/**
	 * Fetches the list of conditional expressions.
	 * 
	 * @return list of conditional expressions.
	 */
	public List<ConditionalExpression> getQuery() {
		return conditionalExpressions;
	}
	
	/**
	 * Parses the provided query.
	 */
	public void parseQuery() {
		while(true) {
			QueryToken fieldToken = lexer.nextToken();
			if(fieldToken.getType() != QueryTokenType.FIELD_VALUE) throw new QueryParserException("There is no attribute name.");
			IFieldValueGetter fieldValueGetter = parseFieldValueGetter(fieldToken);

			QueryToken operatorToken = lexer.nextToken();
			if(operatorToken.getType() != QueryTokenType.COMPARISON_OPERATOR) throw new QueryParserException("There is no comparison operator.");
			IComparisonOperator comparisonOperator = parseComparisonOperator(operatorToken);

			QueryToken stringToken = lexer.nextToken();
			if(stringToken.getType() != QueryTokenType.STRING_LITERAL) throw new QueryParserException("There is no string literal.");
			String stringLiteral = stringToken.getValue().toString();

			conditionalExpressions.add(new ConditionalExpression(fieldValueGetter, stringLiteral, comparisonOperator));
			
			QueryToken currentToken = lexer.nextToken();
			if(currentToken.getType() == QueryTokenType.LOGICAL_OPERATOR) {
				continue;
			} else if(currentToken.getType() == QueryTokenType.EOF) {
				break;
			} else {
				throw new IllegalArgumentException("Invalid query.");
			}
		}
	}
	
	/**
	 * Parses the attribute identifier.
	 * 
	 * @param token the attribute token.
	 * @return a field value getter.
	 */
	public IFieldValueGetter parseFieldValueGetter(QueryToken token) {
		if(token.getValue().equals("jmbag")) {
			return FieldValueGetters.JMBAG;
		} else if(token.getValue().equals("lastName")) {
			return FieldValueGetters.LAST_NAME;
		} else if(token.getValue().equals("firstName")) {
			return FieldValueGetters.FIRST_NAME;
		} else {
			throw new IllegalArgumentException("Invalid field value getter.");
		}
	}
	
	/**
	 * Parses the comparison operator.
	 * 
	 * @param token the operator token.
	 * @return a comparison operator.
	 */
	public IComparisonOperator parseComparisonOperator(QueryToken token) {
		switch(token.getValue()) {
			case "<":
				return ComparisonOperators.LESS;
			case "<=":
				return ComparisonOperators.LESS_OR_EQUALS;
			case ">":
				return ComparisonOperators.GREATER;
			case ">=":
				return ComparisonOperators.GREATER_OR_EQUALS;
			case "=":
				return ComparisonOperators.EQUALS;
			case "!=":
				return ComparisonOperators.NOT_EQUALS;
			case "LIKE":
				return ComparisonOperators.LIKE;
			default:
				throw new IllegalArgumentException("Invalid operator.");
		}
	}
		
}
