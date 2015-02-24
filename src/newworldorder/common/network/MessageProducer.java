package newworldorder.common.network;

public interface MessageProducer {

	public void sendMessage(byte[] message) throws Exception;

}
