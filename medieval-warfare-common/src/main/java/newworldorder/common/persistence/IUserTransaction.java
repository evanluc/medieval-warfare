package newworldorder.common.persistence;

import newworldorder.common.model.User;

public interface IUserTransaction {

	public void createUser(User user) throws PersistenceException;

	public void deleteUser(User user) throws PersistenceException;

	public void updateUser(User before, User after) throws PersistenceException;
	
	public boolean loginUser(User user) throws PersistenceException;
}
