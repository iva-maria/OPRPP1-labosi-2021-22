package hr.fer.oprpp1.hw07.gui.charts;

/**
 * Class {@code XYValues} represents a pair of x and y coordinates
 * in the coordinate system.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class XYValue {
	
	/**
	 * x-coordinate of the point; position relative to the x-axis.
	 */
	private int x;
	
	/**
	 * y-coordinate of the point; position relative to the y-axis.
	 */
	private int y;
	
	/**
	 * Constructor which sets the x and y coordinates to the provided values.
	 * 
	 * @param x x-coordinate.
	 * @param y y-coordinate.
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Fetches the x-value of the current point.
	 * 
	 * @return x-value of the current point.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Fetches the y-value of the current point.
	 * 
	 * @return y-value of the current point.
	 */
	public int getY() {
		return y;
	}
	
}
