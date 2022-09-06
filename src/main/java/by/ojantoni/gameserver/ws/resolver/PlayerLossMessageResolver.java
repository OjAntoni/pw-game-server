package by.ojantoni.gameserver.ws.resolver;

import by.ojantoni.gameserver.actors.registry.PlayersRegistry;
import by.ojantoni.gameserver.messages.SimpleMessage;
import by.ojantoni.gameserver.messages.dto.PlayerPositionDto;
import by.ojantoni.gameserver.messages.types.MessageType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
@Slf4j
public class PlayerLossMessageResolver implements MessageResolver {

    @Autowired
    private PlayersRegistry playersRegistry;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public void resolve(WebSocketSession session, SimpleMessage message) {
        PlayerPositionDto dto = objectMapper.readValue(message.payload, PlayerPositionDto.class);
        playersRegistry.remove(dto.playerId);
        log.info("Removed player " + dto.playerId + "from game session due to loss");
    }

    @Override
    public MessageType getHandledMessageType() {
        return MessageType.PLAYER_LOSS;
    }
}
