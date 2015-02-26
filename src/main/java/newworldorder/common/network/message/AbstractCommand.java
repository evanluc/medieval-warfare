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

}