package newworldorder.game;

import newworldorder.game.driver.IModelCommunicator;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class HUD extends Window{

	public HUD(String title, Skin skin, IModelCommunicator iModelCommunicator, float height){
		super(title,skin);
		Label playerTurn = new Label("current player :\n" + iModelCommunicator.getCurrentPlayerTurn(),skin);
		Label turnNumber = new Label("turn number :\n" + iModelCommunicator.getTurnNumber(),skin);
		this.setWidth(150);
		this.setHeight(150);
		this.add(playerTurn).row();
		this.add(turnNumber).row();
	
		this.setPosition(35,height);
	}
	
}
	