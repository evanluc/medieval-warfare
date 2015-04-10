package newworldorder.game;

import java.util.List;

import newworldorder.client.model.ModelController;
import newworldorder.client.shared.UIActionType;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;

public class ValidMovesTable extends Window{

	private TiledMapStage stage;
	private Skin skin;
	private TiledMapActor actor;
	private Tree moveTree;

	public ValidMovesTable(String title, Skin skin, Actor actor) {
		super(title, skin);
			this.stage = (TiledMapStage) actor.getStage();
			this.skin = skin;
			this.actor = (TiledMapActor) actor;
			this.moveTree = new Tree(skin);
			createButtons();

			
	}
	

	public void createButtons(){
		List <UIActionType> legalMovesList= ModelController.getInstance().getLegalMoves(actor.getXCell(), actor.getYCell());
		for (UIActionType UIAction : legalMovesList){
			TextButton newButton = new TextButton(uiActionTypeToString(UIAction), skin);
			if (UIAction == UIActionType.MOVEUNIT || UIAction == UIActionType.BOMBARDTILE) newButton.addListener(new SingleClickListener(actor,this,stage,UIAction));
			else newButton.addListener(new DoubleClickListener(actor,this,stage,UIAction));
			this.add(newButton).row();	
			this.sizeBy(newButton.getHeight());
			
		}
		TextButton dismiss = new TextButton("dismiss",skin);
		dismiss.addListener(new dismissListener(this));
		this.add(dismiss).row();
	 	
	}

	
	private String uiActionTypeToString(UIActionType action){
		switch(action){
		case MOVEUNIT: return "Move unit";
		case BOMBARDTILE: return "Bombard tile";
		case BUILDROAD: return "Build road";
		case BUILDTOWER: return "Build tower";
		case BUILDUNITINFANTRY: return "Build infantry";
		case BUILDUNITKNIGHT:return "Build knight";
		case BUILDUNITPEASANT:return "Build peasant";
		case BUILDUNITSOLDIER:return"Build soldier";
		case BUILDUNITCANNON: return"Build cannon";
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
