package newworldorder.game;


import newworldorder.game.driver.UIVillageDescriptor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;


public class TiledMapClickListener extends ClickListener {

	private TiledMapActor actor;
	private TiledMapStage stage;
	

	public TiledMapClickListener(TiledMapActor actor) {
		this.actor = actor;
		stage = (TiledMapStage) actor.getStage();
		
	}

	@Override
	public void clicked(InputEvent event, float x, float y) {
	//	System.out.println(actor.cell + " has been clicked.");
		UIVillageDescriptor vil = ((TiledMapStage) actor.getStage()).getModel().getVillage(actor.getXCell(),actor.getYCell());
		
		
		final TiledMapStage stage = (TiledMapStage) actor.getStage();
		final Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
		
		if(vil != null){
		//begin temp
		PopUpWindow popUp = new PopUpWindow("Village Info", skin, actor, stage, vil);
			popUp.setPosition(stage.getWidth() / 2 - popUp.getWidth() / 2, stage.getHeight() / 2 - popUp.getHeight() / 2);	
			popUp.setWidth(250);
			popUp.setHeight(150);
			stage.addActor(popUp);
//			}
////end temp		
		}		
		else if(stage.getMultiActionInput() == false){
		PopUpWindow popUp = new PopUpWindow("moves", skin, actor, stage);
		popUp.setPosition(stage.getWidth() / 2 - popUp.getWidth() / 2, stage.getHeight() / 2 - popUp.getHeight() / 2);	
		popUp.setWidth(250);
		popUp.setHeight(450);
		stage.addActor(popUp);
		}
	
		else if (stage.getMultiActionInput()==true){
			TiledMapActor previousActor = stage.getPreviousActor();
			System.out.println("moving tile from " + previousActor.getXCell() + " " + previousActor.getYCell() +  " to " + actor.getXCell() + " " + actor.getYCell());
			stage.setPreviousActor(null);
			stage.setMultiActionInput(false);
			
		}
	}

}


