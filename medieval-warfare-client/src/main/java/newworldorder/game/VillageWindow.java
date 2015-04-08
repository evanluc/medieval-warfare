package newworldorder.game;


import java.util.HashMap;
import java.util.Iterator;

import newworldorder.client.model.ModelController;
import newworldorder.client.shared.UIActionType;
import newworldorder.client.shared.UIVillageDescriptor;
import newworldorder.client.shared.UnitType;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class VillageWindow extends Window{
	Skin skin;
	private TiledMapActor actor;
	private TiledMapStage stage; 
	private Label wood, gold, income, expenses, health;
	private TextButton endTurn;
	private UIVillageDescriptor villageDescription;

	public VillageWindow(String title, Skin skin, TiledMapActor actor, TiledMapStage stage, UIVillageDescriptor villageDescription){
		super(title, skin);
		this.skin = skin;
		this.actor = actor;
		this.stage = stage;

		wood = new Label("Village wood : " + villageDescription.wood,skin);
		gold = new Label("Village gold : " + villageDescription.gold,skin);
		income = new Label("Village income : " + villageDescription.income,skin);
		expenses = new Label("Village expense : " + villageDescription.expenses,skin);
		health = new Label ("Village health : " + villageDescription.health+"/2",skin);
		this.add(wood).row();
		this.add(gold).row();
		this.add(income).row();
		this.add(expenses).row();
		this.add(health).row();


		endTurn = new TextButton("EndTurn",skin);
		endTurn.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				ModelController.getInstance().informOfUserAction(UIActionType.ENDTURN);
				return false;
			}			
		});
	}


	public void renderVillageWindow(){
		wood.setText("Wood : " + villageDescription.wood);
		gold.setText("Gold : " + villageDescription.gold);
		income.setText("Income : " + villageDescription.income);
		expenses.setText("Expenses : " + villageDescription.expenses);
		health.setText("Health : " + villageDescription.health+"/2");
		
	}

	private void createButtons(){

		for (UIActionType types : UIActionType.values()){

			TextButton newButton = new TextButton(uiActionTypeToString(types), skin);
			if (types == UIActionType.MOVEUNIT) newButton.addListener(new SingleClickListener(actor,this,stage,types));
			else newButton.addListener(new DoubleClickListener(actor,this,stage,types));
			this.add(newButton).row();

		}
		TextButton dismiss = new TextButton("dismiss",skin);
		dismiss.addListener(new dismissListener(this));
		this.add(dismiss).row();

	}

	public String uiActionTypeToString(UIActionType action){
		switch(action){
		case MOVEUNIT: return "Move unit";
		case BUILDROAD: return "Build road";
		case BUILDTOWER: return "Build tower";
		case BUILDUNITINFANTRY: return "Build infantry";
		case BUILDUNITKNIGHT:return "Build knight";
		case BUILDUNITPEASANT:return "Build peasant";
		case BUILDUNITSOLDIER:return"Build soldier";
		case CULTIVATEMEADOW:return"Cultivate meadow";
		case ENDTURN:return"End your turn";
		case UPGRADEUNITINFANTRY:return"Upgrade to infantry";
		case UPGRADEUNITKNIGHT:return"Upgrade to knight";
		case UPGRADEUNITSOLDIER:return "Upgrade to soldier";
		case UPGRADEVILLAGEFORT:return "Upgrade to fort";
		case UPGRADEVILLAGETOWN:return "Upgrade to town";
		}
		return null;
	}

}
