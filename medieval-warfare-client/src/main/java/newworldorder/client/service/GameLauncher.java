package newworldorder.client.service;

import newworldorder.common.matchmaking.GameInfo;
import newworldorder.common.service.IGameLauncher;
import newworldorder.game.desktop.DesktopLauncher;

public class GameLauncher implements IGameLauncher {
	private String username;

	@Override
	public void launchGame(GameInfo info) {
		new DesktopLauncher(username, info);
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
