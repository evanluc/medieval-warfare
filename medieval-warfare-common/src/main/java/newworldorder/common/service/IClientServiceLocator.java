package newworldorder.common.service;

public interface IClientServiceLocator {
	public IGameLauncher getGameLauncher();
	public ISession getSession();
}
