package newworldorder.common.network.factory;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import newworldorder.common.network.Command;
import newworldorder.common.network.IRoutingProducer;
import newworldorder.common.network.util.Serialization;

class RoutingProducer implements IRoutingProducer {
	private Channel channel;
	private String exchangeName;

	RoutingProducer(Channel channel, String exchangeName) {
		this.channel = channel;
		this.exchangeName = exchangeName;
	}

	@Override
	public void sendMessage(byte[] message, String routingKey) throws IOException {
		channel.basicPublish(exchangeName, routingKey, null, message);
	}

	@Override
	public void sendCommand(Command command, String routingKey) throws IOException {
		this.sendMessage(Serialization.command2bytes(command), routingKey);
	}

	@Override
	public void releaseConnection() throws IOException {
		Connection conn = channel.getConnection();
		channel.close();
		conn.close();
	}
}
