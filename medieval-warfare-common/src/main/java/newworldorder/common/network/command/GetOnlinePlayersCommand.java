package newworldorder.common.network.command;

import java.util.Set;

public class GetOnlinePlayersCommand extends RemoteCommand {
	private Set<String> onlineUsers;
	/**
	 * 
	 */
	private static final long serialVersionUID = -7826051951230173128L;

	public GetOnlinePlayersCommand(String sender) {
		super(sender);
	}

	@Override
	public void execute() {
		onlineUsers = this.getServiceLocator().getOnlinePlayers();
	}
	
	public Set<String> getOnlinePlayers() {
		return onlineUsers;
	}

}
