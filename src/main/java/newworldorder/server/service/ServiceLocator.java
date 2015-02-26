package newworldorder.server.service;

import java.io.IOException;

import newworldorder.common.service.IMatchController;
import newworldorder.common.service.IServerServiceLocator;

public class ServiceLocator implements IServerServiceLocator {
	private static ServiceLocator instance;
	
	private IMatchController matchController;
	
	private ServiceLocator() throws IOException {
		matchController = MatchController.getInstance();
	}
	
	public static ServiceLocator getInstance() throws IOException {
		if (instance == null) {
			instance = new ServiceLocator();
		}
		
		return instance;
	}

	@Override
	public IMatchController getMatchController() {
		return matchController;
	}
}
