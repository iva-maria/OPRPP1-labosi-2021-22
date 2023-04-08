package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Class {@code DocumentNode} serves as a node representing an entire document.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class DocumentNode extends Node {
	
	/**
	 * Creates a string representation of the document node.
	 * 
	 * @return current document node in the form of a string.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0, noOfChildren = this.numberOfChildren(); i < noOfChildren; i++) {
			Node childNode = this.getChild(i);
			sb.append(childNode.toString());
		}
		
		return sb.toString();
	}

}
