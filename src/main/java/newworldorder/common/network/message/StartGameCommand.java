package newworldorder.common.network.message;

import newworldorder.common.matchmaking.GameInfo;
import newworldorder.common.service.IClientServiceLocator;
import newworldorder.common.service.IGameLauncher;

public class StartGameCommand extends ClientCommand {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2435938035933490611L;
	private GameInfo gameInfo;
	
	public StartGameCommand(String sender, GameInfo gameInfo) {
		super(sender);
		this.gameInfo = gameInfo;
	}

	@Override
	public void execute() {
		IClientServiceLocator serviceLocator = this.getServiceLocator();
		IGameLauncher launcher = serviceLocator.getGameLauncher();
		launcher.launchGame(gameInfo);
	}
}
