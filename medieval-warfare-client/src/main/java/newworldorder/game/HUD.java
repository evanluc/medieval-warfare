package newworldorder.game;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class HUD extends Window {
	private Label playerTurnLabel, turnNumberLabel, playerTurnText, turnNumberText;

	public HUD(String title, Skin skin, String userName, int turnNumber) {
		super(title, skin);
		playerTurnLabel = new Label("Current Player:", skin);
		playerTurnText = new Label("Player1", skin);
		turnNumberLabel = new Label("Turn Number:", skin);
		turnNumberText = new Label("0", skin);
		
		this.add(playerTurnLabel).row();
		this.add(playerTurnText).row();
		this.add(turnNumberLabel).row();
		this.add(turnNumberText).row();
	}

	public void setCurrentUsername(String currentUsername) {
		playerTurnText.setText(currentUsername);
	}

	public void setCurrentTurn(int currentTurn) {
		turnNumberText.setText(String.valueOf(currentTurn));
	}
}
