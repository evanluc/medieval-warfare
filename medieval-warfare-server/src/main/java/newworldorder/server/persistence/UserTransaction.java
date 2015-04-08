
package newworldorder.server.persistence;

import org.springframework.stereotype.Component;

import newworldorder.common.model.User;
import newworldorder.common.persistence.IUserTransaction;
import newworldorder.common.persistence.PersistenceException;

@Component
public class UserTransaction implements IUserTransaction {
	private UserStore userStore;
	
	public UserTransaction(UserStore userStore) {
		this.userStore = userStore;
	}
	
	@Override
	public synchronized void createUser(User user) throws PersistenceException {
		try {
			if (!userStore.containsUsername(user.getUsername())) {
				userStore.insertUser(user);
			}
			else {
				throw new PersistenceException("User with username '" + user.getUsername() + "' already exists.");
			}
		}
		catch (Exception e) {
			throw new PersistenceException("Unknown exception.");
		}
	}

	@Override
	public synchronized boolean loginUser(User user) throws PersistenceException {
		try {
			User saved = userStore.selectUser(user.getUsername());
			if (user.equals(saved)) return true;
			else return false;
		}
		catch (Exception e) {
			return false;
		}
	}
}
