package newworldorder.game;

import newworldorder.game.driver.IModelCommunicator;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class HUD extends Window{
	private String currentPlayerName;
	private int turnNumber;
	private Skin skin;
	
	
	public HUD(String title, Skin skin, String userName, int turnNumber, float height){
		super(title,skin);
		this.skin = skin;
	}
	
	public void setCurrentUsername(String currentUsername){
		Label playerTurnText = new Label("current player :\n" + currentUsername,skin);
		this.add(playerTurnText).row();

	}
	
	public void setCurrentTurn(int currentTurn){
		Label turnNumberText = new Label("turn number :\n" + currentTurn,skin);
		this.add(turnNumberText).row();
	
	}
	
}
	