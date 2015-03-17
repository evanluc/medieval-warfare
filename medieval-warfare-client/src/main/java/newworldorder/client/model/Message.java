package newworldorder.client.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Message class clean and complete.
 */
class Message implements Serializable {
    
	private static final long serialVersionUID = -4444110926298066700L;
	private final String message;
    private final Date timestamp;
    private final Player sender;
    
    public Message(String pMessage, Player pSender) {
        message = pMessage;
        sender = pSender;
        timestamp = new Date();
    }

    public String getMessage() {
        return message;
    }

    public Player getSender() {
        return sender;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
