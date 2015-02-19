package ca.mcgill.cs.comp361.nwo.mwgs.model;

import java.util.Date;

/**
 * Message class clean and complete.
 */
public class Message {
    
    private final String message;
    private final Date timestamp;
    private final Player sender;
    
    public Message(String pMessage, Player pSender) {
        message = pMessage;
        sender = pSender;
        timestamp = Clock.getNow();
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
