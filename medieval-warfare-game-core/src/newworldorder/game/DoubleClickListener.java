package newworldorder.game;

import newworldorder.game.driver.UIActionType;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class DoubleClickListener extends ClickListener{
	private TiledMapActor actor;
	private Window window;
	private TiledMapStage stage; 
	private UIActionType actionType;
	
	public DoubleClickListener(TiledMapActor actor, Window window, TiledMapStage stage, UIActionType actionType){
		this.actor = actor; 
		this.window = window;
		this.stage = stage; 
		this.actionType = actionType;
	}
	
	@Override
	public void clicked(InputEvent event, float x, float y) {
		window.setVisible(false);
		System.out.println("sending action type : " + actionType);
		
	}
}
