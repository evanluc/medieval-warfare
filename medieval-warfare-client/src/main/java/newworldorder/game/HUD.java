package newworldorder.game;
import newworldorder.client.model.ModelController;
import newworldorder.client.shared.UIActionType;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class HUD extends Window {
	private Label playerTurnLabel, turnNumberLabel, playerTurnText, turnNumberText;
	private TextButton endTurn;

	public HUD(String title, Skin skin, String userName, int turnNumber) {
		super(title, skin);
		playerTurnLabel = new Label("Current Player:", skin);
		playerTurnText = new Label("Player1", skin);
		turnNumberText = new Label("0", skin);
		
		this.add(playerTurnLabel).row();
		this.add(playerTurnText).row();
		this.add(turnNumberText).row();
		
		endTurn = new TextButton("EndTurn",skin);
		endTurn.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				ModelController.getInstance().informOfUserAction(UIActionType.ENDTURN);
				return false;
			}			
		});
		
		this.add(endTurn).row();
	}

	public void setCurrentUsername(String currentUsername) {
		playerTurnText.setText(currentUsername);
	}

	public void setCurrentTurn(int currentTurn) {
		turnNumberText.setText("Turn number : " + String.valueOf(currentTurn));
	}
}
