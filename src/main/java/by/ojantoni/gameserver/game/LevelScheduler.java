package by.ojantoni.gameserver.game;

import by.ojantoni.gameserver.actors.registry.ActorsRegistry;
import by.ojantoni.gameserver.util.Properties;
import com.badlogic.gdx.utils.Timer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class LevelScheduler implements GameSessionListener {
    private Timer.Task scheduleForLevels;

    @Autowired(required = false)
    private List<LevelListener> levelListeners;
    @Getter
    private int level;

    @Override
    public void onGameSessionCreate() {
        level = 0;
        scheduleForLevels = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                level++;
                levelListeners.forEach(listener -> listener.onLevelChange(level));
                level = (level>GameLevel.LEVELS_NUMBER) ? 1: level;
                log.info("Setting level "+level);
            }
        }, 0f, 10);
    }

    @Override
    public void onGameSessionEnd() {
        if(scheduleForLevels!=null){
            scheduleForLevels.cancel();
        }
    }

}
