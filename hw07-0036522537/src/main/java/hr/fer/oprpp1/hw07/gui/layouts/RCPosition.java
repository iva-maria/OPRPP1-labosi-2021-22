package hr.fer.oprpp1.hw07.gui.layouts;

import java.util.Objects;

/**
 * Class {@code RCPosition} is used to represent the position of
 * an element within the layout grid.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class RCPosition {
	
	/**
	 * Index of the current row (smallest index is 1).
	 */
	private final int row;
	
	/**
	 * Index of the current column (smallest index is 1).
	 */
	private final int column;
	
	/**
	 * Constructor which sets the {@code row} and {@code column} properties
	 * to the provided values.
	 * 
	 * @param row row index.
	 * @param column column index.
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Fetches the current row index.
	 * 
	 * @return current row index.
	 */
	public int getRow() {
		return this.row;
	}
	
	/**
	 * Fetches the current column index.
	 * 
	 * @return current column index.
	 */
	public int getColumn() {
		return this.column;
	}
	
	/**
	 * Factory method which creates a new {@code RCPosition} instance
	 * using the provided {@code text}.
	 * 
	 * @param text text which is parsed and turned into an {@code RCPosition} instance.
	 * @return instance of the {@code RCPosition} class which has been created from the provided text.
	 */
	public static RCPosition parse(String text) {
		if(text == null) throw new NullPointerException("The provided text cannot be null.");
		
		text.replaceAll("\\s+", "");
		String[] rowAndColumn = text.split(",");
		
		return new RCPosition(Integer.parseInt(rowAndColumn[0]), Integer.parseInt(rowAndColumn[1]));
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(row, column);
	}
	
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(!(obj instanceof RCPosition)) return false;
		
		RCPosition other = (RCPosition) obj;
		return this.row == other.row && this.column == other.column;
	}
	
}
