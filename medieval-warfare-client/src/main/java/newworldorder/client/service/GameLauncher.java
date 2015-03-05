package newworldorder.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import newworldorder.client.controller.PanelController;
import newworldorder.common.matchmaking.GameInfo;
import newworldorder.common.service.IGameLauncher;
import newworldorder.game.desktop.DesktopLauncher;

@Component
public class GameLauncher implements IGameLauncher {
	private PanelController panelController;
	
	@Autowired
	public GameLauncher(PanelController panelController) {
		super();
		this.panelController = panelController;
	}

	@Override
	public void launchGame(GameInfo info) {
		panelController.hideFrame();
		new DesktopLauncher();
	}
}
