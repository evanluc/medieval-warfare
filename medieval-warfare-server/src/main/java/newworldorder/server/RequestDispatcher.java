package newworldorder.server;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

import newworldorder.common.network.command.CreateAccountCommand;
import newworldorder.common.network.command.GetOnlinePlayersCommand;
import newworldorder.common.network.command.GetPlayerStatsCommand;
import newworldorder.common.network.command.LoginCommand;

public class RequestDispatcher extends MessageListenerAdapter {
	public RequestDispatcher(Object handler, String method) {
		super(handler, method);
	}
	
	@Override
	protected String getListenerMethodName(Message originalMessage, Object extractedMessage) {
		if (extractedMessage instanceof LoginCommand || extractedMessage instanceof CreateAccountCommand || extractedMessage instanceof GetOnlinePlayersCommand || extractedMessage instanceof GetPlayerStatsCommand) {
			return "handleAndReply";
		}
		else {
			return getDefaultListenerMethod();
		}
		
	}
}
