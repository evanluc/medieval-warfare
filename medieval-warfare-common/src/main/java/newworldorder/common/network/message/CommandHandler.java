package newworldorder.common.network.message;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import newworldorder.common.network.MessageHandler;

public class CommandHandler implements MessageHandler {
	private CommandExecutor executor;
	
	public CommandHandler(CommandExecutor executor) {
		this.executor = executor;
	}
	
	@Override
	public void handle(byte[] message) throws IOException, ClassNotFoundException {
		ObjectInputStream ostream = new ObjectInputStream(new ByteArrayInputStream(message));
		AbstractCommand command = (AbstractCommand) ostream.readObject();
		executor.execute(command);
	}

}
