package by.ojantoni.gameserver.ws.player;

import by.ojantoni.gameserver.actors.CoordinatesDto;
import by.ojantoni.gameserver.actors.Player;
import by.ojantoni.gameserver.actors.PlayersRegistry;
import by.ojantoni.gameserver.ws.TextMessageHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
@PlayersHandlers
public class CreatingNewPlayerHandler implements TextMessageHandler {
    @Autowired
    private PlayersRegistry playersRegistry;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public void handle(WebSocketSession session, TextMessage message) {
        CoordinatesDto coord = objectMapper.readValue(message.getPayload(), CoordinatesDto.class);
        if (playersRegistry.get(session.getId()) == null) {
            playersRegistry.add(new Player(coord, session.getId()));
        } else {
            Player player = playersRegistry.get(session.getId());
            player.setCoordinates(coord);
        }
    }
}
