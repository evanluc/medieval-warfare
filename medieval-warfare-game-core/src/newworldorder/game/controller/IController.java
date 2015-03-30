package newworldorder.game.controller;

public interface IController {
	public boolean login(String username, String password);

	public void logout();

	public void requestGame(int numPlayers);
}
