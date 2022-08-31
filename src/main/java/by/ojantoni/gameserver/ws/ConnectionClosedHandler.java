package by.ojantoni.gameserver.ws;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

public interface ConnectionClosedHandler {
    void handle(WebSocketSession session, CloseStatus status);
}
