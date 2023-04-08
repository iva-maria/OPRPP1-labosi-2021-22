package hr.fer.oprpp1.hw07.gui.calc.components;

import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * Class {@code CalcButton} represents a button in a calculator model.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class CalcButton extends JButton {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Color of the button to be displayed in the backgorund.
	 */
	private static final Color DEFAULT_BUTTON_COLOR = Color.decode("#e5cdfb");
	
	/**
	 * Color of the button border.
	 */
	private static final Color DEFAULT_BORDER_COLOR = Color.decode("#dfc3fa");
	
	/**
	 * Name of the button.
	 */
	private String buttonName;
	
	/**
	 * Action listener.
	 */
	private ActionListener buttonAction;
	
	/**
	 * Default constructor.
	 */
	public CalcButton() {
		super();
	}

	/**
	 * Constructor which sets the properties {@code buttonName} and {@code buttonAction}
	 * to the provided values.
	 * 
	 * @param buttonName name of the button.
	 * @param buttonAction action to be performed.
	 */
    public CalcButton(String buttonName, ActionListener buttonAction) {
        super();
        this.buttonName = buttonName;
        this.buttonAction = buttonAction;
        
        initGUI();
    }

    /**
     * Initializes the button GUI.
     */
    protected void initGUI() {
    	setText(buttonName);
    	
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        
        setBackground(DEFAULT_BUTTON_COLOR);
        setBorder(BorderFactory.createLineBorder(DEFAULT_BORDER_COLOR));
        
        addActionListener(buttonAction);
    }

}
