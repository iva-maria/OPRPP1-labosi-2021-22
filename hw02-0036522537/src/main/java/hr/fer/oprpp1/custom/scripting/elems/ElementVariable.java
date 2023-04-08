package hr.fer.oprpp1.custom.scripting.elems;

import java.util.Objects;

/**
 * Class {@code ElementVariable} is used for representation of strings.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class ElementVariable extends Element {

	/**
	 * Name of the current variable.
	 */
	private final String name;
	
	/**
	 * Constructor which sets the property {@code name} to the provided value.
	 * 
	 * @param name the provided value of the property.
	 * @throws NullPoointerException when the provided {@code name} is null.
	 */
	public ElementVariable(String name) {
		if(name == null) throw new NullPointerException("The provided name cannot be null.");
		
		this.name = name;
	}
	
	/**
	 * Fetches the name of the current variable.
	 * 
	 * @return the name of the current variable.
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
	 * Generates a hashcode for the current variable.
	 * 
	 * @return hashcode of the current variable.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.name);
	}
	
	/**
	 * Indicates whether some object is "equal to" this variable by comparing their {@code name} properties.
	 * 
	 * @param obj object which is to be compared.
	 * @return {@code true} if the object is "equal to" this variable, {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof ElementVariable)) return false;
		
		ElementVariable other = (ElementVariable)obj;
		return this.name.equals(other.name);
	}
}
