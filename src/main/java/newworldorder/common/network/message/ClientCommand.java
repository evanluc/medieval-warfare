package newworldorder.common.network.message;

import newworldorder.common.service.IClientServiceLocator;

public abstract class ClientCommand extends AbstractCommand {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IClientServiceLocator serviceLocator;

	public ClientCommand(String sender) {
		super(sender);
	}

	public IClientServiceLocator getServiceLocator() {
		return serviceLocator;
	}
	
	public void setServiceLocator(IClientServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
	}
}
