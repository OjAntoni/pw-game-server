package by.ojantoni.gameserver.objects;

import by.ojantoni.gameserver.actors.AbstractActor;
import by.ojantoni.gameserver.actors.jstar.Jstar;
import by.ojantoni.gameserver.actors.registry.ActorsRegistry;
import com.badlogic.gdx.math.Vector2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CleanCodeObjectRule implements ObjectRule {
    @Autowired
    private ActorsRegistry actorsRegistry;
    @Autowired
    private ObjectsRegistry objectsRegistry;

    @Override
    public void process() {
        List<CleanCode> all = objectsRegistry.getAll(CleanCode.class);
        Jstar jstar = (Jstar) actorsRegistry.get("jstar");
        all.forEach(b -> {
            if(Vector2.dst(b.rectangle.x, b.rectangle.y, jstar.getRectangle().x, jstar.getRectangle().y) < 15){
                b.setAsOutdated();
                jstar.stop(CleanCode.TIME_TO_STOP);
                jstar.setPace(jstar.getPace()/2);
            }
        });
    }
}
