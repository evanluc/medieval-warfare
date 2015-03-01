package newworldorder.common.network.message;

import newworldorder.common.matchmaking.GameRequest;
import newworldorder.common.service.IMatchController;
import newworldorder.common.service.IServerServiceLocator;

public class JoinGameCommand extends RemoteCommand {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1047660304677624037L;
	private GameRequest gameRequest;

	public JoinGameCommand(String sender, GameRequest gameRequest) {
		super(sender);
		this.gameRequest = gameRequest;
	}

	@Override
	public void execute() {
		IServerServiceLocator locator = this.getServiceLocator();
		IMatchController controller = locator.getMatchController();
		controller.addToQueue(gameRequest);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((gameRequest == null) ? 0 : gameRequest.hashCode());
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
		JoinGameCommand other = (JoinGameCommand) obj;
		if (gameRequest == null) {
			if (other.gameRequest != null)
				return false;
		} else if (!gameRequest.equals(other.gameRequest))
			return false;
		return true;
	}
}
