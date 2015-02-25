package newworldorder.common.network.factory;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import newworldorder.common.network.MessageProducer;
import newworldorder.common.network.message.RemoteCommand;

public abstract class AbstractMessageProducer implements MessageProducer {

	@Override
	public abstract void sendMessage(byte[] message) throws Exception;

	@Override
	public void sendCommand(RemoteCommand command) throws Exception {
		ByteArrayOutputStream bstream = new ByteArrayOutputStream();
		ObjectOutputStream ostream = new ObjectOutputStream(bstream);
		ostream.writeObject(command);
		this.sendMessage(bstream.toByteArray());
	}
}