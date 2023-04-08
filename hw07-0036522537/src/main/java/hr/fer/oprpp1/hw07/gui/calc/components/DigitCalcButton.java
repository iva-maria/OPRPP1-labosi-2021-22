package hr.fer.oprpp1.hw07.gui.calc.components;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;

/**
 * Class {@code DigitButton} represents a button which,
 * when pressed, types a digit onto the calculator display.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class DigitCalcButton extends CalcButton {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Color of the button to be displayed in the backgorund.
	 */
	private static final Color DIGIT_BUTTON_COLOR = Color.decode("#a3deff");
	
	/**
	 * Color of the button border.
	 */
	private static final Color DIGIT_BORDER_COLOR = Color.decode("#80cbfb");
	
	/**
	 * Number that is to be displayed on the button.
	 */
	private int buttonNumber;
	
	/**
	 * Action that is to be performed upon clicking on the button.
	 */
	private ActionListener digitButtonAction;

	/**
	 * Constructor which sets the properties to the provided values.
	 * 
	 * @param buttonNumber number that is to be displayed on the button.
	 * @param digitButtonAction action that is to be performed upon clicking on the button.
	 */
    public DigitCalcButton(int buttonNumber, ActionListener digitButtonAction) {
        super(String.valueOf(buttonNumber), digitButtonAction);
        
        this.buttonNumber = buttonNumber;
        this.digitButtonAction = digitButtonAction;
        
        initGUI();
    }

    @Override
    protected void initGUI() {
    	setText(String.valueOf(buttonNumber));
    	
    	setBackground(DIGIT_BUTTON_COLOR);
    	setBorder(BorderFactory.createLineBorder(DIGIT_BORDER_COLOR));
    	
        setFont(this.getFont().deriveFont(30f));
        
        addActionListener(digitButtonAction);
    }

}
