package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudentDatabaseTest {
	
	private StudentDatabase database;
	
	private IFilter acceptAll = record -> true;
	private IFilter acceptNone = record -> false;
	
	@BeforeEach
	public void setUp() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("src/test/resources/database.txt"), StandardCharsets.UTF_8);
		this.database = new StudentDatabase(lines);
	}
	
	@Test
	public void testForJMBAGValidJMBAG() {
		StudentRecord record = this.database.forJMBAG("0000000001");
		
		assertEquals("0000000001", record.getJmbag());
		assertEquals("Akšamović", record.getLastName());
		assertEquals("Marin", record.getFirstName());
		assertEquals(2, record.getFinalGrade());
	}
	
	@Test
	public void testForJMBAGInvalidJMBAG() {
		StudentRecord record = this.database.forJMBAG("0036522537");
		assertNull(record);
	}
	
	@Test
	public void testFilterMethodReturnsAll() {
		int noOfRecords = this.database.filter(acceptAll).size();
		assertEquals(63, noOfRecords);
	}
	
	@Test
	public void testFilterMethodReturnsNone() {
		int noOfRecords = this.database.filter(acceptNone).size();
		assertEquals(0, noOfRecords);
	}
	
}
