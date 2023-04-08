package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

/**
 * Class {@code StudentRecord} represents a database record for a single student.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class StudentRecord {
	
	/**
	 * Unique identifier of the current student.
	 */
	private String jmbag;
	
	/**
	 * Current student's last name.
	 */
	private String lastName;
	
	/**
	 * Current student's first name.
	 */
	private String firstName;
	
	/**
	 * Current student's final grade.
	 */
	private int finalGrade;
	
	/**
	 * Constructor which sets the {@code jmbag}, {@code lastName}, {@code firstName}
	 * and {@code finalGrade} attributes to the provided values.
	 * 
	 * @param jmbag provided value for the {@code jmbag} attribute.
	 * @param lastName provided value for the {@code lastName} attribute.
	 * @param firstName provided value for the {@code firstName} attribute.
	 * @param finalGrade provided value for the {@code finalGrade} attribute.
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Fetches the current student's JMBAG.
	 * 
	 * @return JMBAG of the current student.
	 */
	public String getJmbag() {
		return jmbag;
	}
	
	/**
	 * Fetches the current student's last name.
	 * 
	 * @return last name of the current student.
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Fetches the current student's first name.
	 * 
	 * @return first name of the current student.
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Fetches the current student's final grade.
	 * 
	 * @return final grade of the current student.
	 */
	public int getFinalGrade() {
		return finalGrade;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(jmbag);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof StudentRecord)) return false;
		
		StudentRecord other = (StudentRecord) obj;
		return Objects.equals(jmbag, other.jmbag);
	}
	
}
