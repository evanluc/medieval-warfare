package newworldorder.common.network.message;

public class PrintCommand extends RemoteCommand {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7839191183469247807L;
	private String message;
	
	public PrintCommand(String sender, String message) {
		super(sender);
		this.message = message;
	}

	@Override
	public void execute() {
		System.out.println("[" + getSender() + "] " + message);
	} 
}
