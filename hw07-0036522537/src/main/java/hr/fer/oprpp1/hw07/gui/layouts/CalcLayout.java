package hr.fer.oprpp1.hw07.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Class {@code CalcLayout} represents a layout manager for
 * the calculator application.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class CalcLayout implements LayoutManager2 {
	
	/**
	 * Number of rows for the current layout grid.
	 * This is a read-only property of the grid.
	 */
	private static final int ROWS = 5;
	
	/**
	 * Number of columns for the current layout grid.
	 * This is a read-only property of the grid.
	 */
	private static final int COLUMNS = 7;
	
	/**
	 * Width of the top left component in the layout grid.
	 */
	private static final int FIRST_COMPONENT_WIDTH = 5;

	/**
	 * Number which represents the size of the gap
	 * between adjacent rows and columns.
	 */
	private int rowColumnGap;
	
	/**
	 * A collection of {@code Component} instances belonging to the current layout
	 * and their {@code RCPosition}s.
	 */
	private Map<Component, RCPosition> components;

	/**
	 * Constructor which sets the gap between adjacent 
	 * rows and columns to the provided value.
	 * 
	 * @param rowColumnGap gap between adjacent rows and columns.
	 */
	public CalcLayout(int rowColumnGap) {
		this.rowColumnGap = rowColumnGap;
		this.components = new HashMap<>();
	}
	
	/**
	 * Default constructor which accepts no arguments and sets
	 * the gap between adjacent rows and columns to 0.
	 */
	public CalcLayout() {
		this(0);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getLayoutSize(parent, "preferred");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getLayoutSize(parent, "minimum");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension maximumLayoutSize(Container target) {
		return getLayoutSize(target, "maximum");
	}
	
	/**
	 * Calculates the specified layout size.
	 * 
	 * @param parent the container for the calculation.
	 * @param sizeType layout size that needs to be determined; can be
	 * {@code minimum}, {@code maximum} or {@code preferred}.
	 * @return the required layout size.
	 */
	private Dimension getLayoutSize(Container parent, String sizeType) {
		Function <Component, Dimension> action;
		switch(sizeType) {
			case "preferred":
				action = Component::getPreferredSize;
				break;
			case "minimum":
				action = Component::getMinimumSize;
				break;
			case "maximum":
				action = Component::getMaximumSize;
				break;
			default:
				throw new IllegalArgumentException("Invalid size type.");
		}
		
		Dimension largestCellSize = new Dimension(0, 0);
		for(Map.Entry<Component, RCPosition> entry : components.entrySet()) {
			Dimension currentCellSize = action.apply(entry.getKey());
			if(currentCellSize == null) continue;
			if(entry.getValue().equals(new RCPosition(1, 1))) {
				currentCellSize.width -= ((FIRST_COMPONENT_WIDTH - 1) * rowColumnGap / FIRST_COMPONENT_WIDTH);
			}
			largestCellSize.width = Math.max(largestCellSize.width, currentCellSize.width);
			largestCellSize.height = Math.max(largestCellSize.height, currentCellSize.height);
		}
		
		Insets insets = parent.getInsets();
		
		largestCellSize.width = largestCellSize.width * COLUMNS + rowColumnGap * (COLUMNS - 1) + insets.left + insets.right;
		largestCellSize.height = largestCellSize.height * ROWS + rowColumnGap * (ROWS - 1) + insets.top + insets.bottom;
		
		return largestCellSize;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		
		Dimension preferredComponentDimension = new Dimension(0, 0);
		Function<Component, Dimension> action = Component::getPreferredSize;
		for(Map.Entry<Component, RCPosition> entry : components.entrySet()) {
			Dimension currentCellDimension = action.apply(entry.getKey());
			if(currentCellDimension == null) continue;
			if(entry.getValue().getRow() == 1 && entry.getValue().getColumn() == 1) { //ovo provjeriti
				currentCellDimension.width -= ((FIRST_COMPONENT_WIDTH - 1) * rowColumnGap / FIRST_COMPONENT_WIDTH);
			}
			preferredComponentDimension.width = Math.max(preferredComponentDimension.width, currentCellDimension.width);
			preferredComponentDimension.height = Math.max(preferredComponentDimension.height, currentCellDimension.height);
		}
		
		Dimension preferredLayoutDimension = preferredLayoutSize(parent);
		
		int width = preferredComponentDimension.width * parent.getWidth() / preferredLayoutDimension.width;
		int height = preferredComponentDimension.height * parent.getHeight() / preferredLayoutDimension.height;
		
		for(Map.Entry<Component, RCPosition> entry : components.entrySet()) {
			Component component = entry.getKey();
			RCPosition position = entry.getValue();
			if (position.equals(new RCPosition(1, 1))) {
				component.setBounds(insets.left, insets.top, width * 5 + 4 * rowColumnGap, height);
			} else {
				component.setBounds(insets.left + (position.getColumn() - 1) * (width + rowColumnGap),
						insets.top + (position.getRow() - 1) * (height + rowColumnGap), width, height);
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(comp == null) throw new NullPointerException("The given component must not be null.");
		if(constraints == null) throw new NullPointerException("The given constraints must not be null.");
		
		RCPosition position;
		
		if(constraints instanceof RCPosition) {
			position = (RCPosition) constraints;
		} else if(constraints instanceof String) {
			try{
				position = RCPosition.parse((String) constraints);
			} catch(IllegalArgumentException e) {
				throw new IllegalArgumentException("The given constraints are not parsable.");
			}
		} else {
			throw new IllegalArgumentException("Invalid constraints format.");
		}
		
		if(components.containsValue(position)) throw new CalcLayoutException("There is already a component at this position.");
		
		int row = position.getRow();
		int column = position.getColumn();
		
		if(row < 1 || row > ROWS) throw new CalcLayoutException("Invalid row number.");
		if(column < 1 || column > COLUMNS) throw new CalcLayoutException("Invalid column number.");
		
		if(row == 1 && column > 1 && column < 6) throw new CalcLayoutException("Invalid first row column number."); //ROWS - 1?
		
		components.put(comp, position);	
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeLayoutComponent(Component comp) {
		components.remove(comp);		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void invalidateLayout(Container target) {

	}
	
	/**
	 * @throws UnsupportedOperationException
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException("This method should not be called.");	
	}

}
