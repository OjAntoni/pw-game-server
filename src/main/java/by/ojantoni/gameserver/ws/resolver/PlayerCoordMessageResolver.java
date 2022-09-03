package by.ojantoni.gameserver.ws.resolver;

import by.ojantoni.gameserver.actors.player.Player;
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

import java.util.ArrayList;
import java.util.List;

@Component
public class PlayerCoordMessageResolver implements MessageResolver {
    @Autowired
    private PlayersRegistry playersRegistry;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    @SneakyThrows
    public void resolve(WebSocketSession session, SimpleMessage message) {
        PlayerPositionDto dto = objectMapper.readValue(message.payload, PlayerPositionDto.class);
        playersRegistry.get(dto.playerId).setCoordinates(dto.coordinates);

        List<SimpleMessage> toSend = new ArrayList<>();
        for (Player player : playersRegistry.getAll()) {
            PlayerPositionDto positionDto = new PlayerPositionDto(player.getCoordinates(), player.getId());
            toSend.add(new SimpleMessage(MessageType.PLAYER_COORD, objectMapper.writeValueAsString(positionDto)));
        }
        List<WebSocketSession> sessions = sessionRegistry.getAll();
            for (WebSocketSession s : sessions) {
                synchronized (s){
                    s.sendMessage(new TextMessage(objectMapper.writeValueAsString(toSend)));
                }
            }
    }

    @Override
    public MessageType getHandledMessageType() {
        return MessageType.PLAYER_COORD;
    }
}
