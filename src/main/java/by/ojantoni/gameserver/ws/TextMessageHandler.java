package by.ojantoni.gameserver.ws;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public interface TextMessageHandler {
    void handle(WebSocketSession session, TextMessage message);
}
