package hr.fer.oprpp1.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.*;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;

public class SmartScriptParserTest {
	
	private String readExample(int n) {
		try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt")) {
			if(is==null) throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");
		    byte[] data = is.readAllBytes();
		    String text = new String(data, StandardCharsets.UTF_8);
		    return text;
		} catch(IOException ex) {
			throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		}
	}
	
	@Test
	public void testPrimjer1SingleTextNode() {
		DocumentNode root = new SmartScriptParser(readExample(1)).getDocumentNode();
        assertTrue(root.getChild(0) instanceof TextNode);
        assertEquals(root.numberOfChildren(), 1);
	}
	
	@Test
	public void testPrimjer2SingleTextNodeWithEscapedSymbols() {
		DocumentNode root = new SmartScriptParser(readExample(2)).getDocumentNode();
        assertTrue(root.getChild(0) instanceof TextNode);
        assertEquals(root.numberOfChildren(), 1);
	}
	
	@Test
	public void testPrimjer3SingleTextNodeTwoEscapesOneAfterAnother() {
		DocumentNode root = new SmartScriptParser(readExample(3)).getDocumentNode();
		assertTrue(root.getChild(0) instanceof TextNode);
		assertEquals(root.numberOfChildren(), 1);
	}
	
	@Test
	public void testPrimjer4IllegalEscapeException() {
		 assertThrows(SmartScriptParserException.class, () ->  new SmartScriptParser(readExample(4)));
	}
	
	@Test
	public void testPrimjer5IllegalEscapeException() {
		 assertThrows(SmartScriptParserException.class, () ->  new SmartScriptParser(readExample(5)));
	}
	
	@Test
	public void testPrimjer6TextInThreeRows() {
		DocumentNode root = new SmartScriptParser(readExample(6)).getDocumentNode();
		assertTrue(root.getChild(0) instanceof TextNode);
		assertEquals(root.numberOfChildren(), 1);
	}
	
	@Test
	public void testPrimjer7TextInThreeRowsActuallyFour() {
		DocumentNode root = new SmartScriptParser(readExample(7)).getDocumentNode();
		assertTrue(root.getChild(0) instanceof TextNode);
	}
	
	@Test
	public void testPrimjer8TextInThreeRowsIllegalExcape() {
		assertThrows(SmartScriptParserException.class, () ->  new SmartScriptParser(readExample(8)));
	}
	
	@Test
	public void testPrimjer9() {
		assertThrows(SmartScriptParserException.class, () ->  new SmartScriptParser(readExample(9)));
	}
	
	@Test
	public void testPrimjer10() {
		
	}
	
}
