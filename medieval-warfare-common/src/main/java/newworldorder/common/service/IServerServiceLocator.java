package newworldorder.common.service;

import java.util.Set;

import newworldorder.common.persistence.IUserTransaction;

public interface IServerServiceLocator {
	public IMatchController getMatchController();
	public IUserTransaction getUserTransaction();
	public IGameInitializer getGameInitializer();
	public Set<String> getOnlinePlayers();
}
