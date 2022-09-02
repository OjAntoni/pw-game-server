package by.ojantoni.gameserver.ws.resolver;

import by.ojantoni.gameserver.messages.messages.SimpleMessage;
import by.ojantoni.gameserver.messages.messages.types.MessageType;
import org.springframework.web.socket.WebSocketSession;

public interface MessageResolver {
    void resolve(WebSocketSession session, SimpleMessage message);
    MessageType getHandledMessageType();
}
