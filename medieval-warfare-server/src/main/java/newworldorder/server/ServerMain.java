package newworldorder.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import newworldorder.common.network.MessageConsumer;
import newworldorder.common.network.MessageHandler;
import newworldorder.common.network.factory.ActorFactory;
import newworldorder.common.network.message.CommandExecutor;
import newworldorder.common.network.message.CommandHandler;

@Configuration
@ComponentScan
public class ServerMain {

	public static void main(String[] args) throws Exception {
		ApplicationContext context = new AnnotationConfigApplicationContext(ServerMain.class);
		CommandExecutor executor = context.getBean(RequestDispatcher.class);
		try {
			MessageHandler requestHandler = new CommandHandler(executor);
			MessageConsumer consumer = ActorFactory.createDirectConsumer("localhost", "requestQueue", requestHandler);
			System.out.println("Consumer connected to requestQueue. Waiting for messages...");
			consumer.consumeMessages();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
