package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * Class {@code QueryFilter} represents a filter implementation
 * for queries over the {@code StudentDatabase} database.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class QueryFilter implements IFilter {

	/**
	 * List of conditional expressions which are used for filtering records in the database.
	 */
	private List<ConditionalExpression> conditionalExpressions;
	
	/**
	 * Constructor which initializes the {@code conditionalExpressions} list.
	 * 
	 * @param conditionalExpressions list of conditional expressions to be used for filtering.
	 */
	public QueryFilter(List<ConditionalExpression> conditionalExpressions) {
		this.conditionalExpressions = conditionalExpressions;
	}
	
	@Override
	public boolean accepts(StudentRecord record) {
		for(ConditionalExpression expression : conditionalExpressions) {
			if(!(expression.getComparisonOperator().satisfied(expression.getFieldValueGetter().get(record), expression.getStringLiteral()))) return false;
		}
		return true;
	}

}
