package by.ojantoni.gameserver.game;

import by.ojantoni.gameserver.actors.registry.ActorsRegistry;
import by.ojantoni.gameserver.messages.SimpleMessage;
import by.ojantoni.gameserver.messages.core.ActorState;
import by.ojantoni.gameserver.messages.dto.ObjectDto;
import by.ojantoni.gameserver.messages.types.MessageType;
import by.ojantoni.gameserver.objects.AbstractObject;
import by.ojantoni.gameserver.objects.CleanCode;
import by.ojantoni.gameserver.objects.ObjectRule;
import by.ojantoni.gameserver.objects.ObjectsRegistry;
import by.ojantoni.gameserver.ws.SessionRegistry;
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
public class GameManagementService{
    @Autowired
    private ActorsRegistry actorsRegistry;
    @Autowired
    private SessionRegistry sessionRegistry;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ObjectsRegistry objectsRegistry;

    @Autowired
    private List<GameSessionListener> gameSessionListeners;
    @Autowired
    private List<ObjectRule> objectRules;

    @SneakyThrows
    public void sendActorStatesToPlayers() {
        List<ActorState> states = new ArrayList<>();
        actorsRegistry.getAll().forEach(a -> states.add(new ActorState(a.getId(), a.getState())));
        if(states.isEmpty()){
            return;
        }
        List<SimpleMessage> toSend = states.stream()
                .map(s -> new SimpleMessage(MessageType.ACTOR_STATE, toJson(s)))
                .collect(Collectors.toList());
        if(!toSend.isEmpty() && !sessionRegistry.getAll().isEmpty())
            log.info("Sending current actors states: " + toSend);
        sessionRegistry.sendToAll(new TextMessage(toJson(toSend)));
    }

    public void updateActors() {
        actorsRegistry.deleteDeadActors();
        actorsRegistry.updatePositions();
    }

    public void startGameSession() {
        log.info("Starting game session");
        gameSessionListeners.forEach(GameSessionListener::onGameSessionCreate);
    }

    public void endGameSession() {
        log.info("Ending game session");
        gameSessionListeners.forEach(GameSessionListener::onGameSessionEnd);
    }

    @SneakyThrows
    private String toJson(Object o) {
        return objectMapper.writeValueAsString(o);
    }

    public void updateObjects() {
        objectsRegistry.updateState();
        objectRules.forEach(ObjectRule::process);
        sendDeletedObjectsToPlayers();
        objectsRegistry.deleteOutdated();
        sendObjectsStatesToPlayers();
    }

    private void sendObjectsStatesToPlayers() {
        List<SimpleMessage> collect = objectsRegistry.getAll(CleanCode.class).stream()
                .map(b -> new ObjectDto(b.getId(), b.getName(), b.getCoordinates()))
                .map(dto -> new SimpleMessage(MessageType.COORD_CLEAN_CODE, toJson(dto)))
                .collect(Collectors.toList());
        sessionRegistry.sendToAll(new TextMessage(toJson(collect)));
    }

    private void sendDeletedObjectsToPlayers() {
        List<CleanCode> all = objectsRegistry.getAll(CleanCode.class);
        List<SimpleMessage> collect = all.stream()
                .filter(AbstractObject::isOutdated)
                .map(b -> new ObjectDto(b.getId(), b.getName(), b.getCoordinates()))
                .map(dto -> new SimpleMessage(MessageType.DELETE_CLEAN_CODE, toJson(dto)))
                .collect(Collectors.toList());
        sessionRegistry.sendToAll(new TextMessage(toJson(collect)));
    }

    public void sendObjectStatesToPlayers() {

    }
}
