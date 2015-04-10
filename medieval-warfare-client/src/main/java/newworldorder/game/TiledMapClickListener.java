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

		System.out.println("clicked on tiled map click listener");

		final TiledMapStage stage = (TiledMapStage) actor.getStage();

		if(stage.getModel().isLocalPlayersTurn()== false){
			if(stage.getCurrentlyOutlined() != null){
				TiledMapActor previouslyOutlined = stage.getCurrentlyOutlined();
				Cell previouslyOutlinedCell = stage.getTiledMapDescriptors().outlineLayer.getCell(previouslyOutlined.getXCell(),previouslyOutlined.getYCell());					
				previouslyOutlinedCell.setTile(stage.getTiledMapDescriptors().nullTile);
			}

			Cell outlineCell = stage.getTiledMapDescriptors().outlineLayer.getCell(actor.getXCell(),actor.getYCell());
			outlineCell.setTile(stage.getTiledMapDescriptors().outlineTile);
			stage.setCurrentlyOutlined(actor);
			//stage.getUIStage().notTurnInfoRenderUpdate(actor);
			//System.out.println("button render updated here");
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
//				stage.getUIStage().yourTurnInfoRenderUpdate(actor);


			}

			//Case for movement. We inform the model of the movement and set the new selected tile to be the moved tile.
			if (stage.getMultiActionInput() == true) {
				TiledMapActor previousActor = stage.getPreviousActor();
				System.out.println("moving tile from " + previousActor.getXCell() + " " + previousActor.getYCell() + " to "
						+ actor.getXCell() + " " + actor.getYCell());
				controller.informOfUserAction(UIActionType.MOVEUNIT, previousActor.getXCell(), previousActor.getYCell(),
						actor.getXCell(), actor.getYCell());
				
				
				//now outlining the cell where we are moving and rerendering the move options for that cell
				if(stage.getCurrentlyOutlined() != null){		
					TiledMapActor previouslyOutlined = stage.getCurrentlyOutlined();
					Cell previouslyOutlinedCell = stage.getTiledMapDescriptors().outlineLayer.getCell(previouslyOutlined.getXCell(),previouslyOutlined.getYCell());					
					previouslyOutlinedCell.setTile(stage.getTiledMapDescriptors().nullTile);
				}	
				Cell outlineCell = stage.getTiledMapDescriptors().outlineLayer.getCell(actor.getXCell(),actor.getYCell());
				outlineCell.setTile(stage.getTiledMapDescriptors().outlineTile);
				stage.setCurrentlyOutlined(actor);
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
