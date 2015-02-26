package newworldorder.ui;



import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class MainView extends JFrame {
	private MainMenuPanel aMainMenuPanel = new MainMenuPanel(this);
	private CreditsPanel aCreditsPanel = new CreditsPanel(this);
	private JoinGamePanel aJoinGamePanel = new JoinGamePanel(this);
	private MatchMakingPanel aMatchMakingPanel = new MatchMakingPanel(this);
	private MatchedPanel aMatchedPanel = new MatchedPanel(this);
	private LoginPanel aLoginPanel = new LoginPanel(this);
	private String aName;

	public MainView() {
		super();
		BufferedImage img=null;
		try {
			InputStream stream = getClass().getResourceAsStream("/background.jpg");
			img = ImageIO.read(stream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setContentPane(new BackgroundPanel(img));
		setTitle("Medieval Warfare");
		add(aMainMenuPanel);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		pack();
		setVisible(true);
	}

	public void setCredits() {
		remove(aMainMenuPanel);
		add(aCreditsPanel);
		revalidate();
		repaint();
	}

	public void setLogin() {
		remove(aMainMenuPanel);
		aLoginPanel = new LoginPanel(this);
		add(aLoginPanel);
		revalidate();
		repaint();
	}

	public void setMenuFromCredits() {
		remove(aCreditsPanel);
		add(aMainMenuPanel);
		revalidate();
		repaint();
	}

	public void setMenuFromLogin() {
		remove(aLoginPanel);
		add(aMainMenuPanel);
		revalidate();
		repaint();
	}

	public void setJoinGame() {
		remove(aLoginPanel);
		add(aJoinGamePanel);
		revalidate();
		repaint();

	}

	public void setLoginFromJoin() {
		remove(aJoinGamePanel);
		aLoginPanel = new LoginPanel(this);
		add(aLoginPanel);
		revalidate();
		repaint();
	}

	public void setMatchMaking() {
		remove(aJoinGamePanel);
		aMatchMakingPanel = new MatchMakingPanel(this);
		add(aMatchMakingPanel);
		revalidate();
		repaint();
	}

	public void setJoinFromMatchMaking() {
		remove(aMatchMakingPanel);
		add(aJoinGamePanel);
		revalidate();
		repaint();
	}

	public void setMatched() {
		remove(aMatchMakingPanel);
		add(aMatchedPanel);
		revalidate();
		repaint();
	}

	public void setJoinFromMatched() {
		remove(aMatchedPanel);
		add(aJoinGamePanel);
		revalidate();
		repaint();
	}

	public static void main(String[] args) {
		new MainView();
	}

	public String getName() {
		return aName;
	}

	public void setName(String pName) {
		aName = pName;
	}
}