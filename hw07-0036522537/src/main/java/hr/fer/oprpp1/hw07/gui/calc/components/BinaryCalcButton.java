package hr.fer.oprpp1.hw07.gui.calc.components;

import java.awt.Color;

import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

import javax.swing.BorderFactory;

import hr.fer.oprpp1.hw07.gui.calc.model.CalcModel;
import hr.fer.oprpp1.hw07.gui.calc.model.CalculatorInputException;

/**
 * Class {@code BinaryCalcButton} represents a button which,
 * when pressed, performs a binary operation.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class BinaryCalcButton extends CalcButton {

	private static final long serialVersionUID = 1L;

	/**
	 * Color of the button to be displayed in the backgorund.
	 */
	private static final Color BINARY_BUTTON_COLOR = Color.decode("#d2fed9");
	  
	/**
	 * Color of the button border.
	 */
	private static final Color BINARY_BORDER_COLOR = Color.decode("#c1fecb");
	
	/**
	 * Name of the binary operation.
	 */
	private String binaryOpName;
	
	/**
	 * Calc model.
	 */
	private CalcModel calcModel;
	
	/**
	 * Operation to be performed.
	 */
	private DoubleBinaryOperator binaryOperation;

    /**
     * Creates a new {@code BinaryButton} instance.
     *
     * @param text the text that is to be displayed.
     * @param binaryOperation action listener containing the binary operation that is to be performed when the button in the inverted state is pressed.
     * @param calcModel the calculator model performing the operation.
     */
    public BinaryCalcButton(CalcModel calcModel, String binaryOpName, DoubleBinaryOperator binaryOperation) {
    	super();
        
        this.binaryOpName = binaryOpName;
        this.calcModel = calcModel;
        this.binaryOperation = binaryOperation;
        
        initGUI();
    }
    
    /**
     * Initializes the button GUI.
     */
    public void initGUI() {
    	setText(binaryOpName);
	
    	setBackground(BINARY_BUTTON_COLOR);
    	setBorder(BorderFactory.createLineBorder(BINARY_BORDER_COLOR));
    	
    	addActionListener(e -> {
    		if (calcModel.isActiveOperandSet()) {
    			if(calcModel.getFrozenValue() != null) throw new CalculatorInputException("The result has been frozen!");
    			double result = calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(), calcModel.getValue());
    			calcModel.setFrozenValue(Double.toString(result));
    			calcModel.setActiveOperand(result);
    		} else calcModel.setActiveOperand(calcModel.getValue());

    	calcModel.setPendingBinaryOperation(Objects.requireNonNull(binaryOperation, "The given binary operation cannot be null!"));
    	calcModel.clear();
    	});
    }  

}
