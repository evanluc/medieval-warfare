package newworldorder.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import newworldorder.common.network.MessageHandler;
import newworldorder.common.network.message.RemoteCommand;
import newworldorder.common.service.ServiceLocator;

public class RequestDispatcher implements MessageHandler {

	private ExecutorService threadPool = Executors.newCachedThreadPool();
	private ServiceLocator locator = ServiceLocator.getInstance();

	@Override
	public void handle(byte[] message) {
		try {
			ObjectInputStream ostream = new ObjectInputStream(new ByteArrayInputStream(message));
			RemoteCommand command = (RemoteCommand) ostream.readObject();
			command.setServiceLocator(locator);

			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					command.execute();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
