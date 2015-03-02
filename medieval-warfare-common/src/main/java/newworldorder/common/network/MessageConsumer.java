package newworldorder.common.network;

import java.io.IOException;

public interface MessageConsumer {

	public void startConsuming() throws IOException;

	public void stopConsuming() throws IOException;

	public void releaseConnection() throws IOException;

	public String getQueue();

}
