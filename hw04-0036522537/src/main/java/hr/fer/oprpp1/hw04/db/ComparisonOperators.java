package hr.fer.oprpp1.hw04.db;

/**
 * class {@code ComparisonOperators} defines seven comparison operators 
 * which are used in queries on {@code StudentDatabase}.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class ComparisonOperators {

	/**
	 * Wildcard character.
	 */
	public static final char WILDCARD = '*';
	
	/**
	 * Represents the LESS (<) operator.
	 */
	public static final IComparisonOperator LESS = ((value1, value2) -> value1.compareTo(value2) < 0);
	
	/**
	 * Represents the LESS OR EQUAL (<=) operator.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = ((value1, value2) -> value1.compareTo(value2) <= 0);
	
	/**
	 * Represents the GREATER (>) operator.
	 */
	public static final IComparisonOperator GREATER = ((value1, value2) -> value1.compareTo(value2) > 0);
	
	/**
	 * Represents the GREATER OR EQUAL (>=) operator.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = ((value1, value2) -> value1.compareTo(value2) >= 0);
	
	/**
	 * Represents the EQUAL (==) operator.
	 */
	public static final IComparisonOperator EQUALS = ((value1, value2) -> value1.compareTo(value2) == 0);
	
	/**
	 * Represents the NOT EQUAL (!=) operator.
	 */
	public static final IComparisonOperator NOT_EQUALS = ((value1, value2) -> value1.compareTo(value2) != 0);
	
	/**
	 * Represents the LIKE operator.
	 */
	public static final IComparisonOperator LIKE = ((value1, value2) -> {
		
		int noOfWildcards = 0;
		for(int i = 0; i < value2.length(); i++) {
			if(value2.charAt(i) == WILDCARD) noOfWildcards++;
		}
		
		if(noOfWildcards == 0) {
			return value1.equals(value2);
		} else if(noOfWildcards == 1) {
			int indexOfWildcard = value2.indexOf(WILDCARD);	
			if(indexOfWildcard == 0) {
				return value1.endsWith(value2.substring(1));
			} else if(indexOfWildcard == value2.length() - 1) {
				return value1.startsWith(value2.substring(0, value2.length() - 1));
			} else {
				String[] halves = value2.split("\\*");
				return value1.startsWith(halves[0]) && value1.endsWith(halves[1]);
			}
		} else {
			throw new IllegalArgumentException("Maximum of one wildcard character is allowed.");
		}
	});
	
}
