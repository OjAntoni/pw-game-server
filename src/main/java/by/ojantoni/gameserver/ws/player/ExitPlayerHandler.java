package by.ojantoni.gameserver.ws.player;

import by.ojantoni.gameserver.actors.PlayersRegistry;
import by.ojantoni.gameserver.ws.ConnectionClosedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

@Component
@PlayersHandlers
public class ExitPlayerHandler implements ConnectionClosedHandler {
    @Autowired
    private PlayersRegistry playersRegistry;

    @Override
    public void handle(WebSocketSession session, CloseStatus status) {
        playersRegistry.remove(session.getId());
    }
}
