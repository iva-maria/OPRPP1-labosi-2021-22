package hr.fer.oprpp1.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementOperator;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**
 * Class {@code ForLoop Node} serves as a node representing a single for-loop construct.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class ForLoopNode extends Node {

	/**
	 * Property which represents a for-loop variable.
	 */
	private ElementVariable variable;
	
	/**
	 * Property which represents the for-loop variable starting value.
	 */
	private Element startExpression;
	
	/**
	 * Property which represents the for-loop variable ending value.
	 */
	private Element endExpression;
	
	/**
	 * Property which represents the for-loop variable step value.
	 */
	private Element stepExpression;
	
	/**
	 * Constructor which sets the values of {@code variable}, {@code startExpression}, {@code endExpression} 
	 * and sometimes {@code stepExpression} to the provided values.
	 * 
	 * @param variable the value which the {@code variable} property is to be set to.
	 * @param elements two or three values which {@code startExpression}, {@code endExpression} and possibly
	 * {@code stepExpression} are to be set to.
	 * @throws IllegalArgumentException when there are less than two or more than three provided {@code elements}, or if
	 * they are of the wrong type.
	 * @throws NullPointerException when the provided value for {@code variable} or any of the first two {@code elements}
	 * is {@code null}.
	 */
	public ForLoopNode(ElementVariable variable, Element ...elements) {
		if(elements.length < 2 || elements.length > 3) throw new IllegalArgumentException("There should be two or three elements.");
		
		if(variable == null || elements[0] == null || elements[1] == null) throw new NullPointerException("Arguments cannot be null.");
		
		for(Element element : elements) {
			if(element instanceof ElementFunction || element instanceof ElementOperator) {
				throw new IllegalArgumentException("Elements can be variables, numbers, or strings.");
			}
		}
		
		this.variable = variable;
		this.startExpression = elements[0];
		this.endExpression = elements[1];
		
		if(elements.length == 3) {
			this.stepExpression = elements[2];
		} else {
			this.stepExpression = null;
		}
	}
	
	/**
	 * Fetches the {@code variable} property of the current node.
	 * 
	 * @return value of the {@code variable} property of the current node.
	 */
	public ElementVariable getVariable() {
		return this.variable;
	}
	
	/**
	 * Fetches the {@code startExpression} property of the current node.
	 * 
	 * @return value of the {@code startExpression} property of the current node.
	 */
	public Element getStartExpression() {
		return this.startExpression;
	}
	
	/**
	 * Fetches the {@code endExpression} property of the current node.
	 * 
	 * @return value of the {@code endExpression} property of the current node.
	 */
	public Element getEndExpression() {
		return this.endExpression;
	}
	
	/**
	 * Fetches the {@code stepExpression} property of the current node.
	 * 
	 * @return value of the {@code stepExpression} property of the current node.
	 */
	public Element getStepExpression() {
		return this.stepExpression;
	}
	
	/**
	 * Represents the current for-loop node in the form of a string.
	 * 
	 * @return the current for-loop node in the form of a string.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("{$ FOR ");
		sb.append(variable.asText());
		sb.append(" ");
		sb.append(this.getStartExpression().asText());
		sb.append(" ");
		sb.append(this.getEndExpression().asText());
		
		if(this.getStepExpression() != null) {
			sb.append(" ");
			sb.append(this.getStepExpression().asText());
		}
		
		sb.append(" $}");
		
		for(int i = 0, noOfChildren = this.numberOfChildren(); i < noOfChildren; i++) {
			Node childNode = this.getChild(i);
			sb.append(childNode.toString());
		}
		
		sb.append("{$END$}");
		
		return sb.toString();
	}
	
	/**
	 * Generates a hashcode for the current for-loop node.
	 * 
	 * @return hashcode of the current for-loop node.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.variable, this.startExpression, this.endExpression, this.stepExpression);
	}
	
	/**
	 * Indicates whether some object is "equal to" this for-loop node by comparing their {@code variable},
	 * {@code startExpression}, {@code endExpression} and {@code stepExpression} properties.
	 * 
	 * @param obj object which is to be compared.
	 * @return {@code true} if the object is "equal to" this for-loop node, {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof ForLoopNode)) return false;
		
		ForLoopNode other = (ForLoopNode)obj;
		return this.variable == other.variable
				&& this.startExpression == other.startExpression
				&& this.endExpression == other.endExpression
				&& this.stepExpression == other.stepExpression;
	}
	
}
