package newworldorder.client.service;

import newworldorder.common.matchmaking.GameInfo;
import newworldorder.common.service.IGameLauncher;
import newworldorder.game.desktop.DesktopLauncher;

public class GameLauncher implements IGameLauncher {
	@Override
	public void launchGame(GameInfo info) {
		new DesktopLauncher(info);
	}
}
