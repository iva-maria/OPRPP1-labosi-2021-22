package hr.fer.oprpp1.custom.scripting.elems;

import java.util.Objects;

/**
 * Class {@code ElementConstantInteger} is used for representation of integers.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class ElementConstantInteger extends Element {
	
	/**
	 * Value of the current integer.
	 */
	private final int value;
	
	/**
	 * Constructor which sets the property {@code value} to the provided value.
	 * 
	 * @param value the provided value of the property.
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * Gets the value of the current number.
	 * 
	 * @return the value of the current number.
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Creates a representation of the {@code value} property in the form of a string.
	 * 
	 * @return string representation of the {@code value} property.
	 */
	@Override
	public String asText() {
		return Integer.toString(this.value);
	}
	
	/**
	 * Generates a hashcode for the current number.
	 * 
	 * @return hashcode of the current number.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.value);
	}
	
	/**
	 * Indicates whether some object is "equal to" this variable by comparing their {@code value} properties.
	 * 
	 * @param obj object which is to be compared.
	 * @return {@code true} if the object is "equal to" this variable, {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof ElementConstantInteger)) return false;
		
		ElementConstantInteger other = (ElementConstantInteger)obj;
		return this.value == other.value;
	}
}
