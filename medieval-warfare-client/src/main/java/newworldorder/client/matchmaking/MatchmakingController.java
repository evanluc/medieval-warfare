package newworldorder.client.matchmaking;

import newworldorder.common.network.command.LoginCommand;

/**
 * This class exposes methods that deal with user/party management that 
 * the gui can call.
 */
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
		//Send login command to server
	}
	
	public void newAccount(String username, String password){
		//Send new account command to server
	}
	
	public void updateAccountInfo(String username, String password){
		//Send a command to update info?
	}
	
	public void invitePlayer(String username, Party invitingParty){
		//send a command to username to set its pendingPartyRequest to invitingParty.playersInParty
	}
	
	public void acceptInvite(){
		if(LocalUser.getInstance().getPendingPartyRequest() != null){
			//send a command to getPendingPartyRequest to add this player to party
		}
	}
	
	public void newPlayerAddedToParty(){
		//called by leader of party after someone else calls acceptInvite()
		//Sends a command to all players in party to update their party list
	}

	public void startGame(){
		if(LocalUser.getInstance().getUsername().equals(Party.getInstance().getLeader())){
		//Sends a startGame command with list of players in party
		}
	}
}
