package by.ojantoni.gameserver.game;

import by.ojantoni.gameserver.actors.registry.ActorsRegistry;
import by.ojantoni.gameserver.messages.SimpleMessage;
import by.ojantoni.gameserver.messages.core.ActorState;
import by.ojantoni.gameserver.messages.types.MessageType;
import by.ojantoni.gameserver.ws.SessionRegistry;
import com.badlogic.gdx.ApplicationAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class GameLoop extends ApplicationAdapter {

    @Autowired
    private GameManagementService gameService;


    @Override
    public void create() {

    }

    @Override
    public void render() {
        gameService.updateActors();
        gameService.sendActorStatesToPlayers();
        gameService.updateObjects();
        gameService.sendObjectStatesToPlayers();
    }

}
