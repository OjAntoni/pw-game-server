package by.ojantoni.gameserver.ws.resolver;

import by.ojantoni.gameserver.actors.registry.PlayersRegistry;
import by.ojantoni.gameserver.messages.SimpleMessage;
import by.ojantoni.gameserver.messages.dto.PlayerPositionDto;
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
public class DeletePlayerMessageResolver implements MessageResolver {
    @Autowired
    private SessionRegistry sessionRegistry;
    @Autowired
    private PlayersRegistry playersRegistry;
    @Autowired
    ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public void resolve(WebSocketSession session, SimpleMessage message) {
        PlayerPositionDto dto = objectMapper.readValue(message.payload, PlayerPositionDto.class);
        playersRegistry.remove(dto.playerId);
        for (WebSocketSession s : sessionRegistry.getAll()) {
            s.sendMessage(new TextMessage(objectMapper.writeValueAsString(List.of(new SimpleMessage(MessageType.DELETE_PLAYER, message.payload)))));
        }
    }

    @Override
    public MessageType getHandledMessageType() {
        return MessageType.DELETE_PLAYER;
    }
}
