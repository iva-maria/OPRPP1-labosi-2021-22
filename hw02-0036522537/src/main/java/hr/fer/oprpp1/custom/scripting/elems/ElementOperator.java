package hr.fer.oprpp1.custom.scripting.elems;

import java.util.Objects;

/**
 * Class {@code ElementOperator} is used for representation of operators.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class ElementOperator extends Element {
	
	/**
	 * Text value of the current symbol.
	 */
	private final String symbol;
	
	/**
	 * Constructor which sets the property {@code symbol} to the provided value.
	 * 
	 * @param value the provided value of the property.
	 * @throws NullPointerException when the provided {@code symbol} is {@code null}.
	 */
	public ElementOperator(String symbol) {
		if(symbol == null) throw new NullPointerException("The provided symbol cannot be null.");
		
		this.symbol = symbol;
	}
	
	/**
	 * Gets the value of the current symbol.
	 * 
	 * @return the value of the current symbol.
	 */
	public String getSymbol() {
		return this.symbol;
	}
	
	/**
	 * Creates a representation of the {@code symbol} property in the form of a string.
	 * 
	 * @return string representation of the {@code symbol} property.
	 */
	@Override
	public String asText() {
		return this.symbol;
	}
	
	/**
	 * Generates a hashcode for the current operator.
	 * 
	 * @return hashcode of the current operator.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.symbol);
	}
	
	/**
	 * Indicates whether some object is "equal to" this operator by comparing their {@code symbol} properties.
	 * 
	 * @param obj object which is to be compared.
	 * @return {@code true} if the object is "equal to" this operator, {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof ElementOperator)) return false;
		
		ElementOperator other = (ElementOperator)obj;
		return this.symbol.equals(other.symbol);
	}
}
