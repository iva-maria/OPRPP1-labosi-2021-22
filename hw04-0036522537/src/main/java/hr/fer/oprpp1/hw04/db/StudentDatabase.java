package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class {@code StudentDatabase} represents a database which stores {@code StudentRecord} instances.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class StudentDatabase {
	
	/**
	 * An internal list of all student records in the current database.
	 */
	private List<StudentRecord> studentRecords;
	
	/**
	 * An internal map used as an index for fast retrieval of student records by their jmbags.
	 */
	private Map<String, StudentRecord> indexJMBAG;
	
	/**
	 * A constructor which creates an internal list of student records and index for their retrieval
	 * by jmbags from the provided list of {@code String} objects.
	 * 
	 * @param databaseRows list of {@code String} objects which will represent student records.
	 * @throws IllegalArgumentException if there are duplicate JMBAGs in the provided file or
	 * if the final grade for a record is not an integer between 1 and 5.
	 */
	public StudentDatabase(List<String> databaseRows) {
		
		this.studentRecords = new ArrayList<>(databaseRows.size());
		this.indexJMBAG = new HashMap<>(databaseRows.size());
		
		for(String row : databaseRows) {
			StudentRecord newRecord = this.parseStudentRecord(row);
			
			StudentRecord currentRecord = indexJMBAG.get(newRecord.getJmbag());
			if(currentRecord != null) throw new IllegalArgumentException("There is already an input with the provided JMBAG in the database.");
			
			int newFinalGrade = newRecord.getFinalGrade();
			if(newFinalGrade < 1 || newFinalGrade > 5) throw new IllegalArgumentException("The final grade must be an integer between 1 and 5.");
			
			this.studentRecords.add(newRecord);
			this.indexJMBAG.put(newRecord.getJmbag(), newRecord);
		}
	}
	
	/**
	 * Obtains the requested record using its {@code jmbag} as index in O(1) complexity.
	 * 
	 * @param jmbag JMBAG of the requested student record.
	 * @return {@code StudentRecord} instance with the provided JMBAG is if exists, {@code null} otherwise.
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return this.indexJMBAG.get(jmbag);
	}
	
	/**
	 * Calls {@code accepts()} method from the functional interface {@code IFilter}
	 * for each {@code StudentRecord} instance in its internal list. Each record for 
	 * which the {@code accepts()} method returns {@code true} is added to a new list
	 * of filtered records which is then returned.
	 * 
	 * @param filter the filter used for filtering all records of the current database.
	 * @return list of records for which the {@code accepts()} method of the {@code IFilter}
	 * interface returned {@code true}.
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> filteredStudentRecords = new ArrayList<>();
		
		for(StudentRecord record : studentRecords) {
			if(filter.accepts(record)) filteredStudentRecords.add(record);
		}
		
		return filteredStudentRecords;
	}
	
	/**
	 * Method used for parsing of a single row of input text and creating 
	 * a {@code StudentRecord} out of its components.
	 * 
	 * @param row a single row of text.
	 * @return new {@code StudentRecord} instance whose attributes consist of input row components.
	 */
	private StudentRecord parseStudentRecord(String row) {
		String[] attributes = row.split("\\t");
		
		int finalGrade;
		try {
			finalGrade = Integer.parseInt(attributes[3]);
		} catch(NumberFormatException e) {
			throw new NumberFormatException(e.getMessage());
		}
		
		if(finalGrade < 1 || finalGrade > 5) throw new IllegalArgumentException("Final grade should be a whole number between 1 and 5.");
		
		return new StudentRecord(attributes[0], attributes[1], attributes[2], finalGrade);
	}
	
}
