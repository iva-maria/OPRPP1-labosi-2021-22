package hr.fer.oprpp1.custom.scripting.nodes;

import java.util.Objects;

/**
 * Class {@code TextNode} serves as a node representing a piece of textual data.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class TextNode extends Node {
	
	/**
	 * Textual contents of the current text node.
	 */
	private final String text;
	
	/**
	 * Constructor which sets the {@code text} property to the provided value.
	 * 
	 * @param text the provided value that the text is to be set to.
	 */
	public TextNode(String text) {
		this.text = text;
	}
	
	/**
	 * Fetches the textual contents of the current text node.
	 * 
	 * @return the contents of the current text node.
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * Creates a string representation of the text node.
	 * 
	 * @return current text node in the form of a string.
	 */
	@Override
	public String toString() {
		return this.text;
	}
	
	/**
	 * Generates a hashcode for the current text node.
	 * 
	 * @return hashcode of the current text node.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.text);
	}
	
	/**
	 * Indicates whether some object is "equal to" this text node by comparing their {@code text} arrays.
	 * 
	 * @param obj object which is to be compared.
	 * @return {@code true} if the object is "equal to" this text node, {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof TextNode)) return false;
		
		TextNode other = (TextNode)obj;
		return this.text.equals(other.text);
	}
	
}
