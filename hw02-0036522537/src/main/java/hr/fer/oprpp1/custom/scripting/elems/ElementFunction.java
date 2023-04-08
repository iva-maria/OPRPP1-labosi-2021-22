package hr.fer.oprpp1.custom.scripting.elems;

import java.util.Objects;

/**
 * Class {@code ElementFunction} is used for representation of functions by their names.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class ElementFunction extends Element {

	/**
	 * Name of the current function.
	 */
	private final String name;
	
	/**
	 * Constructor which sets the property {@code name} to the provided value.
	 * 
	 * @param name the provided name for the property.
	 * @throws NullPointerException when the provided {@code name} is {@code null}.
	 */
	public ElementFunction(String name) {
		if(name == null) throw new NullPointerException("The provided name cannot be null.");
		
		this.name = name;
	}
	
	/**
	 * Gets the name of the current function.
	 * 
	 * @return the name of the current function.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Creates a representation of the {@code name} property in the form of a string.
	 * 
	 * @return string representation of the {@code name} property.
	 */
	@Override
	public String asText() {
		return this.name;
	}
	
	/**
	 * Generates a hashcode for the current function.
	 * 
	 * @return hashcode of the current function.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.name);
	}
	
	/**
	 * Indicates whether some object is "equal to" this function by comparing their {@code name} properties.
	 * 
	 * @param obj object which is to be compared.
	 * @return {@code true} if the object is "equal to" this function, {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof ElementFunction)) return false;
		
		ElementFunction other = (ElementFunction)obj;
		return this.name.equals(other.name);
	}
}
