package newworldorder.common.network.message;

import java.io.Serializable;

import newworldorder.common.service.IServiceLocator;

public abstract class RemoteCommand implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sender;
	private IServiceLocator serviceLocator;

	public RemoteCommand(String sender) {
		super();
		this.sender = sender;
	}

	public String getSender() {
		return sender;
	}
	
	public IServiceLocator getServiceLocator() {
		return serviceLocator;
	}
	
	public void setServiceLocator(IServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
	}
	
	public abstract void execute();
}
