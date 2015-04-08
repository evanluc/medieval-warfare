package newworldorder.server.service;

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

	@Autowired
	public ServiceLocator(IMatchController matchController, IUserTransaction transaction, IGameInitializer gameInit) {
		this.matchController = matchController;
		this.transaction = transaction;
		this.gameInitializer = gameInit;
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
}
