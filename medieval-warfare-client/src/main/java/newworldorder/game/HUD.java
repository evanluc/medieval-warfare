package newworldorder.game;
import newworldorder.client.model.ModelController;
import newworldorder.client.shared.UIActionType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class HUD extends Window {
	private Label playerTurnLabel, playerTurnText, turnNumberText;
	private TextButton endTurn, saveGameButton, leaveGameButton;
	private UIStage stage;
	private ModelController modelController = ModelController.getInstance();

	public HUD(String title, Skin skin, String userName, int turnNumber, UIStage gameScreen) {
		super(title, skin);
		this.stage = gameScreen;
		playerTurnLabel = new Label("Current Player:", skin);
		playerTurnText = new Label("Player1", skin);
		turnNumberText = new Label("0", skin);
		
		this.add(playerTurnLabel).row();
		this.add(playerTurnText).row();
		this.add(turnNumberText).row();
		this.setSize(150, 175);
		
		endTurn = new TextButton("End Turn",skin);
		endTurn.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				ModelController.getInstance().informOfUserAction(UIActionType.ENDTURN);
				return false;
			}			
		});
		
		this.add(endTurn).row();
		
		saveGameButton = new TextButton("Save Game", skin);
		saveGameButton.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Dialog saveGameDialog = new Dialog("Save Game", skin){
					@Override
					protected void result (Object object) {
						this.hide();	
					}
				};
				
				Window saveGameWindow = new Window("Please provide a save game file name", skin);
				saveGameWindow.setMovable(false);
				List<String> saveGameList = new List<>(skin);
				FileHandle[] files = Gdx.files.local("assets/saves/").list();
				String[] saveFiles = new String[files.length];
				for(int i = 0; i < files.length; i++) {
					saveFiles[i] = files[i].name();
				}
				Array<String> p = new Array<>(saveFiles);
				saveGameList.setItems(p);
				ScrollPane saveGameListPane = new ScrollPane(saveGameList, skin);
			
				saveGameWindow.add(saveGameListPane).expand().fill().row();
				saveGameDialog.add(saveGameWindow).expandY().fill().pad(20).row();
				TextField saveFileNameTextField = new TextField("", skin);
				saveGameWindow.add(saveFileNameTextField).expand().fill().row();
				TextButton confirmButton = new TextButton("Save Game", skin);
				
				confirmButton.addListener(new ClickListener() {
					@Override
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						if (!saveFileNameTextField.getText().isEmpty()) {
							modelController.saveGame("assets/saves/"+saveFileNameTextField.getText()+".mwg");
						}
						return true;
					}
				});
				
				saveGameListPane.addListener(new ClickListener(){
					@Override
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					if (saveGameList.getSelected() != null) saveFileNameTextField.setText(saveGameList.getSelected().substring(0, saveGameList.getSelected().length()-4));
					
					return false;
					}
				});
				
				saveGameDialog.button(confirmButton).bottom().row();
				TextButton closeButton = new TextButton("Close", skin);
				saveGameDialog.button(closeButton).bottom().row();
				saveGameDialog.show(stage);
				return true;
			}
		});
		
		this.add(saveGameButton).row();
		
		leaveGameButton = new TextButton("Leave Game", skin);
		
		leaveGameButton.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Dialog confirmDialog = new Dialog("Leave Game", skin){
					@Override
					protected void result (Object object) {
						this.hide();	
					}
				};
				
				Window confirmWindow = new Window("Are you sure?", skin);
				confirmWindow.setMovable(false);
				List<String> saveGameList = new List<>(skin);
				TextButton confirmButton = new TextButton("Leave", skin);
				confirmButton.addListener(new ClickListener() {
					@Override
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						return false;
					}
				});
				
				confirmDialog.button(confirmButton).row();
				TextButton closeButton = new TextButton("Close", skin);
				confirmDialog.button(closeButton).row();
				
				confirmDialog.show(stage);
				return true;
			}
		});
		
		this.add(leaveGameButton).row();
	}

	public void setCurrentUsername(String currentUsername) {
		playerTurnText.setText(currentUsername);
	}

	public void setCurrentTurn(int currentTurn) {
		turnNumberText.setText("Turn number : " + String.valueOf(currentTurn));
	}
	
	public void yourTurnRenderUpdate(){
		if (endTurn.hasParent() == false){ System.out.println("has no parent");
			this.add(endTurn).top().row();
		 	
		}
	}
	
	public void notTurnRenderUpdate(){
		if(endTurn.hasParent()) System.out.println("has parent");
		this.removeActor(endTurn);
		
	}
}
