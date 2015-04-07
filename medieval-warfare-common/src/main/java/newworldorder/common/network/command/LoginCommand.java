package newworldorder.common.network.command;

import newworldorder.common.model.User;
import newworldorder.common.persistence.IUserTransaction;
import newworldorder.common.persistence.PersistenceException;
import newworldorder.common.service.IServerServiceLocator;

public class LoginCommand extends RemoteCommand {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7839191183469247807L;
	private String password;

	public LoginCommand(String sender, String password) {
		super(sender);
		this.password = password;
	}

	@Override
	public void execute() {
		User user = new User(this.getSender(), password);
		IServerServiceLocator locator = this.getServiceLocator();
		IUserTransaction transaction = locator.getUserTransaction();
		try {
			transaction.loginUser(user);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		LoginCommand other = (LoginCommand) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
}
