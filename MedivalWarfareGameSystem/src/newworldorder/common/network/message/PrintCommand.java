package newworldorder.common.network.message;

public class PrintCommand extends RemoteCommand {
	private String message;
	
	public PrintCommand(String sender, String message) {
		super(sender);
	}

	@Override
	public void execute() {
		System.out.println("[" + getSender() + "] " + message);
	} 
}
