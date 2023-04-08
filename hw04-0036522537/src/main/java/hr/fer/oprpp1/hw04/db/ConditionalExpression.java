package hr.fer.oprpp1.hw04.db;

/**
 * Class {@code ConditionalExpression} represents a conditional expression in a query
 * over the {@code StudentDatabase} database.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class ConditionalExpression {
	
	/**
	 * Represents a getter for the field value that is to be compared. 
	 */
	private IFieldValueGetter fieldValueGetter;
	
	/**
	 * Represents a string literal that the field value is to be compared to.
	 */
	private String stringLiteral;
	
	/**
	 * Represents a comparison operator for comparing the field value to a string literal.
	 */
	private IComparisonOperator comparisonOperator;
	
	/**
	 * Constructor which sets the {@code fieldValueGetter}, {@code stringLiteral} and {@code comparisonOperator}
	 * attributes to the provided values.
	 * 
	 * @param fieldValueGetter field value getter.
	 * @param stringLiteral string literal.
	 * @param comparisonOperator comparison operator.
	 */
	public ConditionalExpression(IFieldValueGetter fieldValueGetter, String stringLiteral, IComparisonOperator comparisonOperator) {
		this.fieldValueGetter = fieldValueGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}
	
	/**
	 * Fetches the value of the current {@code fieldValueGetter}.
	 * 
	 * @return value of the current {@code fieldValueGetter}.
	 */
	public IFieldValueGetter getFieldValueGetter() {
		return fieldValueGetter;
	}
	
	/**
	 * Fetches the value of the current {@code stringLiteral}.
	 * 
	 * @return value of the current {@code stringLiteral}.
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}
	
	/**
	 * Fetches the value of the current {@code comparisonOperator}.
	 * 
	 * @return value of the current {@code comparisonOperator}.
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
}
