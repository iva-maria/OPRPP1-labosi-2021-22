package hr.fer.oprpp1.hw07.gui.calc.components;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class CalcDisplay extends JLabel {

	private static final long serialVersionUID = 1L;
	
	private static final Color DISPLAY_COLOR = Color.decode("#fafc97");
	
	private static final Color DISPLAY_BORDER_COLOR = Color.decode("#fdfd96");

	//promijeniti ime displayValue
    public CalcDisplay(String displayValue) {
        super(displayValue);
        initGUI();
    }

    private void initGUI() {
        setHorizontalAlignment(SwingConstants.RIGHT);
        setVerticalAlignment(SwingConstants.CENTER);
        
        setOpaque(true);
        setBackground(DISPLAY_COLOR);
        setBorder(BorderFactory.createLineBorder(DISPLAY_BORDER_COLOR));
        
        setFont(this.getFont().deriveFont(30f));
    }
	
}
