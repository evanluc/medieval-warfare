package newworldorder.common.network;

import newworldorder.common.network.message.RemoteCommand;

public interface IRoutingProducer {
	
	public void sendMessage(byte[] message, String routingKey) throws Exception;
	
	public void sendCommand(RemoteCommand command, String routingKey) throws Exception;
	
}
