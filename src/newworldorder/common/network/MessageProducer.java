package newworldorder.common.network;

import java.io.IOException;

public interface MessageProducer {

	public void sendMessage(String message) throws IOException;

}
