package newworldorder.common.network.command;

import newworldorder.common.service.IClientServiceLocator;
import newworldorder.common.service.ISession;

public class RemoveFromPartyCommand extends ClientCommand {
	private String toKick;
	/**
	 * 
	 */
	private static final long serialVersionUID = -5783829081499067054L;

	public RemoveFromPartyCommand(String sender, String toKick) {
		super(sender);
		this.toKick = toKick;
	}

	@Override
	public void execute() {
		IClientServiceLocator locator = this.getServiceLocator();
		ISession session = locator.getSession();
		if (session.getUsername().equals(toKick)) {
			session.clearParty();
		}
		else {
			session.removePlayer(toKick);
		}
	}

}
