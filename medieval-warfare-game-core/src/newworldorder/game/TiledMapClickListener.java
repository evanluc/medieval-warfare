package newworldorder.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import newworldorder.game.driver.IModelCommunicator;
import newworldorder.game.driver.ModelManager;
import newworldorder.game.driver.UIActionType;
import newworldorder.game.driver.UIVillageDescriptor;

public class TiledMapClickListener extends ClickListener {

	private TiledMapActor actor;
	private TiledMapStage stage;
	private IModelCommunicator controller = ModelManager.getInstance();

	public TiledMapClickListener(TiledMapActor actor) {
		this.actor = actor;
		stage = (TiledMapStage) actor.getStage();

	}

	@Override
	public void clicked(InputEvent event, float x, float y) {
		UIVillageDescriptor vil = ((TiledMapStage) actor.getStage()).getModel().getVillage(actor.getXCell(),
				actor.getYCell());

		final TiledMapStage stage = (TiledMapStage) actor.getStage();
		final Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));

		if(stage.getModel().isLocalPlayersTurn()!= false){
			Window notTurnWindow = new Window("Not your turn!", skin);
			Label stopItText = new Label("Your opponent is still making moves. Please wait until his turn end",skin);
			TextButton dismiss = new TextButton("dismiss",skin);
			dismissListener dismissListener = new dismissListener(notTurnWindow);
			notTurnWindow.add(dismiss);
			notTurnWindow.add(stopItText);
			notTurnWindow.addListener(dismissListener);
			notTurnWindow.setWidth(250);
			notTurnWindow.setHeight(100);
			stage.addActor(notTurnWindow);
		}
		else{

			if (vil != null) {
				PopUpWindow popUp = new PopUpWindow("Village Info", skin, actor, stage, vil);
				popUp.setWidth(250);
				popUp.setHeight(150);
				popUp.setPosition(stage.getCamera().position.x - popUp.getWidth() / 2, stage.getCamera().position.y - popUp.getHeight()
						/ 2);

				stage.addActor(popUp);

			} else if (stage.getMultiActionInput() == false) {
				PopUpWindow popUp = new PopUpWindow("moves", skin, actor, stage);
				popUp.setWidth(250);
				popUp.setHeight(450);
				popUp.setPosition(stage.getCamera().position.x - popUp.getWidth() / 2, stage.getCamera().position.y - popUp.getHeight()
						/ 2);
				stage.addActor(popUp);
			}

			else if (stage.getMultiActionInput() == true) {
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

}
