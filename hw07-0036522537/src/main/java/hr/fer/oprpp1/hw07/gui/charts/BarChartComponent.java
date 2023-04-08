package hr.fer.oprpp1.hw07.gui.charts;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;

import static java.lang.Math.*;

/**
 * Class {@code BarChartComponent} is used for creating a
 * visuall representation of a bar chart.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class BarChartComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Color of the background on which the bar chart is displayed.
	 */
	private static final Color BACKGROUND_COLOR = Color.decode("#f5f3ed");	
	
    /**
     * Color of the grid used for easier visual interpretation of the bar chart values.
     */
    private static final Color GRID_COLOR = Color.decode("#d1fffd");
    
    /**
     * Color of the bars.
     */
    private static final Color BAR_COLOR = Color.RED;

	/**
	 * Color of the axes of the bar chart.
	 */
    private static final Color AXIS_COLOR = Color.decode("#000000");

    /**
     * An instance of the {@code BarChart} class which contains all information
     * needed in order to construct the bar chart.
     */
    private final BarChart barChart;
    
    /**
     * An array of bar chart points sorted by their x-coordinates in ascending order.
     */
    private List<XYValue> sortedCoordinates;

    /**
     * Constructor which initializes the {@code barChart}.
     * 
     * @param barChart
     */
    public BarChartComponent(BarChart barChart) {
        this.barChart = barChart;
        this.sortedCoordinates = barChart.getCoordinates();
        
        sortedCoordinates.sort((v1, v2) -> Integer.compare(v1.getX(), v2.getX()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        
        FontMetrics fontMetrics = g2D.getFontMetrics();
        Insets insets = getInsets();
        Dimension dimension = getSize();
        
		int chartWidth = dimension.width - insets.left - insets.right;
		int chartHeight = dimension.height - insets.top - insets.bottom;
		
        g2D.setColor(BACKGROUND_COLOR);
		g2D.fillRect(insets.left, insets.top, chartWidth, chartHeight);
		
        int textHeight = fontMetrics.getHeight();
        
        int totalYAxisDistance = textHeight * 2 + fontMetrics.stringWidth(Integer.toString(barChart.getYMax())) * 2;
        int totalXAxisDistance = textHeight * 4;
        int arrowSpace = textHeight / 2;

        XYValue centerCoordinates = new XYValue(totalYAxisDistance, chartHeight - (totalXAxisDistance + arrowSpace));
        int xAxisLength = chartWidth - totalXAxisDistance - textHeight;
        int yAxisLength = chartHeight - totalYAxisDistance - textHeight * 2;

        drawGrid(g2D, chartWidth, chartHeight, xAxisLength, yAxisLength, arrowSpace, centerCoordinates);
        drawBars(g2D, textHeight, xAxisLength, yAxisLength, centerCoordinates);
        drawAxes(g2D, fontMetrics, chartWidth, chartHeight, textHeight, totalXAxisDistance, totalYAxisDistance, xAxisLength, yAxisLength, arrowSpace, centerCoordinates);
    }

    /**
     * Draws the grid which is displayed behind the bars and is used
     * for easier interpretation of the values.
     * 
     * @param g2D graphics component.
     * @param chartWidth width of the bar chart which needs to be displayed.
     * @param chartHeight height of the chart that needs to be displayed.
     * @param xAxisLength length of the x-axis.
     * @param yAxisLength length of the y-axis.
     * @param arrowSpace height and width of the square space needed to display the arrow points.
     * @param centerCoordinates coordinates of the center of the coordinate system.
     */
    private void drawGrid(Graphics2D g2D, int chartWidth, int chartHeight, int xAxisLength, int yAxisLength, int arrowSpace, XYValue centerCoordinates) {
        int centerX = centerCoordinates.getX();
        int centerY = centerCoordinates.getY();
        int coordinateValue, offset;
        
        g2D.setColor(GRID_COLOR);
        
        for (int i = 1, size = barChart.getCoordinates().size(); i <= size; i++) {
            offset = (int)(i * 1. / size * xAxisLength);
            coordinateValue = centerX + offset;
            g2D.drawLine(coordinateValue, centerY, coordinateValue, arrowSpace);
        }

        for (int step = this.barChart.getYSpacing(), i = 0, amount = this.barChart.getYMax() / step; i <= amount; i++) {
            offset = (int)(i * 1. / amount * yAxisLength);
            coordinateValue = centerY - offset;
            g2D.drawLine(centerX, coordinateValue, chartWidth - arrowSpace, coordinateValue);
        }
    }    

    /**
     * Draws the bars as a visual representation of the data.
     * 
     * @param g2D graphics component.
     * @param textHeight height of the text in the current font.
     * @param xAxisLength length of the x-axis.
     * @param yAxisLength length of the y-axis.
     * @param centerCoordinates coordinates of the center of the coordinate system.
     */
    private void drawBars(Graphics2D g2D, int textHeight, int xAxisLength, int yAxisLength, XYValue centerCoordinates) {
        int centerX = centerCoordinates.getX();
        int centerY = centerCoordinates.getY();
        
        g2D.setColor(BACKGROUND_COLOR);
        g2D.setStroke(new BasicStroke(3));
        for (int i = 0, size = sortedCoordinates.size(), amount = this.barChart.getYMax(), minY = this.barChart.getYMin(), width = (int)(1. / size * xAxisLength); i < size; i++) {
            int xOffset = centerX + (int)(i * 1. / size * xAxisLength);
            int yOffset = (int)((sortedCoordinates.get(i).getY() - minY) * 1. / (amount - minY) * yAxisLength);

            g2D.setColor(BAR_COLOR);
            g2D.fillRect(xOffset, centerY - yOffset, width, yOffset);

            g2D.setColor(BACKGROUND_COLOR);
            g2D.drawRect(xOffset, centerY - yOffset, width, yOffset);
        }
    }

    /**
     * Draws the coordinate system x and y axes.
     * 
     * @param g2D graphics component.
     * @param fontMetrics font metrics of the currently used font.
     * @param chartWidth width of the bar chart which needs to be displayed.
     * @param chartHeight height of the bar chart which needs to be displayed.
     * @param textHeight height of the text in the current font.
     * @param totalXAxisDistance distance of the x-axis from the bottom of the window.
     * @param totalYAxisDistance distance of the y-axis from the bottom of the window.
     * @param xAxisLength length of the x-axis.
     * @param yAxisLength length of the y-axis.
     * @param arrowSpace height and width of the square space needed to display the arrow points.
     * @param centerCoordinates coordinates of the center of the coordinate system.
     */
    private void drawAxes(Graphics2D g2D, FontMetrics fontMetrics, int chartWidth, int chartHeight, int textHeight, int totalXAxisDistance, int totalYAxisDistance,
                          int xAxisLength, int yAxisLength, int arrowSpace, XYValue centerCoordinates) {
        double halfTheReferentSize = (int)ceil(textHeight / 4.);
        int centerX = centerCoordinates.getX();
        int centerY = centerCoordinates.getY();
        
        g2D.setColor(AXIS_COLOR);
        g2D.setStroke(new BasicStroke(2));
        
        g2D.drawLine(centerX, centerY, chartWidth - arrowSpace, centerY);
        g2D.drawLine(chartWidth - (int)halfTheReferentSize, centerY,
        		(int)(chartWidth - arrowSpace + textHeight / 2 * cos(PI / 4 + PI / 2)),
        		(int)(centerY + textHeight / 2 * sin(PI / 4 + PI / 2)));
        g2D.drawLine(chartWidth - (int)halfTheReferentSize, centerY,
        		(int)(chartWidth - arrowSpace + textHeight / 2 * cos(PI * 3 / 4 + PI / 2)),
        		(int)(centerY + textHeight / 2 * sin(PI * 3/ 4 + PI / 2)));
        
        g2D.drawLine(centerX, centerY, centerX, arrowSpace);
        g2D.drawLine(centerX, (int) Math.ceil(textHeight / 4.),
        		(int)(centerX + arrowSpace * Math.cos(PI / 4)), (int)(halfTheReferentSize + textHeight * sin(Math.PI / 4)));
        g2D.drawLine(centerX, (int) Math.ceil(textHeight / 4.),
        		(int)(centerX + arrowSpace * cos(PI * 3 / 4)), (int)(halfTheReferentSize + textHeight * sin(Math.PI * 3 / 4)));

        Font font = fontMetrics.getFont();
        
        g2D.setFont(font.deriveFont(Font.BOLD));

        int xAxisDescriptionX = (chartWidth + centerX) / 2;
        g2D.drawString(this.barChart.getXDescription(),
        				xAxisDescriptionX - fontMetrics.stringWidth(barChart.getXDescription()) / 2, 
        				(int)(centerY + 3.5 * textHeight));

        //okomiti ispis teksta
        AffineTransform oldTransform = g2D.getTransform();
        AffineTransform at = AffineTransform.getQuadrantRotateInstance(3);
        g2D.setTransform(at);
        int yAxisDescriptionY = (chartHeight + totalXAxisDistance + arrowSpace) / 2;
        g2D.drawString(this.barChart.getYDescription(),
        				- (yAxisDescriptionY + fontMetrics.stringWidth(barChart.getYDescription())),
        				(int)(totalXAxisDistance - 2.5 * textHeight));
        
        g2D.setTransform(oldTransform);
        g2D.setFont(font);

        for (int i = 1, size = this.barChart.getCoordinates().size(); i <= size; i++) {
            int offset = (int)(i * 1. / size * xAxisLength);
            g2D.setStroke(new BasicStroke(1.5f));
            g2D.drawLine(centerX + offset, centerY, centerX + offset, centerY + arrowSpace);

            String number = Integer.toString(sortedCoordinates.get(i - 1).getX());
            g2D.setFont(font.deriveFont(Font.BOLD));
            g2D.drawString(number, (int)(centerX + ((i * 2. - 1)/(i * 2)) * offset - fontMetrics.stringWidth(number) / 2), centerY + textHeight);
        }

        int yNumberOffset = (int) Math.ceil(textHeight / 4.);
        for (int step = this.barChart.getYSpacing(), i = 0, y = this.barChart.getYMin(), amount = this.barChart.getYMax() / step; i <= amount; i++) {
            int yOffset = centerY - (int)(i * 1. / amount * yAxisLength);
            g2D.drawLine(centerX, yOffset, centerX - arrowSpace, yOffset);
            
            String number = Integer.toString(y + step * i);
            g2D.setFont(font.deriveFont(Font.BOLD));
            g2D.drawString(number, totalYAxisDistance - textHeight - fontMetrics.stringWidth(number), yOffset + yNumberOffset);
        }
    }

}
