package hr.fer.oprpp1.hw07.gui.layouts.demo;

import java.awt.Container;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import hr.fer.oprpp1.hw07.gui.layouts.*;

/**
 * Class {@code DemoFrame1} is used for demonstrating
 * the abilities of the {@code CalcLayout}.
 * 
 * @author Marko Čupić (probably)
 */
public class DemoFrame1 extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 */
	public DemoFrame1() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		pack();
//		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//		setSize(500, 500);
//		initGUI();
	}
		
	/**
	 * Initializes the GUI.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		cp.add(l("tekst 1"), new RCPosition(1,1));
		cp.add(l("tekst 2"), new RCPosition(2,3));
		cp.add(l("tekst stvarno najdulji"), new RCPosition(2,7));
		cp.add(l("tekst kraći"), new RCPosition(4,2));
		cp.add(l("tekst srednji"), new RCPosition(4,5));
		cp.add(l("tekst"), new RCPosition(4,7));
		}
	
	/**
	 * Creates new {@code JLabel} instance.
	 * 
	 * @param text text to be displayed on the label.
	 * @return new {@code JLabel} instance with the provided text.
	 */
	private JLabel l(String text) {
		JLabel l = new JLabel(text);
		l.setBackground(Color.YELLOW);
		l.setOpaque(true);
		return l;
	}
		
	/**
	 * Main method from which the program execution begins.
	 * 
	 * @param args an array of command-line arguments.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
		new DemoFrame1().setVisible(true);
		});
	}

}
