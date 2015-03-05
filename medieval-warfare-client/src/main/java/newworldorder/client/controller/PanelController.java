package newworldorder.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import newworldorder.client.gui.CreditsPanel;
import newworldorder.client.gui.JoinGamePanel;
import newworldorder.client.gui.LoginPanel;
import newworldorder.client.gui.MainMenuPanel;
import newworldorder.client.gui.MainView;
import newworldorder.client.gui.MatchMakingPanel;
import newworldorder.client.gui.MatchedPanel;

@Component
public class PanelController {
	@Autowired private MainView aMainView;
	
	@Autowired private MainMenuPanel aMainMenuPanel;
	@Autowired private CreditsPanel aCreditsPanel;
	@Autowired private JoinGamePanel aJoinGamePanel;
	@Autowired private MatchMakingPanel aMatchMakingPanel;
	@Autowired private MatchedPanel aMatchedPanel;
	@Autowired private LoginPanel aLoginPanel;
	
	public void showMainView() {
		aMainView.add(aMainMenuPanel);
		aMainView.revalidate();
		aMainView.repaint();
	}
	
	public void setCredits() {
		aMainView.remove(aMainMenuPanel);
		aMainView.add(aCreditsPanel);
		aMainView.revalidate();
		aMainView.repaint();
	}

	public void setLogin() {
		aMainView.remove(aMainMenuPanel);
//		aLoginPanel = new LoginPanel(this); TODO
		aMainView.add(aLoginPanel);
		aMainView.revalidate();
		aMainView.repaint();
	}

	public void setMenuFromCredits() {
		aMainView.remove(aCreditsPanel);
		aMainView.add(aMainMenuPanel);
		aMainView.revalidate();
		aMainView.repaint();
	}

	public void setMenuFromLogin() {
		aMainView.remove(aLoginPanel);
		aMainView.add(aMainMenuPanel);
		aMainView.revalidate();
		aMainView.repaint();
	}

	public void setJoinGame() {
		aMainView.remove(aLoginPanel);
		aMainView.add(aJoinGamePanel);
		aMainView.revalidate();
		aMainView.repaint();

	}

	public void setLoginFromJoin() {
		aMainView.remove(aJoinGamePanel);
//		aLoginPanel = new LoginPanel(this);
		aMainView.add(aLoginPanel);
		aMainView.revalidate();
		aMainView.repaint();
	}

	public void setMatchMaking() {
		aMainView.remove(aJoinGamePanel);
//		aMatchMakingPanel = new MatchMakingPanel(this);
		aMainView.add(aMatchMakingPanel);
		aMainView.revalidate();
		aMainView.repaint();
	}

	public void setJoinFromMatchMaking() {
		aMainView.remove(aMatchMakingPanel);
		aMainView.add(aJoinGamePanel);
		aMainView.revalidate();
		aMainView.repaint();
	}

	public void setMatched() {
		aMainView.remove(aMatchMakingPanel);
		aMainView.add(aMatchedPanel);
		aMainView.revalidate();
		aMainView.repaint();
	}

	public void setJoinFromMatched() {
		aMainView.remove(aMatchedPanel);
		aMainView.add(aJoinGamePanel);
		aMainView.revalidate();
		aMainView.repaint();
	}
	
	public void hideFrame() {
		aMainView.setVisible(false);
	}
}
