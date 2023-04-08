package hr.fer.oprpp1.hw07.gui.calc;

import java.awt.Container;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.oprpp1.hw07.gui.calc.components.BinaryCalcButton;
import hr.fer.oprpp1.hw07.gui.calc.components.CalcButton;
import hr.fer.oprpp1.hw07.gui.calc.components.CalcDisplay;
import hr.fer.oprpp1.hw07.gui.calc.components.DigitCalcButton;
import hr.fer.oprpp1.hw07.gui.calc.components.InvertCalcButton;
import hr.fer.oprpp1.hw07.gui.calc.model.CalcModel;
import hr.fer.oprpp1.hw07.gui.calc.model.CalcModelImpl;
import hr.fer.oprpp1.hw07.gui.calc.model.CalculatorInputException;
import hr.fer.oprpp1.hw07.gui.layouts.CalcLayout;
import hr.fer.oprpp1.hw07.gui.layouts.RCPosition;

import static java.lang.Math.*;

public class Calculator extends JFrame {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Model of the calculator that is used.
	 */
    private final CalcModel calcModel;

    /**
     * Stack for storing variables in the calculator.
     */
    private final Stack<Double> stack;

    /**
     * Collection of invertible buttons.
     */
    private Map<String, InvertCalcButton> invertButtons;

    /**
     * Constructor which sets the {@code calcModel} property
     * to the provided value.
     * 
     * @param calcModel calc model to be used.
     */
    public Calculator(CalcModel calcModel) {
        super();
        this.calcModel = calcModel;
        this.stack = new Stack<>();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Java Calculator v1.0");
        initGUI();
    }

    /**
     * Initializes the calculator GUI.
     */
    private void initGUI() {
        Container container = getContentPane();
        container.setLayout(new CalcLayout(3));

        initDisplay(container);
        initButtons(container);
        pack();
    }

    /**
     * Initializes the calculator display.
     * 
     * @param container the container.
     */
    private void initDisplay(Container container) {
        CalcDisplay display = new CalcDisplay(calcModel.toString());
        container.add(display, new RCPosition(1, 1));
        calcModel.addCalcValueListener(calcModel -> display.setText(calcModel.toString()));
    }
    
    /**
     * Initializes the calculator buttons.
     * 
     * @param container the container.
     */
    private void initButtons(Container container) {
        initDigitButtons(container);
        initBinaryOpButtons(container);
        initOtherOpButtons(container);        
        initInvertibleButtons(container);
    }
    
    /**
     * Initializes the buttons for typing digits into the calculator.
     * 
     * @param container the container.
     */
    private void initDigitButtons(Container container) {
		container.add(new DigitCalcButton(0, e -> calcModel.insertDigit(0)), "5,3");
		int number = 1;
		for (int i = 4; i > 1; i--) {
			for (int j = 3; j < 6; j++) {
				int digit = number;
				container.add(new DigitCalcButton(number++, e -> calcModel.insertDigit(digit)), new RCPosition(i, j));
			}
		}
    }
    
    /**
     * Initializes the buttons for binary operations.
     * 
     * @param container the container.
     */
    private void initBinaryOpButtons(Container container) {
    	container.add("2,6", new BinaryCalcButton(calcModel, "/", (a, b) -> a / b));
    	container.add("3,6", new BinaryCalcButton(calcModel, "*", (a, b) -> a * b));
    	container.add("4,6", new BinaryCalcButton(calcModel, "-", (a, b) -> a - b));
    	container.add("5,6", new BinaryCalcButton(calcModel, "+", (a, b) -> a + b));
    }
    
    /**
     * Initializes the buttons for setting calculator options and other
     * operations like pushing to and popping from stack.
     * 
     * @param container the container.
     */
    private void initOtherOpButtons(Container container) {
    	container.add("1,6", new CalcButton("=", e -> {
    		DoubleBinaryOperator pendingOperation = calcModel.getPendingBinaryOperation();
    		if (pendingOperation != null) calcModel.setActiveOperand(calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(), calcModel.getValue()));
    		calcModel.setPendingBinaryOperation(null);
    		calcModel.setValue(calcModel.getActiveOperand());
    	}));
	
    	container.add("5,4", new CalcButton("+/-", e -> calcModel.swapSign()));
	
    	container.add("5,5", new CalcButton(".", e -> calcModel.insertDecimalPoint()));
	
    	container.add("1,7", new CalcButton("clr", e -> calcModel.clear()));
	
    	container.add("2,7", new CalcButton("reset", e -> calcModel.clearAll()));
	
    	container.add("3,7", new CalcButton("push", e -> stack.push(calcModel.getValue())));
	
