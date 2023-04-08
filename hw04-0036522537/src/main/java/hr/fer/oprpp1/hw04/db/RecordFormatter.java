package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Class {@code RecordFormatter} is used for a formatted
 * print of database records.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class RecordFormatter {
	
	/**
	 * Creates an array of {@code String} instances which represent rows of output.
	 * 
	 * @param records student records which need to be printed.
	 * @return list of rows ready to be printed.
	 */
	public List<String> format(List<StudentRecord> records) {
		List<String> output = new ArrayList<>(records.size() + 2);
		
		int jmbagColumnWidth = 10;
		int lastNameColumnWidth = 0;
		int firstNameColumnWidth = 0;
		int finalGradeColumnWidth = 1;
		
		for (StudentRecord record : records) {
			if(record.getLastName().length() > lastNameColumnWidth) lastNameColumnWidth = record.getLastName().length();
			if(record.getFirstName().length() > firstNameColumnWidth) firstNameColumnWidth = record.getFirstName().length();
		}
		
		StringBuilder sb1 = new StringBuilder();
		sb1.append("+");
		for(int i = 0; i < jmbagColumnWidth + 2; i++) {
			sb1.append("=");
		}
		sb1.append("+");
		for(int i = 0; i < lastNameColumnWidth + 2; i++) {
			sb1.append("=");
		}
		sb1.append("+");
		for(int i = 0; i < firstNameColumnWidth + 2; i++) {
			sb1.append("=");
		}
		sb1.append("+");
		for(int i = 0; i < finalGradeColumnWidth + 2; i++) {
			sb1.append("=");
		}
		output.add(sb1.toString());
				
		for(StudentRecord record : records) {
			StringBuilder sb2 = new StringBuilder();
			sb2.append("| ");
			sb2.append(record.getJmbag());
			sb2.append(" | ");
			sb2.append(record.getLastName());
			sb2.append(new String(new char[lastNameColumnWidth - record.getLastName().length()]).replace("\0", " "));
			sb2.append(" | ");
			sb2.append(record.getFirstName());
			sb2.append(new String(new char[firstNameColumnWidth - record.getFirstName().length()]).replace("\0", " "));
			sb2.append(" | ");
			sb2.append(record.getFinalGrade());
			sb2.append(" |");
			output.add(sb2.toString());
		}
		
		output.add(sb1.toString());
//		output.add("Records selected: " + records.size());
		
		return output;
	}
	
}
