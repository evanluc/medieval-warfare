package newworldorder.common.network.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import newworldorder.common.network.message.RemoteCommand;

public class Serialization {
	public static byte[] command2bytes(RemoteCommand command) throws IOException {
		ByteArrayOutputStream bstream = new ByteArrayOutputStream();
		ObjectOutputStream ostream = new ObjectOutputStream(bstream);
		ostream.writeObject(command);
		return bstream.toByteArray();
	}
	
	public static RemoteCommand bytes2Command(byte[] bytes) throws IOException, ClassNotFoundException {
		ObjectInputStream ostream = new ObjectInputStream(new ByteArrayInputStream(bytes));
		RemoteCommand command = (RemoteCommand) ostream.readObject();
		return command;
	}
}
