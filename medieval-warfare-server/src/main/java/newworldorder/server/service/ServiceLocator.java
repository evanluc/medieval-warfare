package newworldorder.server.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import newworldorder.common.persistence.IUserTransaction;
import newworldorder.common.service.IGameInitializer;
import newworldorder.common.service.IMatchController;
import newworldorder.common.service.IServerServiceLocator;

@Component
public class ServiceLocator implements IServerServiceLocator {
	private final IMatchController matchController;
	private final IUserTransaction transaction;
	private final IGameInitializer gameInitializer;
	private final OnlineUsers onlineUsers;

	@Autowired
	public ServiceLocator(IMatchController matchController, IUserTransaction transaction, IGameInitializer gameInit, OnlineUsers users) {
		this.matchController = matchController;
		this.transaction = transaction;
		this.gameInitializer = gameInit;
		this.onlineUsers = users;
	}

	@Override
	public IMatchController getMatchController() {
		return matchController;
	}

	@Override
	public IUserTransaction getUserTransaction() {
		return transaction;
	}

	@Override
	public IGameInitializer getGameInitializer() {
		return gameInitializer;
	}

	@Override
	public Set<String> getOnlinePlayers() {
		return onlineUsers.getAll();
	}
}
