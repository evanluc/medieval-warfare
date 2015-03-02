package newworldorder.common.network;

import java.io.IOException;

import newworldorder.common.network.message.AbstractCommand;

public interface MessageProducer {

	public void sendMessage(byte[] message) throws Exception;

	public void sendCommand(AbstractCommand command) throws Exception;

	public void releaseConnection() throws IOException;

}
