package hr.fer.oprpp1.hw07.gui.charts;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Class {@code BarChartDemo} represents a command-line application which
 * draws a bar chart using the information provided through a text file.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class BarChartDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * An instance of the {@code BarChart} class which contains all information
     * needed in order to construct the bar chart.
	 */
	private BarChart barChart;

	/**
	 * Path to the file containing information needed for initializing the bar chart.
	 */
	private Path file;

	/**
	 * Constructor which sets the {@code barChart} and {@code file}
	 * properties to the provided values.
	 * 
	 * @param barChart instance of the {@code BarChart} class which contains all information
     * needed in order to construct the bar chart.
	 * @param file path to the file containing information needed for initializing the bar chart.
	 */
	public BarChartDemo(BarChart barChart, Path file) {
		this.barChart = barChart;
		this.file = file;
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Bar chart");
		setSize(1080, 720);
		
		initGUI();
	}

	/**
	 * Initializes the bar chart graphic user interface.
	 */
	private void initGUI() {
		add(new JLabel(file.toString(), SwingConstants.CENTER), BorderLayout.PAGE_START);
		add(new BarChartComponent(barChart));
	}
	
	/**
	 * Parses the provided text file in order to create a new {@code BarChart} instance.
	 * 
	 * @param lines list containing lines of the text file.
	 * @return new {@code BarChart} instance created using the information from the text file.
	 */
	private static BarChart parse(List<String> lines) {
		String xDescription = lines.get(0);
		String yDescription = lines.get(1);
		
		List<XYValue> xyValues = new ArrayList<>();
		String[] parts = lines.get(2).split("\\s+");
		for (String part : parts) {
			String[] coordinates = part.split(",");
			xyValues.add(new XYValue(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])));	
		}
		
		int yMin = Integer.parseInt(lines.get(3));
		int yMax = Integer.parseInt(lines.get(4));
		int ySpacing = Integer.parseInt(lines.get(5));
		
		return new BarChart(xyValues, xDescription, yDescription, yMin, yMax, ySpacing);
	}

	/**
	 * Main method from which the execution of the application starts.
	 * 
	 * @param args a string array of command-line arguments.
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Path currentDir = Paths.get(".").toAbsolutePath().normalize();
		Path file = currentDir.resolve(Paths.get(args[0])).normalize();
		
		List<String> lines = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(file.toFile()))) {
			int counter = 6;
			String line;
			while ((line = br.readLine()) != null && counter > 0) {
				lines.add(line);
				counter--;
			}
		}
		BarChart barChart = parse(lines);
		
		SwingUtilities.invokeLater(() -> new BarChartDemo(barChart, file).setVisible(true));	
	}

}
