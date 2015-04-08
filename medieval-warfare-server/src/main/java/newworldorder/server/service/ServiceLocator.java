package newworldorder.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import newworldorder.common.persistence.IUserTransaction;
import newworldorder.common.service.IMatchController;
import newworldorder.common.service.IServerServiceLocator;

@Component
public class ServiceLocator implements IServerServiceLocator {
	private final IMatchController matchController;
	private final IUserTransaction transaction;

	@Autowired
	public ServiceLocator(IMatchController matchController, IUserTransaction transaction) {
		this.matchController = matchController;
		this.transaction = transaction;
	}

	@Override
	public IMatchController getMatchController() {
		return matchController;
	}

	@Override
	public IUserTransaction getUserTransaction() {
		return transaction;
	}
}
