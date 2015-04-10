package newworldorder.common.service;

import java.util.Set;

import newworldorder.common.model.IStats;
import newworldorder.common.persistence.IUserTransaction;

public interface IServerServiceLocator {
	public IMatchController getMatchController();
	public IUserTransaction getUserTransaction();
	public IGameInitializer getGameInitializer();
	public Set<String> getOnlinePlayers();
	public IStats getStatsForPlayer(String username);
	public void win(String username);
	public void loss(String username);
}
