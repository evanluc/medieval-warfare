package newworldorder.game;

import newworldorder.client.model.ModelController;
import newworldorder.client.shared.UIVillageDescriptor;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class VillageClickListener extends ClickListener{
	private TiledMapActor actor;
	private TiledMapStage stage;
	private Skin skin;

	public VillageClickListener(Actor actor, Skin skin){	
		this.stage = (TiledMapStage) actor.getStage();
		this.skin = skin;
		this.setButton(Input.Buttons.RIGHT);
		this.actor = (TiledMapActor) actor;
	}
	@Override
	public void clicked(InputEvent event, float x, float y) {
		UIVillageDescriptor vil = ((TiledMapStage) actor.getStage()).getModel().getVillage(actor.getXCell(),
				actor.getYCell());
		
		if (vil != null) {
			PopUpWindow popUp = new PopUpWindow("Village Info", skin, actor, stage, vil);
			popUp.setWidth(250);
			popUp.setHeight(150);
			popUp.setPosition(stage.getCamera().position.x - popUp.getWidth() / 2, stage.getCamera().position.y - popUp.getHeight()
					/ 2);

			stage.getUIStage().addActor(popUp);

		}


	}
}




