package hr.fer.oprpp1.custom.scripting.parser;

import java.util.Arrays;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.lexer.*;
import hr.fer.oprpp1.custom.scripting.nodes.*;

/**
 * Class {@code SmartScriptParser} represents an implementation of a simple parser.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class SmartScriptParser {	
	
	private SmartScriptLexer lexer;
	
	private ObjectStack stack;
	
	private DocumentNode documentNode;

	public SmartScriptParser(String documentBody) {
		this.lexer = new SmartScriptLexer(documentBody);
		this.stack = new ObjectStack();
		this.documentNode = new DocumentNode();
		
		try {
			parseDocumentBody();
		} catch(Exception e) {
			throw new SmartScriptParserException("An error ocurred during parsing of documentBody.");
		}
	}
	
	/**
	 * Fetches the {@code documentNode} property and returns it.
	 * 
	 * @return {@code documentNode} property.
	 */
	public DocumentNode getDocumentNode() {
		return this.documentNode;
	}
	
	public void parseDocumentBody() {
		this.stack.push(documentNode);
		
		SmartScriptToken currentToken = this.lexer.nextToken();
		while(currentToken.getType() != SmartScriptTokenType.EOF) {
			SmartScriptTokenType currentType = currentToken.getType();
			SmartScriptLexerState currentState = this.lexer.getState();
			
			if(currentState == SmartScriptLexerState.TEXT) {
				if(currentType == SmartScriptTokenType.TAG_BEGINNING) {
					parseTagBeginning();
				} else {
					parseText(currentToken);
				}
			} else if(currentState == SmartScriptLexerState.TAG) {
				parseTag(currentToken);
			}
			
			currentToken = this.lexer.nextToken();
		}
		
		if(this.stack.isEmpty()) throw new SmartScriptParserException("The document contains more {$END$}-s than opened non-empty tags.");
	}
	
	public void parseText(SmartScriptToken currentToken) {
		TextNode textNode = new TextNode((String)currentToken.getValue());
		Node stackNode = (Node)this.stack.peek();
		stackNode.addChildNode(textNode);
	}
	
	public void parseTagBeginning() {
		this.lexer.setState(SmartScriptLexerState.TAG);
	}
	
	public void parseTag(SmartScriptToken currentToken) {
		String currentValue = (String)currentToken.getValue(); 

		if(currentValue.equals("=")) {			
			ArrayIndexedCollection elements = this.fetchElements();
			
			EchoNode echoNode = new EchoNode(Arrays.copyOf(elements.toArray(), elements.size(), Element[].class));
			Node stackNode = (Node)this.stack.peek();
			stackNode.addChildNode(echoNode);
			
		} else if(currentValue.equals("FOR")) {			
			ArrayIndexedCollection elements = this.fetchElements();
			
			ForLoopNode forLoopNode = new ForLoopNode((ElementVariable)elements.get(0), Arrays.copyOfRange(elements.toArray(), 1, elements.size(), Element[].class));
			Node stackNode = (Node)this.stack.peek();
			stackNode.addChildNode(forLoopNode);
			this.stack.push(forLoopNode);
		} else if(currentValue.equals("END")) {
			this.lexer.nextToken();
			this.stack.pop();
		}
		
		this.lexer.setState(SmartScriptLexerState.TEXT);
	}
	
	private ArrayIndexedCollection fetchElements() {
		ArrayIndexedCollection elements = new ArrayIndexedCollection();
		
		SmartScriptToken currentToken = this.lexer.nextToken();
		while(currentToken.getType() != SmartScriptTokenType.TAG_END) {
			SmartScriptTokenType currentType = currentToken.getType();
			
			if(currentType == SmartScriptTokenType.EOF) {
				throw new SmartScriptParserException("The tag was not followed by a correct ending.");
			} else if(currentType == SmartScriptTokenType.VARIABLE) {
				elements.add(new ElementVariable((String)currentToken.getValue()));
			} else if(currentType == SmartScriptTokenType.INTEGER) {
				elements.add(new ElementConstantInteger((int)currentToken.getValue()));
			} else if(currentType == SmartScriptTokenType.DOUBLE) {
				elements.add(new ElementConstantDouble((double)currentToken.getValue()));
			} else if(currentType == SmartScriptTokenType.STRING_TAG) {
				elements.add(new ElementString((String)currentToken.getValue()));
			} else if(currentType == SmartScriptTokenType.FUNCTION) {
				elements.add(new ElementFunction((String)currentToken.getValue()));
			} else if(currentType == SmartScriptTokenType.OPERATOR) {
				elements.add(new ElementOperator((String)currentToken.getValue()));
			}
			
			currentToken = this.lexer.nextToken();
		}
		
		return elements;
	}
	
}
