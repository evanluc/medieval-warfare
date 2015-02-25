package newworldorder.common.network.factory;

import newworldorder.common.network.IRoutingProducer;
import newworldorder.common.network.message.RemoteCommand;
import newworldorder.common.network.util.Serialization;

import com.rabbitmq.client.Channel;

public class RoutingProducer implements IRoutingProducer  {
	private Channel channel;
	private String exchangeName;

	RoutingProducer(Channel channel, String exchangeName) {
		this.channel = channel;
		this.exchangeName = exchangeName;
	}
	
	public void sendMessage(byte[] message, String routingKey) throws Exception {
		channel.basicPublish(exchangeName, routingKey, null, message);
	}
	
	public void sendCommand(RemoteCommand command, String routingKey) throws Exception {
		this.sendMessage(Serialization.command2bytes(command), routingKey);
	}
}
