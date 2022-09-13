package by.ojantoni.gameserver.actors.jstar;

import by.ojantoni.gameserver.actors.AbstractActor;
import by.ojantoni.gameserver.actors.player.Player;
import by.ojantoni.gameserver.actors.registry.PlayersRegistry;
import by.ojantoni.gameserver.game.GameSessionListener;
import by.ojantoni.gameserver.messages.core.Coordinates;
import by.ojantoni.gameserver.util.Properties;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component(value = "jstar")
public class Jstar extends AbstractActor implements GameSessionListener {
    @Autowired
    private PlayersRegistry playersRegistry;
    private Timer.Task jstarschedule;
    private Player playerToSneak;
    private int leftTimeToBeStopped;
    private boolean normalJstar;
    private boolean stoppedJstar;
    @Setter
    private double pace;

    @PostConstruct
    private void init() {
        rectangle = new Rectangle();
        rectangle.x = MathUtils.random(0, Properties.SCREEN_WIDTH - rectangle.width);
        rectangle.y = 20;
        normalJstar = true;
        stoppedJstar = false;
        pace = 0.01;
    }

    @Override
    public void stop(int secondsDelay) {
        leftTimeToBeStopped = secondsDelay;
        stoppedJstar = true;
        normalJstar = false;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                stoppedJstar = false;
                normalJstar = true;
            }
        }, secondsDelay, 0, 1);
    }

    @Override
    public void resume() {
        jstarschedule = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                timeAlive++;
                if (leftTimeToBeStopped > 0) {
                    leftTimeToBeStopped--;
                }
            }
        }, 0f, 1f);
    }

    @Override
    public boolean canBeMoved() {
        return leftTimeToBeStopped == 0;
    }

    @Override
    public void calculateNewCoordinates() {
        setNearestPlayer();
        if (canBeMoved() && playerToSneak!=null) {
            Coordinates coord = playerToSneak.getCoordinates();
            rectangle.y = (float) (rectangle.y + ((coord.y - rectangle.y) / Math.abs(coord.y - rectangle.y)) * pace / 10);
            rectangle.x = (float) (rectangle.x + ((coord.x - rectangle.x) / Math.abs(coord.x - rectangle.x)) * pace / 10);
        }
        incrementPace();
    }

    private void setNearestPlayer() {
        double minDistance = Double.MAX_VALUE;
        Player nearestPlayer = null;
        List<Player> all = playersRegistry.getAll();
        for (Player player : all) {
            Coordinates coordinates = player.getCoordinates();
            float dst = Vector2.dst(rectangle.x, rectangle.y, coordinates.x, coordinates.y);
            if (dst < minDistance) {
                minDistance = dst;
                nearestPlayer = player;
            }
        }
        playerToSneak = nearestPlayer;
    }

    private void incrementPace() {
        pace += 0.006;
    }

    @Override
    public void pause() {
        if (jstarschedule != null) {
            jstarschedule.cancel();
        }
    }

    @Override
    public Object getState() {
        return new JstarState(new Coordinates(rectangle.x, rectangle.y), normalJstar, stoppedJstar);
    }

    @Override
    public String getId() {
        return "jstar";
    }


    @Override
    public void onGameSessionCreate() {
        pace = 0.01;
        rectangle.x = MathUtils.random(0, Properties.SCREEN_WIDTH - rectangle.width);
        rectangle.y = 20;
    }

    @Override
    public void onGameSessionEnd() {
        pace = 0;
    }
}
