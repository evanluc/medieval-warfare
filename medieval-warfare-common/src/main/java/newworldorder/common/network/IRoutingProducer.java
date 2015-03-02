package newworldorder.common.network;

import java.io.IOException;

public interface IRoutingProducer {

	public void sendMessage(byte[] message, String routingKey) throws IOException;

	public void sendCommand(Command command, String routingKey) throws IOException;

	public void releaseConnection() throws IOException;

}
