package newworldorder.common.network.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import newworldorder.common.network.Command;

public class Serialization {
	public static byte[] command2bytes(Command command) throws IOException {
		ByteArrayOutputStream bstream = new ByteArrayOutputStream();
		ObjectOutputStream ostream = new ObjectOutputStream(bstream);
		ostream.writeObject(command);
		byte[] bytes = bstream.toByteArray();
		ostream.close();
		return bytes;
	}

	public static Command bytes2command(byte[] bytes) throws IOException, ClassNotFoundException {
		ObjectInputStream ostream = new ObjectInputStream(new ByteArrayInputStream(bytes));
		Command command = (Command) ostream.readObject();
		ostream.close();
		return command;
	}
}
