package newworldorder.common.network.command;

import java.util.List;

import newworldorder.common.model.PartyInvitation;
import newworldorder.common.service.ISession;

public class SynchronizePartyCommand extends ClientCommand {
	private List<PartyInvitation> party;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1654059921385343907L;

	public SynchronizePartyCommand(String host, List<PartyInvitation> party) {
		super(host);
		this.party = party;
	}

	@Override
	public void execute() {
		ISession session = this.getServiceLocator().getSession();
		session.setParty(party);
	}

}
