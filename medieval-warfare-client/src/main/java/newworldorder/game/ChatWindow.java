package newworldorder.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Array;

public class ChatWindow extends Window {
	private ScrollPane scrollPane;
	private List<Label> messageList;
	private TextField textField;

	public ChatWindow(String title, Skin skin) {
		super(title, skin);
		this.setWidth(250);
		this.setHeight(500);
		Table table = new Table();
		this.add(table);
		table.setFillParent(true);
		messageList = new List<>(skin);
		messageList.setFillParent(true);
		scrollPane = new ScrollPane(messageList, skin);
		textField = new TextField("Enter chat message", skin);
		textField.addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keyCode) {
				if (keyCode == Input.Keys.ENTER) {
					Array<Label> labels = messageList.getItems();
					labels.add(new Label(textField.getMessageText(), skin));
					messageList.setItems(labels);
				}
				return true;

			}
		});
		table.add(scrollPane).width(250);
		table.row();
		table.add(textField).width(250);
	}
}
