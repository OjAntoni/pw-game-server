package by.ojantoni.gameserver.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class WebSocketEventHandler extends TextWebSocketHandler implements WebSocketHandler {
    @Autowired
    MessageHandler messageHandler;
    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Connection established with session " + session);
        sessionRegistry.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        //log.info("Get message from session: "+session);
        messageHandler.resolveMessages(session, message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        log.info("Connection closed with session " + session);
        sessionRegistry.remove(session);
    }
}
