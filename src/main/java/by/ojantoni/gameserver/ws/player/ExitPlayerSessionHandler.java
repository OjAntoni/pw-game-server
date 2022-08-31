package by.ojantoni.gameserver.ws.player;

import by.ojantoni.gameserver.ws.ConnectionClosedHandler;
import by.ojantoni.gameserver.ws.SessionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

@Component
@PlayersHandlers
public class ExitPlayerSessionHandler implements ConnectionClosedHandler {
    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    public void handle(WebSocketSession session, CloseStatus status) {
        sessionRegistry.remove(session);
    }
}
