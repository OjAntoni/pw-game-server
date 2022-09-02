package by.ojantoni.gameserver.ws;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

@Component
public class SessionRegistry {
    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    public void add(WebSocketSession session) {
        if (session != null) {
            sessions.add(session);
        }
    }

    public void remove(WebSocketSession session) {
        if (session != null)
            sessions.remove(session);
    }

    public void remove(String sessionId){
        sessions.removeIf(s -> s.getId().equals(sessionId));
    }

    public List<WebSocketSession> getAll(){
        return new ArrayList<>(sessions);
    }
}
