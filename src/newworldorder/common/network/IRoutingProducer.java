package newworldorder.common.network;

import newworldorder.common.network.message.AbstractCommand;

public interface IRoutingProducer {
	
	public void sendMessage(byte[] message, String routingKey) throws Exception;
	
	public void sendCommand(AbstractCommand command, String routingKey) throws Exception;
	
}
