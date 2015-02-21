package newworldorder.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import newworldorder.common.network.Command;
import newworldorder.common.network.MessageHandler;

public class RequestDispatcher implements MessageHandler {
	
	private ExecutorService threadPool = Executors.newCachedThreadPool();
	
	@Override
	public void handle(String message) {
		byte[] bytes = message.getBytes();
		
		try {
			ObjectInputStream ostream = new ObjectInputStream(new ByteArrayInputStream(bytes));
			Command command = (Command) ostream.readObject();
			
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					command.execute();
				}
			});
		}
		catch (IOException e) {
			// TODO
		}
		catch (ClassNotFoundException e) {
			// TODO
		}
	}

}
