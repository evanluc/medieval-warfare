package newworldorder.common.service;

import newworldorder.common.matchmaking.GameInfo;

public interface IGameLauncher {
	public void launchGame(GameInfo info);

	public void setUsername(String username);
}
