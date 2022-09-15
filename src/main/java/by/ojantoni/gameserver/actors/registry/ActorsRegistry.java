package by.ojantoni.gameserver.actors.registry;

import by.ojantoni.gameserver.actors.AbstractActor;
import by.ojantoni.gameserver.game.GameLevel;
import by.ojantoni.gameserver.game.GameSessionListener;
import by.ojantoni.gameserver.game.LevelListener;
import by.ojantoni.gameserver.messages.core.Coordinates;
import by.ojantoni.gameserver.util.Properties;
import com.badlogic.gdx.utils.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ActorsRegistry implements GameSessionListener, LevelListener {
    @Autowired
    private Map<String, AbstractActor> allActors;

    @PostConstruct
    private void init(){
        allActors.values().forEach(AbstractActor::pause);
    }

    private void updateInGameActors(GameLevel level) {
        List<String> names = level.getActorNamesInGame();

        allActors.values().forEach(a -> {
            if(!names.contains(a.getId())){
                a.pause();
            }
        });

        names.forEach(n -> {
            if(allActors.containsKey(n) && !allActors.get(n).isAlive()){
                allActors.get(n).resume();
            }
        });

    }

    public void updatePositions() {
        allActors.values().stream().filter(AbstractActor::isAlive).forEach(AbstractActor::calculateNewCoordinates);
    }

    public AbstractActor get(String name) {
        return allActors.get(name);
    }

    public List<AbstractActor> getAll() {
        return new ArrayList<>(allActors.values());
    }

    @Override
    public void onGameSessionCreate() {

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
