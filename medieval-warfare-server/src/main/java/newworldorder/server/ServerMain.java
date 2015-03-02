package newworldorder.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import newworldorder.common.network.MessageConsumer;
import newworldorder.common.network.MessageHandler;
import newworldorder.common.network.factory.ActorFactory;
import newworldorder.common.network.message.CommandExecutor;
import newworldorder.common.network.message.CommandHandler;

public class ServerMain {

	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ServerConfig.class);
		CommandExecutor executor = context.getBean(RequestDispatcher.class);
		context.close();

		try {
			MessageHandler requestHandler = new CommandHandler(executor);
			MessageConsumer consumer = ActorFactory.createDirectConsumer("localhost", "requestQueue", requestHandler);
			System.out.println("Consumer connected to requestQueue. Waiting for messages...");
			consumer.startConsuming();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
