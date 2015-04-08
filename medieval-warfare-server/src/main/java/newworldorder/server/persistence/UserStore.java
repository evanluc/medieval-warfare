package newworldorder.server.persistence;

import newworldorder.common.model.User;

interface UserStore {
	public boolean containsUsername(String username) throws Exception;
	
	public void insertUser(User user) throws Exception;
	
	public User selectUser(String username) throws Exception;
}
