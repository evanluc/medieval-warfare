package newworldorder.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import newworldorder.client.model.ModelController;
import newworldorder.client.shared.UIActionType;
import newworldorder.client.shared.UIVillageDescriptor;

public class TiledMapClickListener extends ClickListener {
	private TiledMapActor actor;
	private TiledMapStage stage;
	private ModelController controller = ModelController.getInstance();
	private Skin skin;

	public TiledMapClickListener(TiledMapActor actor, Skin skin) {
		this.actor = actor;
		stage = (TiledMapStage) actor.getStage();
		this.skin = skin;
		this.setButton(Input.Buttons.LEFT);
	}

	@Override
	  public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)  {
		UIVillageDescriptor vil = ((TiledMapStage) actor.getStage()).getModel().getVillage(actor.getXCell(),
				actor.getYCell());

		System.out.println("clicked on tiled map click listener");
		
		final TiledMapStage stage = (TiledMapStage) actor.getStage();

		if(stage.getModel().isLocalPlayersTurn()== false){
			
			Window notTurnWindow = new Window("Not your turn!", skin);
			Label stopItText = new Label("Your opponent is still making moves.\n Please wait until their turn ends\n",skin);
			TextButton dismiss = new TextButton("dismiss",skin);
			dismissListener dismissListener = new dismissListener(notTurnWindow);
			notTurnWindow.add(stopItText).row();			
			notTurnWindow.add(dismiss).row();
			notTurnWindow.addListener(dismissListener);
			notTurnWindow.setWidth(450);
			notTurnWindow.setHeight(100);
			notTurnWindow.setPosition(stage.getCamera().position.x - notTurnWindow.getWidth() / 2, stage.getCamera().position.y - notTurnWindow.getHeight()
					/ 2);
//			stage.addActor(notTurnWindow);
			stage.getUIStage().addActor(notTurnWindow);
		}
		
		else{
			if (stage.getMultiActionInput() == false) {

				if(stage.getCurrentlyOutlined() != null){
					TiledMapActor previouslyOutlined = stage.getCurrentlyOutlined();
					Cell previouslyOutlinedCell = stage.getTiledMapDescriptors().outlineLayer.getCell(previouslyOutlined.getXCell(),previouslyOutlined.getYCell());					
					previouslyOutlinedCell.setTile(stage.getTiledMapDescriptors().nullTile);
				}

				Cell outlineCell = stage.getTiledMapDescriptors().outlineLayer.getCell(actor.getXCell(),actor.getYCell());
				outlineCell.setTile(stage.getTiledMapDescriptors().outlineTile);
				stage.setCurrentlyOutlined(actor);
				stage.getUIStage().buttonRenderUpdate(actor);


				//creating popup window with moves
//
//				ValidMovesTable popUp = new ValidMovesTable("Moves", skin, actor);
//				popUp.setPosition(stage.getCamera().position.x - popUp.getWidth() / 2, stage.getCamera().position.y - popUp.getHeight()
//						/ 2);
//				
//				stage.addActor(popUp);
			}

			//case for movement. 
			if (stage.getMultiActionInput() == true) {
				TiledMapActor previousActor = stage.getPreviousActor();
				System.out.println("moving tile from " + previousActor.getXCell() + " " + previousActor.getYCell() + " to "
						+ actor.getXCell() + " " + actor.getYCell());
				controller.informOfUserAction(UIActionType.MOVEUNIT, previousActor.getXCell(), previousActor.getYCell(),
						actor.getXCell(), actor.getYCell());
				stage.setPreviousActor(null);
				stage.setMultiActionInput(false);
			}
		}
		return false;
	}
	@Override
	public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
		Cell highlightCell = stage.getTiledMapDescriptors().highlightLayer.getCell(actor.getXCell(),actor.getYCell());
		highlightCell.setTile(stage.getTiledMapDescriptors().redTile);
	}

	@Override
	public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
		Cell highlightCell = stage.getTiledMapDescriptors().highlightLayer.getCell(actor.getXCell(),actor.getYCell());
		highlightCell.setTile(stage.getTiledMapDescriptors().nullTile);
	}

}
