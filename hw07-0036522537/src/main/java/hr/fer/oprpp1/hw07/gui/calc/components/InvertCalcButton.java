package hr.fer.oprpp1.hw07.gui.calc.components;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;

import javax.swing.BorderFactory;

import hr.fer.oprpp1.hw07.gui.calc.model.CalcModel;
import hr.fer.oprpp1.hw07.gui.calc.model.CalculatorInputException;

/**
 * Class {@code InvertCalcButton} represents a button which,
 * when pressed, performs an operation. The operation can be
 * one of two possible, depending on whether the checkbox is
 * ticked.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class InvertCalcButton extends CalcButton {	
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Color of the button to be displayed in the backgorund.
	 */
	private static final Color INVERT_BUTTON_COLOR = Color.decode("#ffdbe0");
	
	/**
	 * Color of the button border.
	 */
	private static final Color INVERT_BORDER_COLOR = Color.decode("#ffd3da");

	/**
	 * Text to be displayed when the button is not inverted.
	 */
	private String normalText;
	
	/**
	 * Action to be performed when the button is not inverted.
	 */
	private ActionListener normalAction;

	/**
	 * Text to be displayed when the button is inverted.
	 */
	private String invertedText;

	/**
	 * Action to be performed when the button is inverted.
	 */
	private ActionListener invertedAction;
	
	/**
	 * Determines whether the button is currently inverted.
	 */
	public boolean isInverted;

	/**
	 * Constructor which sets all properties to the provided values.
	 * 
	 * @param normalText text to be displayed when the button is not inverted.
	 * @param normalAction action to be performed when the button is not inverted.
	 * @param invertedText text to be displayed when the button is inverted.
	 * @param invertedAction action to be performed when the button is inverted.
	 * @param calcModel model of the calculator used when performing operations.
	 */
    public InvertCalcButton(String normalText, ActionListener normalAction, 
    						String invertedText, ActionListener invertedAction, 
    						CalcModel calcModel) {
        super(normalText, normalAction);

        this.normalText = normalText;
        this.normalAction = normalAction;
        this.invertedText = invertedText;
        this.invertedAction = invertedAction;

        this.isInverted = false;
        
        initGUI();
    }
    
    /**
     * Constructor which sets the properties to the provided values.
     * 
     * @param normalText text to be displayed when the button is not inverted.
	 * @param normalAction action to be performed when the button is not inverted.
	 * @param invertedText text to be displayed when the button is inverted.
	 * @param invertedAction action to be performed when the button is inverted.
     * @param calcModel model of the calculator used when performing operations.
     */
    public InvertCalcButton(String normalText, DoubleBinaryOperator normalAction, String invertedText, DoubleBinaryOperator invertedAction, CalcModel calcModel) {
    	this(normalText, e -> {
    		if (calcModel.isActiveOperandSet()) {
    			if(calcModel.getFrozenValue() != null) throw new CalculatorInputException("The result has been frozen!");
    			double result = calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(), calcModel.getValue());
    			calcModel.setFrozenValue(Double.toString(result));
    			calcModel.setActiveOperand(result);
    		} else calcModel.setActiveOperand(calcModel.getValue());

    		calcModel.setPendingBinaryOperation(normalAction);
    		calcModel.clear();
    	}, invertedText, e -> {
    		if (calcModel.isActiveOperandSet()) {
    			if(calcModel.getFrozenValue() != null) throw new CalculatorInputException("The result has been frozen!");
    			double result = calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(), calcModel.getValue());
    			calcModel.setFrozenValue(Double.toString(result));
    			calcModel.setActiveOperand(result);
    		} else calcModel.setActiveOperand(calcModel.getValue());

    		calcModel.setPendingBinaryOperation(invertedAction);
    		calcModel.clear();
    	}, calcModel);
    } 

    /**
     * Initializes the button GUI.
     */
    public void initGUI() {
    	super.initGUI();
    	
    	setBackground(INVERT_BUTTON_COLOR);
		setBorder(BorderFactory.createLineBorder(INVERT_BORDER_COLOR));
		addActionListener(normalAction);
    }

    /**
     * Inverts the button appearance and its operation.
     */
    public void invert() {
    	setText(this.isInverted ? this.normalText : this.invertedText);
        removeActionListener(this.isInverted ? this.invertedAction : this.normalAction);
        addActionListener(this.isInverted ? this.normalAction : this.invertedAction);
        this.isInverted = !this.isInverted;
    }
}