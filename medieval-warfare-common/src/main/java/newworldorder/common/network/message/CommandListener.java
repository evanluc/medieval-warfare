package newworldorder.common.network.message;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class CommandListener implements MessageListener {
	private CommandExecutor executor;

	public CommandListener(CommandExecutor executor) {
		this.executor = executor;
	}

	@Override
	public void onMessage(Message message) {
		System.out.println("Received message: " + message.toString());
		byte[] bytes = message.getBody();
		ObjectInputStream ostream;

		try {
			ostream = new ObjectInputStream(new ByteArrayInputStream(bytes));
			AbstractCommand command = (AbstractCommand) ostream.readObject();
			executor.execute(command);
		}
		catch (IOException e) {
			// TODO Not sure how to handle for now..
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			// TODO Probably log this and send message back to sender?
			e.printStackTrace();
		}
	}
}
