package newworldorder.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import newworldorder.common.network.message.CommandExecutor;
import newworldorder.common.network.message.RemoteCommand;
import newworldorder.common.service.ServiceLocator;

public class RequestDispatcher implements CommandExecutor {

	private ExecutorService threadPool = Executors.newCachedThreadPool();
	private ServiceLocator locator = ServiceLocator.getInstance();

	@Override
	public void execute(RemoteCommand command) {
		command.setServiceLocator(locator);
		
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				command.execute();
			}
		});
	}
}
