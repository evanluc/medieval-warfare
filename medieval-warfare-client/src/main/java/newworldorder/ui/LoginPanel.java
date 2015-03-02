package newworldorder.ui;

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

public class LoginPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainView aMainView;

	public LoginPanel(MainView pMainView) {
		super();
		aMainView = pMainView;
		setLayout(new BorderLayout());

		aMainView.setName(null);
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
				aMainView.setMenuFromLogin();
			}
		});

		JPanel subCenterPanel = new JPanel();
		final JTextField field = new JTextField(20);
		field.setText(aMainView.getName());
		field.setAlignmentX(Component.CENTER_ALIGNMENT);
		field.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent pEvent) {
				aMainView.setName(field.getText());
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
				aMainView.setName(field.getText());
				aMainView.setJoinGame();

				// try {
				// MessageProducer producer =
				// ActorFactory.createDirectProducer("142.157.148.57",
				// "requestQueue");
				// LoginCommand loginCommand = new
				// LoginCommand(aMainView.getName(), "password");
				// producer.sendCommand(loginCommand);
				// } catch (IOException e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// } catch (Exception e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
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
