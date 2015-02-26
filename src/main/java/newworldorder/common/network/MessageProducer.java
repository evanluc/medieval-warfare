package newworldorder.common.network;

import newworldorder.common.network.message.AbstractCommand;

public interface MessageProducer {

	public void sendMessage(byte[] message) throws Exception;
	
	public void sendCommand(AbstractCommand command) throws Exception;

}
