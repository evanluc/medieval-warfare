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
	public void handle(byte[] message) {
		ObjectInputStream ostream;

		try {
			ostream = new ObjectInputStream(new ByteArrayInputStream(message));
			AbstractCommand command = (AbstractCommand) ostream.readObject();
			executor.execute(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block

			// Not sure how to handle for now..

			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block

			// Probably log this and send message back to client?

			e.printStackTrace();
		}
	}
}
