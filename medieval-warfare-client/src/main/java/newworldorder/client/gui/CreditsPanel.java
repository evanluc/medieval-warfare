package newworldorder.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

@org.springframework.stereotype.Component
public class CreditsPanel extends BasePanel {

	Style bold;
	Style justified;
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	public CreditsPanel() {

		super();
		setLayout(new BorderLayout());

		JButton back = new JButton("Back");
		back.setBackground(new Color(220, 20, 60));
		back.setForeground(Color.WHITE);
		back.setFocusPainted(false);
		back.setFont(new Font("Calibri", Font.BOLD, 20));
		back.setAlignmentX(Component.CENTER_ALIGNMENT);
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelController.setMenuFromCredits();
			}
		});
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
		southPanel.add(back);
		southPanel.setOpaque(false);
		add(southPanel, BorderLayout.SOUTH);
		JPanel northPanel = new JPanel();
		JTextPane text = getTextPane();
		text.setOpaque(false);
		northPanel.add(text);
		northPanel.setOpaque(false);
		add(northPanel, BorderLayout.NORTH);
		setOpaque(false);

	}

	private JTextPane getTextPane() {
		String[] initString = { "Developed by: \n", "Andrew Martin\n", "David Zhou\n", "Evan Luc\n", "Grady Weber\n",
				"and Joel Cheverie\n" };
		String[] initStyles = { "bold", "bold", "bold", "bold", "bold", "bold" };
		JTextPane textPane = new JTextPane();
		textPane.setPreferredSize(new Dimension(270, 550));
		StyledDocument doc = textPane.getStyledDocument();
		addStylesToDocument(doc);
		try {
			for (int i = 0; i < initString.length; i++) {
				int pos = doc.getLength();
				doc.insertString(pos, initString[i], doc.getStyle(initStyles[i]));
				if (initStyles[i].equals("justified")) {
					Style logicalStyle = doc.getLogicalStyle(pos);
					doc.setParagraphAttributes(pos, initString[i].length(), justified, false);
					doc.setLogicalStyle(pos, logicalStyle);
				}
			}
		} catch (BadLocationException ble) {
			System.out.println("bad location: " + ble.getMessage());
		}
		return textPane;
	}

	public void addStylesToDocument(StyledDocument doc) {
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		Style regular = doc.addStyle("regular", def);
		StyleConstants.setFontFamily(def, "Calibri");
		StyleConstants.setFontSize(regular, 24);
		bold = doc.addStyle("bold", regular);
		StyleConstants.setBold(bold, true);
		StyleConstants.setForeground(bold, new Color(3, 3, 3));
		StyleConstants.setAlignment(bold, StyleConstants.ALIGN_LEFT);
		justified = doc.addStyle("justified", regular);
		StyleConstants.setAlignment(justified, StyleConstants.ALIGN_JUSTIFIED);
		StyleConstants.setForeground(justified, new Color(90, 0, 90));
	}
}
