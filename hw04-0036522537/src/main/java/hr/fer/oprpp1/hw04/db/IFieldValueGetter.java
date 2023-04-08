package hr.fer.oprpp1.hw04.db;

/**
 * Interface {@code IFieldValueGetter} represents a strategy whose responsibility is
 * to obtain a requested field value from given {@code StudentRecord} instance.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public interface IFieldValueGetter {

	/**
	 * Fetches the requested field value from the provided {@code StudentRecord}.
	 * 
	 * @param record {@code StudentRecord} instance from which the specific field value shall be obtained.
	 * @return requested field value from the provided record.
	 */
	public String get(StudentRecord record);
	
}
