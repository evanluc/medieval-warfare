package newworldorder.common.service;

import newworldorder.common.persistence.IUserTransaction;

public interface IServerServiceLocator {
	public IMatchController getMatchController();
	public IUserTransaction getUserTransaction();
}
