package newworldorder.client.networking;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

import newworldorder.common.network.command.CheckPartyCommand;
import newworldorder.common.network.command.ProbeCommand;

public class CommandDispatcher extends MessageListenerAdapter {
	public CommandDispatcher(Object handler, String method) {
		super(handler, method);
	}
	
	@Override
	protected String getListenerMethodName(Message originalMessage, Object extractedMessage) {
		if (extractedMessage instanceof CheckPartyCommand || extractedMessage instanceof ProbeCommand) {
			return "handleAndReply";
		}
		else {
			return getDefaultListenerMethod();
		}
		
	}
}
