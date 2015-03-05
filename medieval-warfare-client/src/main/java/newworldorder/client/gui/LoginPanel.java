package newworldorder.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;

import newworldorder.client.controller.IController;

@org.springframework.stereotype.Component
public class LoginPanel extends BasePanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired private IController controller;

	public LoginPanel() {
		super();
//		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
//		controller = ctx.getBean(IController.class);
//		ctx.close();
		setLayout(new BorderLayout());

		JPanel northPanel = new JPanel();
		JLabel jlabel = new JLabel("Please Login");
		jlabel.setFont(new Font("Calibri", 1, 60));
		northPanel.add(jlabel);
		northPanel.setOpaque(false);
		add(northPanel, BorderLayout.NORTH);

		JButton back = new JButton("Back");
		back.setBackground(new Color(220, 20, 60));
		back.setForeground(Color.WHITE);
		back.setFocusPainted(false);
		back.setFont(new Font("Calibri", Font.BOLD, 20));
		back.setAlignmentX(Component.CENTER_ALIGNMENT);
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelController.setMenuFromLogin();
			}
		});

		JPanel subCenterPanel = new JPanel();
		final JTextField field = new JTextField(20);
//		field.setText(aMainView.getName()); TODO: fix
		field.setAlignmentX(Component.CENTER_ALIGNMENT);
		field.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent pEvent) {
//				aMainView.setName(field.getText());
			}
		});
		JButton enter = new JButton("Enter");
		enter.setBackground(new Color(59, 89, 182));
		enter.setForeground(Color.WHITE);
		enter.setFocusPainted(false);
		enter.setFont(new Font("Calibri", Font.BOLD, 20));
		enter.setAlignmentX(Component.CENTER_ALIGNMENT);
		enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent pEvent) {
				String username = field.getText();
//				aMainView.setName(username);
				controller.login(username, "password");
				panelController.setJoinGame();
			}
		});
		subCenterPanel.add(field);
		subCenterPanel.add(enter);
		subCenterPanel.setOpaque(false);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.setOpaque(false);
		centerPanel.add(subCenterPanel);
		add(centerPanel, BorderLayout.CENTER);
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));

		southPanel.add(back);
		southPanel.setOpaque(false);
		add(southPanel, BorderLayout.SOUTH);
		setOpaque(false);
	}
}
