package newworldorder.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TiledMapClickListener extends ClickListener {

	private TiledMapActor actor;

	public TiledMapClickListener(TiledMapActor actor) {
		this.actor = actor;

	}

	@Override
	public void clicked(InputEvent event, float x, float y) {
		// System.out.println(actor.cell + " has been clicked.");

		final TiledMapStage stage = (TiledMapStage) actor.getStage();
		final Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));

		if (stage.getMultiActionInput() == false) {
			PopUpWindow popUp = new PopUpWindow("moves", skin, actor, stage);
			popUp.setPosition(stage.getWidth() / 2 - popUp.getWidth() / 2, stage.getHeight() / 2 - popUp.getHeight()
					/ 2);
			popUp.setWidth(250);
			popUp.setHeight(450);
			stage.addActor(popUp);
		}

		if (stage.getMultiActionInput() == true) {
			TiledMapActor previousActor = stage.getPreviousActor();
			System.out.println("moving tile from " + previousActor.getXCell() + " " + previousActor.getYCell() + " to "
					+ actor.getXCell() + " " + actor.getYCell());
			stage.setPreviousActor(null);
			stage.setMultiActionInput(false);

		}
	}

}
