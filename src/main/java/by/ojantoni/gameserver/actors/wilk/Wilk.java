package by.ojantoni.gameserver.actors.wilk;

import by.ojantoni.gameserver.actors.AbstractActor;
import by.ojantoni.gameserver.actors.jstar.Jstar;
import by.ojantoni.gameserver.game.GameSessionListener;
import by.ojantoni.gameserver.messages.core.Coordinates;
import by.ojantoni.gameserver.util.Properties;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.function.Function;

@Component
public class Wilk extends AbstractActor implements GameSessionListener {
    @Autowired
    private Jstar jstar;
    private boolean isActive = true;
    private float pace = 0.2f;
    private boolean isAlive = true;

    public Wilk() {
        rectangle = new Rectangle();
    }

    @Override
    public void calculateNewCoordinates() {
        if(isActive){
            Rectangle jstarRectangle = jstar.getRectangle();
            rectangle.y = rectangle.y + ((jstarRectangle.y - rectangle.y) / Math.abs(jstarRectangle.y - rectangle.y)) * pace;
            rectangle.x = rectangle.x + ((jstarRectangle.x - rectangle.x) / Math.abs(jstarRectangle.x - rectangle.x)) * pace;
            checkForCollisionsWithJstar();
        }
    }

    private void checkForCollisionsWithJstar() {
        Rectangle jr = jstar.getRectangle();
        float dst = Vector2.dst(jr.x, jr.y, rectangle.x, rectangle.y);
        if(dst<Properties.JSTAR_WILK_MIN_DISTANCE){
            jstar.stop(6);
            jstar.setPace(0.005);
            isAlive = false;
        }
    }

    @Override
    public boolean shouldBeDeletedFromGame() {
        return !isAlive;
    }

    @Override
    public void pause() {
        isActive = false;
    }

    @Override
    public void resume() {
        isActive = true;
    }

    private void setInitCoord() {
        Random r = new Random();
        int i = r.nextInt(4) + 1;
        switch (i){
            case 1: rectangle.x = 0; rectangle.y = 0; break;
            case 2: rectangle.x = 0; rectangle.y = Properties.SCREEN_HEIGHT; break;
            case 3: rectangle.x = Properties.SCREEN_WIDTH; rectangle.y = Properties.SCREEN_HEIGHT; break;
            case 4: rectangle.x = Properties.SCREEN_WIDTH; rectangle.y = 0; break;
        }
    }

    @Override
    public Object getState() {
        return new WilkState(new Coordinates(rectangle.x, rectangle.y), isAlive);
    }

    @Override
    public String getId() {
        return "wilk";
    }

    @Override
    public void onGameSessionCreate() {
        isAlive = true;
    }

    @Override
    public void onGameSessionEnd() {

    }

    @Override
    public void onGameLevelAdd() {
        if(!isAlive){
            setInitCoord();
            isAlive = true;
        }
    }
}
