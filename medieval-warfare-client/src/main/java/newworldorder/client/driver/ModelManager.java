package newworldorder.client.driver;

import java.util.List;

import newworldorder.client.model.GameEngine;

public class ModelManager implements IModelCommunicator {
	
	ModelManager instance;
	GameEngine engine;
	
	private ModelManager() {
		engine = new GameEngine();
	}
	
	@Override
	public ModelManager getInstance() {
		if (instance == null) {
			instance = new ModelManager();
		}
		return instance;
	}

	@Override
	public void informOfUserAction(UIActionType action, int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void informOfUserAction(UIActionType action, int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub

	}

	@Override
	public UITileDescriptor getTile(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UITileDescriptor> getUpdatedTiles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadGame(String filePath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveGame(String filePath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newGame(List<String> playerIds, String mapFilePath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void informOfUserAction(UIActionType action) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connect(String serverAddress) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void login(String username, String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub
		
	}

}
