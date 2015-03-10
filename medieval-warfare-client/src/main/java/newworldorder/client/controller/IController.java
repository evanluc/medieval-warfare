package newworldorder.client.controller;

public interface IController {
	public boolean login(String username, String password);
	
	public void requestGame(int numPlayers);
}
