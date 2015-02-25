package newworldorder.common.network;

import java.io.IOException;

public interface MessageHandler {

	public void handle(byte[] message) throws IOException, ClassNotFoundException;

}
