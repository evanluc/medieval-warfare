package newworldorder.game.driver;

import java.util.List;

import newworldorder.common.matchmaking.GameInfo;

public interface IModelCommunicator {

	public void loadGame(String filePath);

	public void saveGame(String filePath);

	public void newGame(String username, GameInfo gameInfo, String mapFilePath);

	public int getMapHeight();

	public int getMapWidth();

	public void informOfUserAction(UIActionType action);

	public void informOfUserAction(UIActionType action, int x, int y);

	public void informOfUserAction(UIActionType action, int x1, int y1, int x2, int y2);

	public UITileDescriptor getTile(int x, int y);

	public List<UITileDescriptor> getUpdatedTiles();

	public UIVillageDescriptor getVillage(int x, int y);

	public void leaveGame();

	public boolean hasUpdatedTiles();

	public boolean isLocalPlayersTurn();

	public void setLocalPlayerId(int playerId);

	public int getLocalPlayerId();

	public String getCurrentPlayerTurn();

	public int getTurnNumber();
}
