package hr.fer.oprpp1.custom.scripting.nodes;

import java.util.Arrays;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/**
 * Class {@code EchoNode} acts as a node which represents a commands which generats some textual output dynamically.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class EchoNode extends Node {
	
	/**
	 * An array of {@code Element} instances.
	 */
	private final Element[] elements;
	
	/**
	 * Constructor which sets the {@code elements} array to the provided value.
	 * 
	 * @param elements the provided array of elements.
	 * @throws NullPointerException when the provided array is {@code null}.
	 */
	public EchoNode(Element[] elements) {
		if(elements == null) throw new NullPointerException("The provided array of elements cannot be null.");
		
		this.elements = elements;
	}
	
	/**
	 * Fetches the {@code elements} array.
	 * 
	 * @return {@code elements} array.
	 */
	public Element[] getElements() {
		return this.elements;
	}
	
	/**
	 * Creates a string representation of the echo node.
	 * 
	 * @return current echo node in the form of a string.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("{$= ");
		
		for(Element element : this.elements) {
			sb.append(element.asText());
			sb.append(" ");
		}
		
		sb.append("$}");
		
		return sb.toString();
	}
	
	/**
	 * Generates a hashcode for the current echo node.
	 * 
	 * @return hashcode of the current echo node.
	 */
	@Override
	public int hashCode() {
		return Arrays.hashCode(this.elements);
	}
	
	/**
	 * Indicates whether some object is "equal to" this echo node by comparing their {@code elements} arrays.
	 * 
	 * @param obj object which is to be compared.
	 * @return {@code true} if the object is "equal to" this echo node, {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof EchoNode)) return false;
		
		EchoNode other = (EchoNode)obj;
		return Arrays.equals(this.elements, other.elements);
	}
}
