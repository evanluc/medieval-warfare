package newworldorder.common.network;

import java.io.IOException;

import newworldorder.common.network.message.AbstractCommand;

public interface IRoutingProducer {

	public void sendMessage(byte[] message, String routingKey) throws IOException;

	public void sendCommand(AbstractCommand command, String routingKey) throws IOException;

	public void releaseConnection() throws IOException;

}