    	container.add("4,7", new CalcButton("pop", e -> {
    		if(stack.isEmpty()) throw new CalculatorInputException("The stack is empty."); //generateError
    		calcModel.setValue(stack.pop());
    	}));
    }
    
    /**
     * Initializes the buttons for invertible operations.
     * 
     * @param container the container.
     */
    private void initInvertibleButtons(Container container) {
    	JCheckBox invertCheckBox = new JCheckBox("Inv");
	
    	invertCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
    	invertCheckBox.setVerticalAlignment(SwingConstants.CENTER);
    	invertCheckBox.setSelected(false);
	
    	invertButtons = new LinkedHashMap<>(Map.of(
    			"2,1", new InvertCalcButton("1/x",
    					e -> {
    						if (calcModel.getFrozenValue() != null) generateError("The result has been frozen!");
    						calcModel.setValue(1 / calcModel.getValue());
    					}, "1/x",
    					e -> {
    						if (calcModel.getFrozenValue() != null) generateError("The result has been frozen!");
    						calcModel.setValue(1 / calcModel.getValue());
    					}, calcModel),
    			"3,1", new InvertCalcButton("log",
    					e -> {
    						if (calcModel.getFrozenValue() != null) generateError("The result has been frozen!");
    						calcModel.setValue(log10(calcModel.getValue()));
    					}, "10^x", 
    					e -> {
    						if (calcModel.getFrozenValue() != null) generateError("The result has been frozen!");
    						calcModel.setValue(pow(10, calcModel.getValue()));
    					}, calcModel),
    			"4,1", new InvertCalcButton("ln",
    					e -> {
    						if (calcModel.getFrozenValue() != null) generateError("The result has been frozen!");
    						calcModel.setValue(log(calcModel.getValue()));
    					}, "e^x",
    					e -> {
    						calcModel.setValue(pow(Math.E, calcModel.getValue()));
    						if (calcModel.getFrozenValue() != null) generateError("The result has been frozen!");
    					}, calcModel),
    			"5,1", new InvertCalcButton("x^n", Math::pow, "x^(1/n)", (double x, double n) -> pow(x, 1 / n), calcModel),
    			"2,2", new InvertCalcButton("sin",
    					e -> {
    						if (calcModel.getFrozenValue() != null) generateError("The result has been frozen!");
    						calcModel.setValue(sin(calcModel.getValue()));
    					}, "arcsin",
    					e -> {
    						if (calcModel.getFrozenValue() != null) generateError("The result has been frozen!");
    						calcModel.setValue(asin(calcModel.getValue()));
    					}, calcModel),
    			"3,2", new InvertCalcButton("cos",
    					e -> {
    						if (calcModel.getFrozenValue() != null) generateError("The result has been frozen!");
    						calcModel.setValue(cos(calcModel.getValue()));
    					}, "arccos",
    					e -> {
    						if (calcModel.getFrozenValue() != null) generateError("The result has been frozen!");
    						calcModel.setValue(acos(calcModel.getValue()));
    					}, calcModel),
    			"4,2", new InvertCalcButton("tan",
    					e -> {
    						if (calcModel.getFrozenValue() != null) generateError("The result has been frozen!");
    						calcModel.setValue(tan(calcModel.getValue()));
    					}, "arctan",
    					e -> {
    						if (calcModel.getFrozenValue() != null) generateError("The result has been frozen!");
    						calcModel.setValue(atan(calcModel.getValue()));
    					}, calcModel),
    			"5,2", new InvertCalcButton("ctg",
    					e -> {
    						if (calcModel.getFrozenValue() != null) generateError("The result has been frozen!");
    						calcModel.setValue(1 / tan(calcModel.getValue()));
    					}, "arcctg",
    					e -> {
    						if (calcModel.getFrozenValue() != null) generateError("The result has been frozen!");
    						calcModel.setValue(1 / atan(calcModel.getValue()));
    					}, calcModel)));
	
		invertCheckBox.addActionListener(l -> {
			for (Map.Entry<String, InvertCalcButton> invertButton : invertButtons.entrySet()) {
				invertButton.getValue().invert();
			}
		});
	
    	container.add(invertCheckBox, new RCPosition(5, 7));
	
		for(Map.Entry<String, InvertCalcButton> invertButton : invertButtons.entrySet()) {
			container.add(invertButton.getValue(), invertButton.getKey());
		}
    }

    /**
     * Method from which the program execution begins.
     * 
     * @param args an array of command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculator(new CalcModelImpl()).setVisible(true));
    }
    
    /**
     * Instantiates an error window.
     * 
     * @param msg the message that needs to be displayed.
     */
    public void generateError(String msg) {
    	JOptionPane.showMessageDialog(null, msg, "Warning", JOptionPane.WARNING_MESSAGE);
    }

}
