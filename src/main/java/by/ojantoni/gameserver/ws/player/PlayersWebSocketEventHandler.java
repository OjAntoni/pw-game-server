package by.ojantoni.gameserver.ws.player;

import by.ojantoni.gameserver.ws.ConnectionClosedHandler;
import by.ojantoni.gameserver.ws.ConnectionEstablishedHandler;
import by.ojantoni.gameserver.ws.TextMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;

@Component
public class PlayersWebSocketEventHandler extends TextWebSocketHandler implements WebSocketHandler {
    @Autowired
    @PlayersHandlers
    List<ConnectionEstablishedHandler> connectionEstablishedHandlers;
    @Autowired
    @PlayersHandlers
    List<TextMessageHandler> textMessageHandlers;
    @Autowired
    @PlayersHandlers
    List<ConnectionClosedHandler> connectionClosedHandlers;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        connectionEstablishedHandlers.forEach(h -> h.handle(session));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        textMessageHandlers.forEach(h -> h.handle(session, message));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        connectionClosedHandlers.forEach(h -> h.handle(session, closeStatus));
    }
}
