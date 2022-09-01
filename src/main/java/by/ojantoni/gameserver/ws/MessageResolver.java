package by.ojantoni.gameserver.ws;

import org.springframework.web.socket.WebSocketSession;
import web.messages.SimpleMessage;
import web.messages.types.MessageTypes;

public interface MessageResolver {
    void resolve(WebSocketSession session, SimpleMessage message);
    MessageTypes getHandledMessageType();
}
