package hr.fer.oprpp1.hw07.gui.charts;

import java.util.List;

/**
 * Class {@code BarChart} is used for collecting information
 * needed for graphic representation of a bar chart.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class BarChart {

	/**
	 * Coordinates of the bar chart points.
	 */
	private List<XYValue> coordinates;

	/**
	 * Title of the x-axis.
	 */
	private String xDescription;

	/**
	 * Title of the y-axis.
	 */
	private String yDescription;

	/**
	 * Smallest y-value.
	 */
	private int yMin;

	/**
	 * Largest y-value.
	 */
	private int yMax;

	/**
	 * Difference between two adjacent y-values.
	 */
	private int ySpacing;

	/**
	 * Constructor which initializes the variables which represent the information
	 * used for graphic representation of a bar chart.
	 *  
	 * @param coordinates coordinates of the bar chart points.
	 * @param xDescription title of the x-axis.
	 * @param yDescription title of the y-axis.
	 * @param yMin smallest value on the y-axis.
	 * @param yMax largest value on the y-axis.
	 * @param ySpacing difference between two adjacent values on the y-axis.
	 */
	public BarChart(List<XYValue> coordinates, String xDescription, String yDescription, int yMin, int yMax, int ySpacing) {
		if(yMax <= yMin) throw new IllegalArgumentException("Maximum value on the y-axis should be larger than the minimum value.");
		if(yMin < 0) throw new IllegalArgumentException("Minimum value on the y-axis should not be less than zero.");
	
		for(XYValue c : coordinates) {
			if(c.getY() < this.yMin) throw new IllegalArgumentException("The value on the y-axis should not be less than the minimum.");
		}
	
		this.coordinates = coordinates;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.yMin = yMin;
		this.yMax = yMax;
		this.ySpacing = ySpacing;
	
		if((yMax - yMin) % ySpacing != 0) this.yMax = yMin + ((yMax - yMin) / ySpacing + 1) * ySpacing;
	}

	/**
	 * Fetches the coordinates of the bar chart points.
	 * 
	 * @return coordinates of the bar chart points.
	 */
	public List<XYValue> getCoordinates() {
		return coordinates;
	}

	/**
	 * Fetches the title of the x-axis.
	 * 
	 * @return title of the x-axis.
	 */
	public String getXDescription() {
		return xDescription;
	}

	/**
	 * Fetches the title of the y-axis.
	 * 
	 * @return title of the y-axis.
	 */
	public String getYDescription() {
		return yDescription;
	}

	/**
	 * Fetches the smallest y-value.
	 * 
	 * @return smallest y-value.
	 */
	public int getYMin() {
		return yMin;
	}

	/**
	 * Fetches the largest y-value.
	 * 
	 * @return largest y-value.
	 */
	public int getYMax() {
		return yMax;
	}

	/**
	 * Fetches the difference between two adjacent y-values.
	 * 
	 * @return difference between two adjacent y-values.
	 */
	public int getYSpacing() {
		return ySpacing;
	}
	
}
