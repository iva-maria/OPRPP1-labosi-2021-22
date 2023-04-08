package hr.fer.oprpp1.hw07.gui.prim;

import java.awt.*;

import javax.swing.*;

public class PrimDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	public PrimDemo() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("PrimDemo");
		initGUI();
		pack();
	}

	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		PrimListModel model = new PrimListModel();
		
		JScrollPane list1 = new JScrollPane(new JList<>(model));
        JScrollPane list2 = new JScrollPane(new JList<>(model));
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));
        panel.add(list1);
        panel.add(list2);
        
        cp.add(panel, BorderLayout.CENTER);
        
        JButton nextButton = new JButton("Next!");
        nextButton.addActionListener(e -> ((PrimListModel) model).next());
        cp.add(nextButton, BorderLayout.SOUTH);
        
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new PrimDemo().setVisible(true));
	}

}
