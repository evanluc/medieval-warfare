package newworldorder.game;

import java.util.List;

import newworldorder.client.model.ModelController;
import newworldorder.client.shared.UIActionType;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class UIStage extends Stage{
	private Skin skin;
	private Tree tree;
	private Table table;
	private HUD hud;
	private Window notTurnWindow;
	private Window yourTurnWindow;
	
	
	public UIStage(Skin skin){
		this.skin = skin;
		
		hud = new HUD("HUD", skin, ModelController.getInstance().getCurrentTurnPlayer(), ModelController.getInstance().getTurnNumber());
		hud.setPosition(5,this.getViewport().getScreenHeight()-5);
		this.addActor(hud);
		
		
		this.notTurnWindow = new Window("Not your turn!", skin);
		Label stopItText = new Label("Your opponent is still making moves.\n Please wait until their turn ends\n"
				+ "In the meantime you can still survey your tiles and villages.",skin);
		notTurnWindow.add(stopItText).row();			
		notTurnWindow.setWidth(250);
		notTurnWindow.setHeight(150);
		notTurnWindow.setPosition(this.getCamera().position.x - notTurnWindow.getWidth()/2, 5);
		this.addActor(notTurnWindow);
		


//		this.yourTurnWindow = new Window("It's your turn!", skin);
//		Label goForItText = new Label("Select a tile and make a move!",skin);
//		notTurnWindow.add(goForItText).row();			
//		notTurnWindow.setWidth(450);
//		notTurnWindow.setHeight(100);
//		notTurnWindow.setPosition(this.getCamera().position.x - yourTurnWindow.getWidth() / 2, 5);
//		this.addActor(yourTurnWindow);
	
		
		this.table = new Table();
		table.setFillParent(true);
		this.addActor(table);
		this.tree = new Tree(skin);
		table.add(tree).fill().expand();
		table.left();
	

	}
	//here we render stuff for the current turn
	public void currentTurnRenderUpdate(){
		notTurnWindow.setVisible(false);
		hud.setCurrentUsername(ModelController.getInstance().getCurrentTurnPlayer());
		hud.setCurrentTurn(ModelController.getInstance().getTurnNumber());
	}
	
	public void notTurnRenderUpdate(){
//		if (yourTurnWindow.isVisible()) yourTurnWindow.setVisible(false);
		notTurnWindow.setVisible(true);
		hud.setCurrentUsername(ModelController.getInstance().getCurrentTurnPlayer());
		hud.setCurrentTurn(ModelController.getInstance().getTurnNumber());
		if (tree != null) tree.clear();
		
		
	}
	
	public void buttonRenderUpdate(TiledMapActor selectedCell){
		tree.clear();
		List <UIActionType> legalMovesList= ModelController.getInstance().getLegalMoves(selectedCell.getXCell(), selectedCell.getYCell());
		for (UIActionType UIAction : legalMovesList){
			TextButton newButton = new TextButton(uiActionTypeToString(UIAction), skin);
			tree.add(new Node(newButton));
			if (UIAction == UIActionType.MOVEUNIT) newButton.addListener(new SingleClickListener(selectedCell,tree,(TiledMapStage) selectedCell.getStage(),UIAction));
			else newButton.addListener(new DoubleClickListener(selectedCell,tree,(TiledMapStage) selectedCell.getStage(),UIAction));
		} 
		
	}
	
	
	public void flushWindow(){
		
	}
	
	
	private String uiActionTypeToString(UIActionType action){
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
		case BOMBARDTILE:return "Upgrade bombarding tile!";
		case BUILDUNITCANNON: return "Upgrade to town";
		case UPGRADEVILLAGECASTLE: return "Upgrade to castle";

		}
		return null;
	}
}
