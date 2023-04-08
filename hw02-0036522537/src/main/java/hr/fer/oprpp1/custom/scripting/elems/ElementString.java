package hr.fer.oprpp1.custom.scripting.elems;

import java.util.Objects;

/**
 * Class {@code ElementString} is used for representation of strings by their names.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class ElementString extends Element {
	
	/**
	 * Value of the current string.
	 */
	private final String value;
	
	/**
	 * Constructor which sets the property {@code value} to the provided value.
	 * 
	 * @param value the provided value of the property.
	 * @throws NullPointerException when the provided {@code value} is {@code null}.
	 */
	public ElementString(String value) {
		if(value == null) throw new NullPointerException("The provided value cannot be null.");
		
		this.value = value;
	}
	
	/**
	 * Gets the value of the current string.
	 * 
	 * @return the value of the current string.
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * Creates a representation of the {@code value} property in the form of a string.
	 * 
	 * @return string representation of the {@code value} property.	
	 */
	@Override
	public String asText() {
		return this.value;
	}
	
	/**
	 * Generates a hashcode for the current string.
	 * 
	 * @return hashcode of the current string.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.value);
	}
	
	/**
	 * Indicates whether some object is "equal to" this string by comparing their {@code value} properties.
	 * 
	 * @param obj object which is to be compared.
	 * @return {@code true} if the object is "equal to" this string, {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof ElementString)) return false;
		
		ElementString other = (ElementString)obj;
		return this.value.equals(other.value);
	}
}
