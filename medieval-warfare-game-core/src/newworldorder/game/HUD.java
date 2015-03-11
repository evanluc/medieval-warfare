package newworldorder.game;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class HUD extends Window {
	private Label playerTurnText, turnNumberText;

	public HUD(String title, Skin skin, String userName, int turnNumber, float height) {
		super(title, skin);
		playerTurnText = new Label("Current player:\n", skin);
		turnNumberText = new Label("Turn number\n: 0", skin);
		this.add(playerTurnText).row();
		this.add(turnNumberText).row();
	}

	public void setCurrentUsername(String currentUsername) {
		playerTurnText.setText("Current player:\n" + currentUsername);
	}

	public void setCurrentTurn(int currentTurn) {
		turnNumberText.setText("Turn number:\n" + currentTurn);
	}
}
