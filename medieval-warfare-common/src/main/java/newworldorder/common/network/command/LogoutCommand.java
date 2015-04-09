package newworldorder.common.network.command;

import newworldorder.common.persistence.IUserTransaction;
import newworldorder.common.service.IServerServiceLocator;

public class LogoutCommand extends RemoteCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5142075829510701787L;

	public LogoutCommand(String sender) {
		super(sender);
	}

	@Override
	public void execute() {
		IServerServiceLocator locator = this.getServiceLocator();
		IUserTransaction transaction = locator.getUserTransaction();
		transaction.logoutUser(this.getSender());
	}

}
