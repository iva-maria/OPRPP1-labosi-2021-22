package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class {@code StudentDB} is a command-line application used
 * for creating queries over a database of students.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class StudentDB {
	
	/**
	 * Function which obtains queries from the command line,
	 * filters the database entries accordingly and prints them
	 * on the standard output.
	 * 
	 * @param args an array of command line arguments.
	 * @throws IOException when there is an error in reading the database file.
	 */
	public static void main(String[] args) throws IOException {
		List<String> rows = null;
		
		try {
			rows = Files.readAllLines(Paths.get("src/main/resources/database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		StudentDatabase database = new StudentDatabase(rows);
		
		Scanner sc = new Scanner(System.in);
		
		String input;
		while(true) {
			System.out.print("> ");
			
			input = sc.nextLine().trim();
			
			if(input.equalsIgnoreCase("exit")) {
				System.out.println("Goodbye!");
				break;
			}
			
			String query = input.substring(5);
			QueryParser parser = new QueryParser(query);
			
			List<StudentRecord> queryRecords;
			StudentRecord queryRecord;
			if(parser.isDirectQuery()) {
				System.out.println("Using index for record retrieval.");
				
				queryRecords = new ArrayList<>();
				queryRecord = database.forJMBAG(parser.getQueriedJMBAG());
				if(queryRecord != null) queryRecords.add(queryRecord);
			} else {
				QueryFilter filter = new QueryFilter(parser.getQuery());
				queryRecords = database.filter(filter);
			}
			
			if(queryRecords.size() > 0) {
				RecordFormatter formatter = new RecordFormatter();
				List<String> output = formatter.format(queryRecords);
				output.forEach(System.out::println);
			}
			System.out.println("Records selected: " + queryRecords.size());
			System.out.println();
		}
		
		sc.close();
	}
	
//	private static void formattedPrint(List<StudentRecord> records) {
//		int jmbagColumnWidth = 10;
//		int lastNameColumnWidth = 0;
//		int firstNameColumnWidth = 0;
//		int finalGradeColumnWidth = 1;
//		
//		for (StudentRecord record : records) {
//			if(record.getLastName().length() > lastNameColumnWidth) lastNameColumnWidth = record.getLastName().length();
//			if(record.getFirstName().length() > firstNameColumnWidth) firstNameColumnWidth = record.getFirstName().length();
//		}
//		
//		StringBuilder sb1 = new StringBuilder();
//		sb1.append("+");
//		for(int i = 0; i < jmbagColumnWidth + 2; i++) {
//			sb1.append("=");
//		}
//		sb1.append("+");
//		for(int i = 0; i < lastNameColumnWidth + 2; i++) {
//			sb1.append("=");
//		}
//		sb1.append("+");
//		for(int i = 0; i < firstNameColumnWidth + 2; i++) {
//			sb1.append("=");
//		}
//		sb1.append("+");
//		for(int i = 0; i < finalGradeColumnWidth + 2; i++) {
//			sb1.append("=");
//		}
//		sb1.append("+\n");
//		
//		StringBuilder sb2 = new StringBuilder();
//		for(StudentRecord record : records) {
//			sb2.append("| ");
//			sb2.append(record.getJmbag());
//			sb2.append(" | ");
//			sb2.append(record.getLastName());
//			sb2.append(new String(new char[lastNameColumnWidth - record.getLastName().length()]).replace("\0", " "));
//			sb2.append(" | ");
//			sb2.append(record.getFirstName());
//			sb2.append(new String(new char[firstNameColumnWidth - record.getFirstName().length()]).replace("\0", " "));
//			sb2.append(" | ");
//			sb2.append(record.getFinalGrade());
//			sb2.append(" |\n");
//		}
//		
//		System.out.print(sb1.toString());
//		System.out.print(sb2.toString());
//		System.out.print(sb1.toString());
//	}

}
