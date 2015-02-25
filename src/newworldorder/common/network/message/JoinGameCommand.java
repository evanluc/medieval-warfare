package newworldorder.common.network.message;

import newworldorder.common.matchmaking.GameRequest;
import newworldorder.common.service.IMatchController;
import newworldorder.common.service.IServiceLocator;

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
		IServiceLocator locator = this.getServiceLocator();
		IMatchController controller = locator.getMatchController();
		controller.addToQueue(gameRequest);
	}

}
