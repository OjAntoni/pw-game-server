package by.ojantoni.gameserver.objects;

import by.ojantoni.gameserver.messages.core.Coordinates;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public abstract class AbstractObject {
    protected final Rectangle rectangle = new Rectangle();
    protected boolean isAlive = true;
    protected int id;

    public void calculateNewState(){};
    public Coordinates getCoordinates(){
        return new Coordinates(rectangle.x, rectangle.y);
    }
    public boolean isOutdated(){
        return !isAlive;
    }
    public void setAsOutdated(){
        isAlive=false;
    }
    public int getId(){
        return id;
    }
    public abstract String getName();
}
