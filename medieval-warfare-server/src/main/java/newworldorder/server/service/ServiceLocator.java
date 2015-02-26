package newworldorder.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import newworldorder.common.service.IMatchController;
import newworldorder.common.service.IServerServiceLocator;

@Component
public class ServiceLocator implements IServerServiceLocator {
	private IMatchController matchController;
	
	@Autowired
	private ServiceLocator(IMatchController matchController) {
		this.matchController = matchController;
	}

	@Override
	public IMatchController getMatchController() {
		return matchController;
	}
}
