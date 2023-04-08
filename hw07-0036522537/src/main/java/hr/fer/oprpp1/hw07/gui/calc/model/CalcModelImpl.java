package hr.fer.oprpp1.hw07.gui.calc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

/**
 * Class {@code CalcModelImpl} represents an implementation of a
 * calculator model, as determined by the {@code CalcModel} interface.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class CalcModelImpl implements CalcModel {
	
	/**
	 * Determines whether the current Calculator is editable.
	 */
	private boolean isEditable;
	
	/**
	 * Determines whether the current value is positive.
	 */
	private boolean isPositive;
	
	/**
	 * String value to be displayed on the calculator display.
	 */
	private String stringValue;
	
	/**
	 * Numeric value for calculating.
	 */
	private double doubleValue;
	
	/**
	 * Frozen value of the current calculator.
	 */
	private String frozenValue;
	
	/**
	 * The operand currently being used.
	 */
	private Double activeOperand;
	
	/**
	 * Operation pending to be performed.
	 */
	private DoubleBinaryOperator pendingOperation;
	
	
	/**
	 * List of action listeners.
	 */
	private List<CalcValueListener> listeners;
	
	
	/**
	 * Constructor which sets all properties to default values.
	 */
	public CalcModelImpl() {
		this.isEditable = true;
		this.isPositive = true;
		this.stringValue = "";
		this.doubleValue = 0;
		this.frozenValue = null;
		this.activeOperand = null;
		this.pendingOperation = null;
		this.listeners = new ArrayList<>();
	}

	/**
	 * Adds a listener that needs to be notified about changes
	 * to the value to the list.
	 * 
	 * @param l new listener.
	 */
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);
	}

	/**
	 * Removes a listener that needs to be notified about changes
	 * to the value from the list.
	 * 
	 * @param l the listener that needs to be removed.
	 */
	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Creates a string representation of the value that is to be
	 * displayed on the calculator screen.
	 * 
	 * @return string representation of the current value.
	 */
	@Override
	public String toString() {
		if(this.frozenValue != null) return this.frozenValue;
		if(this.stringValue.isBlank() || this.stringValue.equals("0")) return (this.isPositive ? "" : "-") + "0";
		return (this.isPositive ? "" : "-") + this.stringValue;
	}

	/**
	 * Fetches the currently stored value from the calculator.
	 * 
	 * @return value that is currently stored in the calculator.
	 */
	@Override
	public double getValue() {
		return (this.isPositive ? 1 : -1) * this.doubleValue;
	}

	@Override
	public void setValue(double value) {
		this.doubleValue = Math.abs(value);
		this.stringValue = Double.toString(doubleValue);
		this.isPositive = value >= 0;
		
		this.isEditable = false;
		this.frozenValue = null;
		
		notifyListeners();
	}
	
	/**
	 * Fetches the current frozen value.
	 */
	public String getFrozenValue() {
		return this.frozenValue;
	}
	
	/**
	 * Sets the frozen value to the provided {@code value}.
	 */
	public void setFrozenValue(String value) {
		this.frozenValue = value;
	}

	/**
	 * Determines whether the calculator is in its editable state.
	 * 
	 * @return value of the {@code isEditable} variable.
	 */
	@Override
	public boolean isEditable() {
		return this.isEditable;
	}

	@Override
	public void clear() {
		this.isEditable = true;
		this.isPositive = true;
		
		this.stringValue = "";
		this.doubleValue = 0;
		
		this.notifyListeners();
	}

	@Override
	public void clearAll() {
		this.pendingOperation = null;
		this.frozenValue = null;
		this.clearActiveOperand();		
		this.clear();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!this.isEditable()) throw new CalculatorInputException("The calculator is not editable.");
		
		this.isPositive = !this.isPositive;
		this.frozenValue = null;
		
		notifyListeners();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!this.isEditable()) throw new CalculatorInputException("The calculator is not editable.");	
		
		if(this.stringValue.contains(".")) throw new CalculatorInputException("The number already contains a decimal point.");
		if(this.stringValue.isEmpty()) throw new CalculatorInputException("There are no digits preceeding the decimal point.");
		
		String newValueString = this.stringValue.isEmpty() ? "0." : (this.stringValue + ".");
		try {
			double newValueDouble = Double.parseDouble(newValueString);
			
			this.stringValue = newValueString;
			this.doubleValue = newValueDouble;
		} catch(NumberFormatException e) {
			throw new CalculatorInputException("The given input cannot be parsed into a double value.");
		}
		
		this.frozenValue = null;
		
		notifyListeners();
	}

	/**
	 * Adds the provided digit at the end of the number that is currently being
	 * put in. If the current number is 0, the addition is ignored.
	 * 
	 * @param digit the digit that is to be added.
	 * @throws CalculatorInputException if adding another digit makes the number too big.
	 * @throws IllegalArgumentException if the provided {@code digit} is not a number between 0 and 9.
	 */
	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!this.isEditable()) throw new CalculatorInputException("This calculator is not editable.");
		if(digit < 0 || digit > 9) throw new IllegalArgumentException("The given input cannot be parsed into a digit.");
		
		try {
			String newValueString = (stringValue + String.valueOf(digit)).replaceFirst("^0+(?!(\\.|$))", "");
			double newValueDouble = Double.parseDouble(newValueString);
			if(Double.isNaN(newValueDouble) || Double.isInfinite(newValueDouble)) {
				throw new CalculatorInputException("The value is invalid.");
			}
			
			this.stringValue = newValueString;
			this.doubleValue = newValueDouble;
			this.frozenValue = null;
		} catch(NumberFormatException e) {
			throw new CalculatorInputException("The given input cannot be parsed into a double value.");
		}
		
		notifyListeners();
	}

	/**
	 * Checks whether the active operand is set.
	 * 
	 * @return {@code true} if the active operand is set, {@code false} otherwise.
	 */
	@Override
	public boolean isActiveOperandSet() {
		return this.activeOperand != null;
	}

	/**
	 * Fetches the current active operand.
	 * 
	 * @return value of the current active operand.
	 * @throws IllegalStateException if the active operand has not been set yet.
	 */
	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(!isActiveOperandSet()) throw new IllegalStateException("The active operand is not defined.");
		return this.activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		this.isEditable = false;
		
		notifyListeners();
	}

	/**
	 * Deletes the value of the current active operand.
	 */
	@Override
	public void clearActiveOperand() {
		this.activeOperand = null;
		
		notifyListeners();
	}

	/**
	 * Fetches the current binary operation.
	 * 
	 * @return current binary operation.
	 */
	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return this.pendingOperation;
	}

	/**
	 * Sets the current binary operation to the provided value.
	 * If the operation was previously set, it gets overwritten.
	 * 
	 * @param op the value that the operation is to be set to.
	 */
	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
		
		notifyListeners();
	}
	
	/**
	 * Notifies all existing listeners about changes in the value.
	 */
	public void notifyListeners() {
		for(CalcValueListener listener : this.listeners) {
			listener.valueChanged(this);
		}
	}
	
}
