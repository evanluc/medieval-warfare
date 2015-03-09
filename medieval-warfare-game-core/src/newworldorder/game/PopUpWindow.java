package newworldorder.game;


import newworldorder.game.driver.UIActionType;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class PopUpWindow extends Window{
	
	private Skin skin;
	private TiledMapActor actor;
	private TiledMapStage stage; 

	
	public PopUpWindow(String title, Skin skin, TiledMapActor actor, TiledMapStage stage){
		super(title, skin);
		this.skin = skin;
		this.actor = actor;
		this.stage = stage;
		createButtons();
	}
	
	private void createButtons(){
		
		for (UIActionType types : UIActionType.values()){

			TextButton newButton = new TextButton(types.toString(), skin);
			if (types == UIActionType.MOVEUNIT) newButton.addListener(new SingleClickListener(actor,this,stage,types));
			else newButton.addListener(new DoubleClickListener(actor,this,stage,types));
			this.add(newButton).row();

		}
	}

}
