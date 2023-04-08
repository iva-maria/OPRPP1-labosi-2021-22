package hr.fer.oprpp1.hw04.db;

/**
 * Class {@code FieldValueGetters} represents {@code IFieldValueGetter} instances
 * used for obtaining the requested field values.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class FieldValueGetters {
	
	/**
	 * Getter of the {@code firstName} field value.
	 */
	public static final IFieldValueGetter FIRST_NAME = StudentRecord::getFirstName;
	
	/**
	 * Getter of the {@code lastName} field value.
	 */
	public static final IFieldValueGetter LAST_NAME = StudentRecord::getLastName;
	
	/**
	 * Getter of the {@code jmbag} field value.
	 */
	public static final IFieldValueGetter JMBAG = StudentRecord::getJmbag;
}
