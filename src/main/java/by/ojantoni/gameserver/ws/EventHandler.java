package by.ojantoni.gameserver.ws;

import com.badlogic.gdx.utils.Array;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;

@Component
public class EventHandler extends TextWebSocketHandler implements WebSocketHandler {
    Array<WebSocketSession> sessions = new Array<>();
    @Autowired(required = false)
    List<ConnectionEstablishedHandler> connectionEstablishedHandlers;
    @Autowired(required = false)
    List<TextMessageHandler> textMessageHandlers;
    @Autowired(required = false)
    List<ConnectionClosedHandler> connectionClosedHandlers;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        sessions.add(session);
        System.out.println("Socket Connected: " + session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Received " + message.toString());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("Socket Closed: [" + closeStatus.getCode() + "] " + closeStatus.getReason());
        sessions.removeValue(session, true);
        super.afterConnectionClosed(session, closeStatus);
    }
}
