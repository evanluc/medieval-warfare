package newworldorder.common.network;

import java.io.IOException;

public interface MessageProducer {

	public void sendMessage(byte[] message) throws Exception;

	public void sendCommand(Command command) throws Exception;

	public void releaseConnection() throws IOException;

}
