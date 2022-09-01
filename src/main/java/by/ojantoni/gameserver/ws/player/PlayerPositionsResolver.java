package by.ojantoni.gameserver.ws.player;

import by.ojantoni.gameserver.actors.CoordinatesDto;
import by.ojantoni.gameserver.actors.PlayersRegistry;
import by.ojantoni.gameserver.ws.MessageResolver;
import by.ojantoni.gameserver.ws.SessionRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import web.messages.SimpleMessage;
import web.messages.types.MessageTypes;

import java.util.ArrayList;
import java.util.List;

@Component
@PlayersHandlers
@AllArgsConstructor
public class PlayerPositionsResolver implements MessageResolver {
    private final PlayersRegistry playersRegistry;
    private final SessionRegistry sessionRegistry;
    private final ObjectMapper objectMapper;

        @Override
        @SneakyThrows
        public void resolve(WebSocketSession session, SimpleMessage message) {
            List<CoordinatesDto> playersPositions = new ArrayList<>();
            playersRegistry.getAll().forEach(p -> playersPositions.add(p.getCoordinatesDto()));
            String allCoord = objectMapper.writeValueAsString(playersPositions);
            synchronized (sessionRegistry) {
                for (WebSocketSession s : sessionRegistry.getAll()) {
                    s.sendMessage(new TextMessage(allCoord));
                }
            }
        }

        @Override
        public MessageTypes getHandledMessageType() {
            return MessageTypes.PLAYER_COORD;
        }

}
