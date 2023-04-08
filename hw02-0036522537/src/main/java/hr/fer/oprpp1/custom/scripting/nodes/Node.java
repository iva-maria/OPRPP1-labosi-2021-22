package hr.fer.oprpp1.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * Class {@code Node} is a base class for all graph nodes.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class Node {
	
	private ArrayIndexedCollection children;
	
	/**
	 * Default constructor.
	 */
	public Node() {
		this.children = null;
	}

	/**
	 * Adds given {@code child} to an internally managed collection of children.
	 * 
	 * @param child the node which is to be added.
	 */
	public void addChildNode(Node child) {
		if(this.children == null) this.children = new ArrayIndexedCollection();
		this.children.add(child);
	}
	
	/**
	 * Determines the number of nodes that are the children of current node.
	 * 
	 * @return the number of elements stored in the {@code children} array.
	 */
	public int numberOfChildren() {
		if(this.children == null) return 0;
		return this.children.size();
	}
	
	/**
	 * Fetches the child node stored at the provided position {@code index} in the {@code children} array.
	 * 
	 * @param index the index of the node that is to be fetched.
	 * @return the child node stored at the provided {@code index}.
	 * @throws NullPointerException when there are no children of the current node.
	 * @throws IndexOutOfBoundsException when the provided {@code index} is not between 0 and {@code numberOfChildren}-1.
	 */
	public Node getChild(int index) {
		if(this.children == null) throw new NullPointerException("The node has no children.");
		if(index < 0 || index >= this.numberOfChildren()) throw new IndexOutOfBoundsException("The index must be a number between 0 and numberOfChildren-1.");
		
		return (Node)this.children.get(index);
	}
	
	/**
	 * Generates a hashcode for the current node.
	 * 
	 * @return hashcode of the current node.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.children);
	}
	
	/**
	 * Indicates whether some object is "equal to" this node by comparing their {@code children} arrays.
	 * 
	 * @param obj object which is to be compared.
	 * @return {@code true} if the object is "equal to" this node, {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof Node)) return false;
		
		Node other = (Node)obj;
		return Objects.equals(this.children, other.children);
	}
}
