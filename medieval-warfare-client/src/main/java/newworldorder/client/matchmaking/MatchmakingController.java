package newworldorder.client.matchmaking;

import newworldorder.common.network.command.LoginCommand;


public class MatchmakingController {
	private static MatchmakingController instance = null;
	
	private MatchmakingController() {
		super();
	}
	
	public static MatchmakingController getInstance() {
		if (instance == null) {
			instance = new MatchmakingController();
		}
		return instance;
	}
	
	public void login(String username, String password){
		LoginCommand loginCommand = new LoginCommand(username, password);
		
	}
}
