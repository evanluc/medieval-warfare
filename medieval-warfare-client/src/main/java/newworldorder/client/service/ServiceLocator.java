package newworldorder.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import newworldorder.common.service.IClientServiceLocator;
import newworldorder.common.service.IGameLauncher;

@Component
public class ServiceLocator implements IClientServiceLocator {
	private final IGameLauncher launcher;
	
	@Autowired
	public ServiceLocator(IGameLauncher launcher) {
		super();
		this.launcher = launcher;
	}

	@Override
	public IGameLauncher getGameLauncher() {
		return launcher;
	}

}
