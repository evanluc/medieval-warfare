package newworldorder.common.network.command;

public class ProbeCommand extends ClientCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1553914492349090024L;
	private boolean online = false;
	
	public ProbeCommand(String sender) {
		super(sender);
	}

	@Override
	public void execute() {
		online = true;
	}
	
	public boolean getStatus() {
		return online;
	}
}
