package newworldorder.common.network.message;

import newworldorder.common.service.IServerServiceLocator;

public abstract class RemoteCommand extends AbstractCommand {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServerServiceLocator serviceLocator;

	public RemoteCommand(String sender) {
		super(sender);
	}

	public IServerServiceLocator getServiceLocator() {
		return serviceLocator;
	}
	
	public void setServiceLocator(IServerServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
	}
}
