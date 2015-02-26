package newworldorder.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import newworldorder.common.network.message.AbstractCommand;
import newworldorder.common.network.message.CommandExecutor;
import newworldorder.common.network.message.JoinGameCommand;
import newworldorder.common.network.message.RemoteCommand;
import newworldorder.server.service.ServiceLocator;

@Component
public class RequestDispatcher implements CommandExecutor {

	private ExecutorService threadPool = Executors.newCachedThreadPool();
	private ServiceLocator locator;
	
	@Autowired
	public RequestDispatcher(ServiceLocator locator) {
		this.locator = locator;
	}

	@Override
	public void execute(AbstractCommand command) {
		if (command instanceof RemoteCommand) {
			RemoteCommand remoteCommand = (RemoteCommand) command;
			remoteCommand.setServiceLocator(locator);
			
			if (remoteCommand instanceof JoinGameCommand) {
				System.out.println(remoteCommand.getSender() + " wants to play");
			}
			
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					remoteCommand.execute();
				}
			});
		}
	}
}
