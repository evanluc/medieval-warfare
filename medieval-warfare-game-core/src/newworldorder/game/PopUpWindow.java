package newworldorder.game;


import newworldorder.game.driver.UIActionType;
import newworldorder.game.driver.UIVillageDescriptor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;


public class PopUpWindow extends Window{
	Skin skin;
	private TiledMapActor actor;
	private TiledMapStage stage; 


	public PopUpWindow(String title, Skin skin, TiledMapActor actor, TiledMapStage stage){
		super(title, skin);
		this.skin = skin;
		this.actor = actor;
		this.stage = stage;
		createButtons();
	}

	public PopUpWindow(String title, Skin skin, TiledMapActor actor, TiledMapStage stage, UIVillageDescriptor villageDescription){
		super(title, skin);
		this.skin = skin;
		this.actor = actor;
		this.stage = stage;
		createVillageDescription(villageDescription);


	}

	private void createVillageDescription(UIVillageDescriptor villageDescription) {
		Label wood = new Label("Village wood : " + villageDescription.wood,skin);
		Label gold = new Label("Village gold : " + villageDescription.gold,skin);
		Label income = new Label("Village income : " + villageDescription.income,skin);
		TextButton dismiss = new TextButton("dismiss",skin);
		dismiss.addListener(new dismissListener(this));
		this.add(wood).row();
		this.add(gold).row();
		this.add(income).row();
		this.add(dismiss).row();

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