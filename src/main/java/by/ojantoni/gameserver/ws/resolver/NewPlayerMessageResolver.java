package by.ojantoni.gameserver.ws.resolver;

import by.ojantoni.gameserver.actors.Player;
import by.ojantoni.gameserver.actors.PlayersRegistry;
import by.ojantoni.gameserver.messages.messages.SimpleMessage;
import by.ojantoni.gameserver.messages.messages.dto.PlayerPositionDto;
import by.ojantoni.gameserver.messages.messages.types.MessageType;
import by.ojantoni.gameserver.ws.SessionRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Component
public class NewPlayerMessageResolver implements MessageResolver {
    @Autowired
    private PlayersRegistry playersRegistry;
    @Autowired
    private SessionRegistry sessionRegistry;
    @Autowired
    private ObjectMapper mapper;

    @Override
    @SneakyThrows
    public void resolve(WebSocketSession session, SimpleMessage message) {
        PlayerPositionDto dto = mapper.readValue(message.payload, PlayerPositionDto.class);
        Player player = new Player(dto.coordinates, dto.playerId);
        playersRegistry.add(player);
        for (WebSocketSession s : sessionRegistry.getAll()) {
            if(!s.equals(session)){
                s.sendMessage(new TextMessage(
                        mapper.writeValueAsString(List.of(new SimpleMessage(MessageType.NEW_PLAYER,
                                mapper.writeValueAsString(new PlayerPositionDto(player.getCoordinates(), player.getId())))))
                ));
            }
        }
    }

    @Override
    public MessageType getHandledMessageType() {
        return MessageType.NEW_PLAYER;
    }
}
