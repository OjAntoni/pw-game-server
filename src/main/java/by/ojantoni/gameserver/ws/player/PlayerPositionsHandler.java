package by.ojantoni.gameserver.ws.player;

import by.ojantoni.gameserver.actors.CoordinatesDto;
import by.ojantoni.gameserver.actors.PlayersRegistry;
import by.ojantoni.gameserver.ws.SessionRegistry;
import by.ojantoni.gameserver.ws.TextMessageHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;

@Component
@PlayersHandlers
@AllArgsConstructor
public class PlayerPositionsHandler implements TextMessageHandler {
    private final PlayersRegistry playersRegistry;
    private final SessionRegistry sessionRegistry;
    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public void handle(WebSocketSession session, TextMessage message) {
        List<CoordinatesDto> playersPositions = new ArrayList<>();
        playersRegistry.getAll().forEach(p -> playersPositions.add(p.getCoordinatesDto()));
        String allCoord = objectMapper.writeValueAsString(playersPositions);
        synchronized (sessionRegistry) {
            for (WebSocketSession s : sessionRegistry.getAll()) {
                s.sendMessage(new TextMessage(allCoord));
            }
        }

    }
}
