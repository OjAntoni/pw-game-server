package by.ojantoni.gameserver.ws;

import org.springframework.web.socket.WebSocketSession;

public interface ConnectionEstablishedHandler {
    void handle(WebSocketSession session);
}
