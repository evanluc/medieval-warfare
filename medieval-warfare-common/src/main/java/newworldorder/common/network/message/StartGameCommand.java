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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((gameInfo == null) ? 0 : gameInfo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		StartGameCommand other = (StartGameCommand) obj;
		if (gameInfo == null) {
			if (other.gameInfo != null)
				return false;
		} else if (!gameInfo.equals(other.gameInfo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StartGameCommand [gameInfo=" + gameInfo + "]";
	}
}
