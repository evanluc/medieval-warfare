package newworldorder.common.service;

public class ServiceLocator implements IServiceLocator {
	private static ServiceLocator instance;
	
	private ServiceLocator() {};
	
	public static ServiceLocator getInstance() {
		if (instance == null) {
			instance = new ServiceLocator();
		}
		
		return instance;
	}

	@Override
	public IMatchController getMatchController() {
		return MatchController.getInstance();
	}
}
