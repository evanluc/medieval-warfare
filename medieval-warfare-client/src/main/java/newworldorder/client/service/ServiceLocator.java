package newworldorder.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import newworldorder.common.service.IClientServiceLocator;
import newworldorder.common.service.IGameLauncher;
import newworldorder.common.service.ISession;

@Component
public class ServiceLocator implements IClientServiceLocator {
	private final IGameLauncher launcher;
	private final ISession session;

	@Autowired
	public ServiceLocator(IGameLauncher launcher, ISession session) {
		super();
		this.launcher = launcher;
		this.session = session;
	}

	@Override
	public IGameLauncher getGameLauncher() {
		launcher.setUsername(session.getUsername());
		return launcher;
	}

	@Override
	public ISession getSession() {
		return session;
	}
}
