package by.ojantoni.gameserver.actors.registry;

import by.ojantoni.gameserver.actors.AbstractActor;
import by.ojantoni.gameserver.game.GameLevel;
import by.ojantoni.gameserver.game.GameSessionListener;
import by.ojantoni.gameserver.game.LevelListener;
import by.ojantoni.gameserver.messages.core.Coordinates;
import by.ojantoni.gameserver.util.Properties;
import com.badlogic.gdx.utils.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ActorsRegistry implements GameSessionListener, LevelListener {
    @Autowired
    private Map<String, AbstractActor> allActors;
    private HashMap<String, AbstractActor> currentInGameActors = new HashMap<>();

    @PostConstruct
    private void init(){
        allActors.values().forEach(AbstractActor::pause);
    }

    public void deleteDeadActors() {
        List<String> actorsToDelete = new ArrayList<>();
        currentInGameActors.forEach((name, a) -> {
            if (a.shouldBeDeletedFromGame()) {
                actorsToDelete.add(name);
            }
        });
        actorsToDelete.forEach(name -> {
            currentInGameActors.get(name).pause();
            currentInGameActors.remove(name);
        });
    }

    public void updateInGameActors(GameLevel level) {
        currentInGameActors.values().forEach(AbstractActor::pause);
        currentInGameActors = new HashMap<>();
        for (String s : level.getActorNamesInGame()) {
            if (allActors.get(s) != null) {
                currentInGameActors.put(s, allActors.get(s));
                allActors.get(s).resume();
            }
        }
    }

    public void updatePositions() {
        currentInGameActors.values().forEach(AbstractActor::calculateNewCoordinates);
    }

    public AbstractActor getCurrent(String name) {
        return currentInGameActors.get(name);
    }

    public AbstractActor get(String name) {
        return allActors.get(name);
    }

    public List<AbstractActor> getAll() {
        return new ArrayList<>(currentInGameActors.values());
    }

    @Override
    public void onGameSessionCreate() {
        updateInGameActors(GameLevel.ONE);
    }

    @Override
    public void onGameSessionEnd() {
        allActors.values().forEach(AbstractActor::pause);

    }

    @Override
    public void onLevelChange(int level) {
        updateInGameActors(GameLevel.getLevel(level));
    }
}
