package by.ojantoni.gameserver.objects;

import by.ojantoni.gameserver.actors.registry.ActorsRegistry;
import com.badlogic.gdx.utils.Timer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class CleanCode extends AbstractObject {
    private ApplicationContext appContext;
    public static final int TTL = 5;
    public static final int TIME_TO_STOP = 5;

    public CleanCode(int id, float x, float y) {
        rectangle.x = x;
        rectangle.y = y;
        this.id = id;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                isAlive = false;
            }
        }, TTL, 0, 1);
    }

    @Override
    public String getName() {
        return "clean_code";
    }
}
