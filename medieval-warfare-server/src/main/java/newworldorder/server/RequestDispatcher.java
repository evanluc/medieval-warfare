package newworldorder.server;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

import newworldorder.common.network.command.LoginCommand;

public class RequestDispatcher extends MessageListenerAdapter {
	public RequestDispatcher(Object handler, String method) {
		super(handler, method);
	}
	
	@Override
	protected String getListenerMethodName(Message originalMessage, Object extractedMessage) {
		if (extractedMessage instanceof LoginCommand) {
			return "handleAndReply";
		}
		else {
			return getDefaultListenerMethod();
		}
		
	}
}
