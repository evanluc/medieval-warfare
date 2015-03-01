package newworldorder.client.driver;

import java.util.List;

public interface IModelCommunicator {
	
	public IModelCommunicator getInstance();
	
	public void loadGame(String filePath);
	
	public void saveGame(String filePath);
	
	public void newGame(List<String> playerIds, String mapFilePath);
	
	public void informOfUserAction(UIActionType action);
	
	public void informOfUserAction(UIActionType action, int x, int y);
	
	public void informOfUserAction(UIActionType action, int x1, int y1, int x2, int y2);
	
	public UITileDescriptor getTile(int x, int y);
	
	public List<UITileDescriptor> getUpdatedTiles();
	
	public void connect(String serverAddress);
	
	public void disconnect();
	
	public void login(String username, String password);
	
	public void logout();
}
