package newworldorder.common.network.command;


public class CreateAccountCommand extends RemoteCommand {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4192064055097480790L;

	public CreateAccountCommand(String username, String password) {
		super(username);
	}

	@Override
	public void execute() {
		// TODO
	}

}
