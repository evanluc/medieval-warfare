package newworldorder.client.controller;

import java.util.List;

public interface IController {
	public boolean login(String username, String password);
	
	public boolean newAccount(String username, String password);

	public void logout();

	public void requestGame(int numPlayers);
	
	public void invitePlayer(String username, String invitingPlayer);
	
	public void acceptInvite();
	
	public void startPartyGame();
	
	public List<String> getPlayersInParty();
	
	public String getLeaderOfParty();
	
	public void leaveParty(String username);
	
	public void kickFromParty(String toKick);
}
