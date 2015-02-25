package newworldorder.common.network;

import newworldorder.common.network.message.RemoteCommand;

public interface MessageProducer {

	public void sendMessage(byte[] message) throws Exception;
	
	public void sendCommand(RemoteCommand command) throws Exception;

}
