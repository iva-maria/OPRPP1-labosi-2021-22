package hr.fer.oprpp1.hw04.db;

/**
 * Functional interface {@code IFilter} acts as a filter for {@code StudentRecord}
 * instances of the {@code StudentDatabase} database.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public interface IFilter {

	/**
	 * Determines whether the provided {@code record} shall be accepted by the filter.
	 * 
	 * @param record a record which needs to be filtered.
	 * @return {@code true} if the record is accepted, {@code false} otherwise.
	 */
	public boolean accepts(StudentRecord record);
	
}
