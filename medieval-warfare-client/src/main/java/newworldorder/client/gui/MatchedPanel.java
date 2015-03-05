package newworldorder.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import newworldorder.game.desktop.DesktopLauncher;

@org.springframework.stereotype.Component
public class MatchedPanel extends BasePanel {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	public MatchedPanel() {
		super();
		setLayout(new BorderLayout());

		JPanel northPanel = new JPanel();
		JLabel jlabel = new JLabel("Opponent Found!");
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
				panelController.setJoinFromMatched();
			}
		});

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		JLabel jtitle = new JLabel("Choose Map");
		jtitle.setFont(new Font("Calibri", 1, 30));
		jtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		leftPanel.add(jtitle);
		BufferedImage img = null;
		try {
			String imgPath = "/medievalBattle.jpg";
			img = ImageIO.read(getClass().getResourceAsStream(imgPath));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		leftPanel.add(new BackgroundPanel(img));
		JButton map = new JButton("Seaside Squirmish");
		map.setAlignmentX(Component.CENTER_ALIGNMENT);
		map.setBackground(new Color(59, 89, 182));
		map.setForeground(Color.WHITE);
		map.setFocusPainted(false);
		map.setFont(new Font("Calibri", Font.BOLD, 20));
		map.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelController.hideFrame();
				new DesktopLauncher();
			}
		});
		leftPanel.add(map);
		leftPanel.setOpaque(false);
		add(leftPanel, BorderLayout.WEST);

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
		southPanel.add(back);
		southPanel.setOpaque(false);
		add(southPanel, BorderLayout.SOUTH);
		setOpaque(false);

	}
}
