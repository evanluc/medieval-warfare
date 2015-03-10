package newworldorder.game.driver;

import java.util.List;

public interface IModelCommunicator {
	
	public void loadGame(String filePath);
	
	public void saveGame(String filePath);
	
	public void newGame(List<String> playerIds, String mapFilePath);
	
	public int getMapHeight();
	
	public int getMapWidth();
	
	public void informOfUserAction(UIActionType action);
	
	public void informOfUserAction(UIActionType action, int x, int y);
	
	public void informOfUserAction(UIActionType action, int x1, int y1, int x2, int y2);
	
	public UITileDescriptor getTile(int x, int y);
	
	public List<UITileDescriptor> getUpdatedTiles();
	
	public UIVillageDescriptor getVillage(int x, int y);
	
	public void login(String username, String password);
	
	public void logout();
	
	public void joinGame(String gameExchange);
	
	public void leaveGame();

	public boolean hasUpdatedTiles();
}
