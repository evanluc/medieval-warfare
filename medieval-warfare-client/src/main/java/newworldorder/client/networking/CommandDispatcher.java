package newworldorder.client.networking;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

import newworldorder.common.network.command.CheckPartyCommand;

public class CommandDispatcher extends MessageListenerAdapter {
	public CommandDispatcher(Object handler, String method) {
		super(handler, method);
	}
	
	@Override
	protected String getListenerMethodName(Message originalMessage, Object extractedMessage) {
		if (extractedMessage instanceof CheckPartyCommand) {
			return "handleAndReply";
		}
		else {
			return getDefaultListenerMethod();
		}
		
	}
}
