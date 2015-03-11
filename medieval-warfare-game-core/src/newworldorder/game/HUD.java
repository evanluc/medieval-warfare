package newworldorder.game;

import newworldorder.game.driver.IModelCommunicator;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class HUD extends Window{

	public HUD(String title, Skin skin, IModelCommunicator iModelCommunicator){
		super(title,skin);
		Label playerTurn = new Label("The current player is " + iModelCommunicator.getCurrentPlayerTurn(),skin);
		Label turnNumber = new Label("The current turn number is " + iModelCommunicator.getTurnNumber(),skin);
		this.add(playerTurn).row();
		this.add(turnNumber).row();
		this.setPosition(0, 0);
	}
	
}
	