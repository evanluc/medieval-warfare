package newworldorder.common.network.message;

import java.io.Serializable;

public abstract class AbstractCommand implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sender;

	public AbstractCommand(String sender) {
		super();
		this.sender = sender;
	}

	public String getSender() {
		return sender;
	}

	public abstract void execute();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sender == null) ? 0 : sender.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractCommand other = (AbstractCommand) obj;
		if (sender == null) {
			if (other.sender != null)
				return false;
		} else if (!sender.equals(other.sender))
			return false;
		return true;
	}
}