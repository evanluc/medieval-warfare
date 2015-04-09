package newworldorder.common.network.command;

import newworldorder.common.model.User;
import newworldorder.common.persistence.IUserTransaction;
import newworldorder.common.persistence.PersistenceException;
import newworldorder.common.service.IServerServiceLocator;


public class CreateAccountCommand extends RemoteCommand {
	private String password;
	private boolean result = false;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4192064055097480790L;

	public CreateAccountCommand(String username, String password) {
		super(username);
		this.password = password;
	}

	@Override
	public void execute() {
		User user = new User(this.getSender(), password);
		IServerServiceLocator locator = this.getServiceLocator();
		IUserTransaction transaction = locator.getUserTransaction();
		try {
			result = transaction.createUser(user);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean getResult() {
		return result;
	}

}
