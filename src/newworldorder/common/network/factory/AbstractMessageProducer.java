package newworldorder.common.network.factory;

import newworldorder.common.network.MessageProducer;
import newworldorder.common.network.message.RemoteCommand;
import newworldorder.common.network.util.Serialization;

public abstract class AbstractMessageProducer implements MessageProducer {

	@Override
	public abstract void sendMessage(byte[] message) throws Exception;

	@Override
	public void sendCommand(RemoteCommand command) throws Exception {
		this.sendMessage(Serialization.command2bytes(command));
	}
}