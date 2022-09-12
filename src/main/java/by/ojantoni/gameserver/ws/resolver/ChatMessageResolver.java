package by.ojantoni.gameserver.ws.resolver;

import by.ojantoni.gameserver.messages.SimpleMessage;
import by.ojantoni.gameserver.messages.dto.ChatMessage;
import by.ojantoni.gameserver.messages.types.MessageType;
import by.ojantoni.gameserver.ws.SessionRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Component
public class ChatMessageResolver implements MessageResolver {
    @Autowired
    private SessionRegistry sessionRegistry;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public void resolve(WebSocketSession session, SimpleMessage message) {
        sessionRegistry.sendToAll(new TextMessage(objectMapper.writeValueAsString(List.of(message))));
    }

    @Override
    public MessageType getHandledMessageType() {
        return MessageType.CHAT_MESSAGE;
    }
}
