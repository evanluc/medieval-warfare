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
	public void clicked(InputEvent event, float x, float y) {
		UIVillageDescriptor vil = ((TiledMapStage) actor.getStage()).getModel().getVillage(actor.getXCell(),
				actor.getYCell());

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
			stage.addActor(notTurnWindow);
		}
		else{

		/*	if (vil != null) {
				PopUpWindow popUp = new PopUpWindow("Village Info", skin, actor, stage, vil);
				popUp.setWidth(250);
				popUp.setHeight(150);
				popUp.setPosition(stage.getCamera().position.x - popUp.getWidth() / 2, stage.getCamera().position.y - popUp.getHeight()
						/ 2);

				stage.addActor(popUp);

			}*/ if (stage.getMultiActionInput() == false) {
				
				Cell outlineCell = stage.getTiledMapDescritors().outlineLayer.getCell(actor.getXCell(),actor.getYCell());
				outlineCell.setTile(stage.getTiledMapDescritors().outlineTile);
				
				//creating popup window with moves
				
				PopUpWindow popUp = new PopUpWindow("moves", skin, actor, stage);
				popUp.setWidth(250);
				popUp.setHeight(450);
				popUp.setPosition(stage.getCamera().position.x - popUp.getWidth() / 2, stage.getCamera().position.y - popUp.getHeight()
						/ 2);
				stage.addActor(popUp);
			}

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
	}
	@Override
	public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
		Cell highlightCell = stage.getTiledMapDescritors().highlightLayer.getCell(actor.getXCell(),actor.getYCell());
		highlightCell.setTile(stage.getTiledMapDescritors().redTile);
	}

	@Override
	public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
		Cell highlightCell = stage.getTiledMapDescritors().highlightLayer.getCell(actor.getXCell(),actor.getYCell());
		highlightCell.setTile(stage.getTiledMapDescritors().nullTile);
	}

}