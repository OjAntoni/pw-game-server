package by.ojantoni.gameserver.ws.resolver;

import by.ojantoni.gameserver.messages.SimpleMessage;
import by.ojantoni.gameserver.messages.dto.ObjectDto;
import by.ojantoni.gameserver.messages.types.MessageType;
import by.ojantoni.gameserver.objects.CleanCode;
import by.ojantoni.gameserver.objects.ObjectsRegistry;
import by.ojantoni.gameserver.ws.SessionRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Component
public class NewCleanCodeMessageResolver implements MessageResolver {
    @Autowired
    private ObjectsRegistry objectsRegistry;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    @SneakyThrows
    public void resolve(WebSocketSession session, SimpleMessage message) {
        ObjectDto dto = objectMapper.readValue(message.payload, ObjectDto.class);
        objectsRegistry.add(new CleanCode(dto.getId(), dto.getCoordinates().x, dto.getCoordinates().y));
        sessionRegistry.sendToAll(new TextMessage(objectMapper.writeValueAsString(List.of(message))));
    }

    @Override
    public MessageType getHandledMessageType() {
        return MessageType.NEW_CLEAN_CODE;
    }
}
