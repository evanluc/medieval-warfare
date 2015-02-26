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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((serviceLocator == null) ? 0 : serviceLocator.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;

		return true;
	}
}
