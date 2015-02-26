package newworldorder.server;

import newworldorder.common.network.MessageConsumer;
import newworldorder.common.network.MessageHandler;
import newworldorder.common.network.factory.ActorFactory;
import newworldorder.common.network.message.CommandExecutor;
import newworldorder.common.network.message.CommandHandler;

public class ServerMain {

	public static void main(String[] args) throws Exception {
		CommandExecutor executor = new RequestDispatcher();
		MessageHandler requestHandler = new CommandHandler(executor);
		MessageConsumer consumer = ActorFactory.createDirectConsumer("localhost", "requestQueue", requestHandler);
		System.out.println("Consumer connected to requestQueue. Waiting for messages...");
		consumer.consumeMessages();
	}

}
