package newworldorder.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

public class MainMenuPanel extends JPanel
{	
	
	/**
	 * 
	 */
	private MainView aMainView;
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public MainMenuPanel(MainView pMainView)
	{	
		super();
		aMainView = pMainView;
		setLayout( new BorderLayout() );

		JButton play = new JButton("Play");
		play.setAlignmentX(Component.CENTER_ALIGNMENT);
        play.setBackground(new Color(59, 89, 182));
        play.setForeground(Color.WHITE);
        play.setFocusPainted(false);
        play.setFont(new Font("Calibri", Font.BOLD, 20));
        play.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				aMainView.setLogin();
			}	
		});
        JButton credits = new JButton("Credits");
		credits.setAlignmentX(Component.CENTER_ALIGNMENT);
        credits.setBackground(new Color(0, 238, 18));
        credits.setForeground(Color.WHITE);
        credits.setFocusPainted(false);
        credits.setFont(new Font("Calibri", Font.BOLD, 20));
        credits.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				aMainView.setCredits();
			}	
		});
		JButton quit = new JButton("Quit");
		quit.setBackground(new Color(220, 20, 60));
        quit.setForeground(Color.WHITE);
        quit.setFocusPainted(false);
        quit.setFont(new Font("Calibri", Font.BOLD, 20));
		quit.setAlignmentX(Component.CENTER_ALIGNMENT);
		quit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
				return;
			}	
		});
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));	
		southPanel.add(play);
		southPanel.add(quit);
		southPanel.add(credits);
		southPanel.setOpaque(false);
		add(southPanel, BorderLayout.SOUTH);
		JPanel northPanel = new JPanel();
		JLabel jlabel = new JLabel("Medieval Warfare");
	    jlabel.setFont(new Font("Calibri",1,60));
	    northPanel.add(jlabel);
	    northPanel.setOpaque(false);
	    add(northPanel, BorderLayout.NORTH);
	    setOpaque(false);

		
		
		
		/* Start Game */
	}

}
