package by.ojantoni.gameserver;

import com.badlogic.gdx.utils.Array;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class EventHandler extends TextWebSocketHandler implements WebSocketHandler {
    Array<WebSocketSession> sessions = new Array<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        sessions.add(session);
        System.out.println("Socket Connected: " + session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //session.sendMessage(new TextMessage("{ \"history\": [ \"ololo\", \"2\" ] }"));
        System.out.println("Received " + message.toString());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("Socket Closed: [" + closeStatus.getCode() + "] " + closeStatus.getReason());
        sessions.removeValue(session, true);
        super.afterConnectionClosed(session, closeStatus);
    }
}
