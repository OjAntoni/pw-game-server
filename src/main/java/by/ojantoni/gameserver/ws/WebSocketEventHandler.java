package by.ojantoni.gameserver.ws;

import by.ojantoni.gameserver.ws.player.PlayersHandlers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;

@Component
public class WebSocketEventHandler extends TextWebSocketHandler implements WebSocketHandler {
    @Autowired
    @PlayersHandlers
    List<ConnectionEstablishedHandler> connectionEstablishedHandlers;
    @Autowired
    @PlayersHandlers
    List<TextMessageHandler> textMessageHandlers;
    @Autowired
    @PlayersHandlers
    List<ConnectionClosedHandler> connectionClosedHandlers;

    @Autowired
    MessageHandler messageHandler;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        connectionEstablishedHandlers.forEach(h -> h.handle(session));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        messageHandler.resolveMessages(session, message.getPayload());
        //textMessageHandlers.forEach(h -> h.handle(session, message));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        connectionClosedHandlers.forEach(h -> h.handle(session, closeStatus));
    }
}
