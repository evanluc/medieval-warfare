package newworldorder.server;

import newworldorder.common.network.ActorFactory;
import newworldorder.common.network.MessageConsumer;
import newworldorder.common.network.MessageHandler;

public class ServerMain {

	public static void main(String[] args) throws Exception {
		MessageHandler dispatcher = new RequestDispatcher();
		MessageConsumer consumer = ActorFactory.createDirectConsumer("localhost", "requestQueue", dispatcher);
		consumer.consumeMessages();
	}

}
