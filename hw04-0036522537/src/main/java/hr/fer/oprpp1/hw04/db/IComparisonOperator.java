package hr.fer.oprpp1.hw04.db;

/**
 * Interface {@code IComparisonOperator} represents a strategy which determines
 * whether some two values satisfy a certain operation of comparison.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public interface IComparisonOperator {

	/**
	 * Determines whether the two provided values, which are string literals and in
	 * no case string names, satisfy a certain operation of comparison.
	 * 
	 * @param value1 first value to be compared.
	 * @param value2 second value to be compared.
	 * @return {@code true} if the two provided values satisfy the comparison operation, {@code false} otherwise.
	 */
	public boolean satisfied(String value1, String value2);
	
}
