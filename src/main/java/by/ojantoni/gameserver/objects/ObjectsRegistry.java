package by.ojantoni.gameserver.objects;

import by.ojantoni.gameserver.game.GameSessionListener;
import com.badlogic.gdx.utils.Timer;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ObjectsRegistry implements GameSessionListener {

    private final List<AbstractObject> objects = new LinkedList<>();
    Timer.Task schedule;

    public void add(AbstractObject object){
        objects.add(object);
    }

    public <T> List<T> getAll(Class<T> tClass){
        return objects.stream().filter(o -> o.getClass() == tClass).map(tClass::cast).collect(Collectors.toList());
    }

    public void updateState(){
        objects.forEach(AbstractObject::calculateNewState);
    }

    @Override
    public void onGameSessionCreate() {
        schedule = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                objects.removeIf(AbstractObject::isOutdated);
            }
        }, 0f, 0.5f);
    }

    @Override
    public void onGameSessionEnd() {
        if(schedule != null){
            schedule.cancel();
        }
    }

    public void deleteOutdated() {
        objects.removeIf(AbstractObject::isOutdated);
    }
}
