package newworldorder.common.network.message;

public class LoginCommand extends RemoteCommand {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7839191183469247807L;
	private String password;
	
	public LoginCommand(String sender, String password) {
		super(sender);
		this.password = password;
	}

	@Override
	public void execute() {
		System.out.println("[" + getSender() + ", " + password + "]");
	} 
}
