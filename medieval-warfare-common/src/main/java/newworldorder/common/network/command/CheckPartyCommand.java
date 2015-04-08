package newworldorder.common.network.command;

import newworldorder.common.service.ISession;

public class CheckPartyCommand extends ClientCommand {
	private boolean canJoin = false;
	/**
	 * 
	 */
	private static final long serialVersionUID = -8455023389762159628L;

	public CheckPartyCommand(String sender) {
		super(sender);
	}

	@Override
	public void execute() {
		ISession session = this.getServiceLocator().getSession();
		canJoin = session.getParty().isEmpty();
	}
	
	public boolean canJoin() {
		return canJoin;
	}
}
