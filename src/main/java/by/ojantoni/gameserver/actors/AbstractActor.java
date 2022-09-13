package by.ojantoni.gameserver.actors;

import by.ojantoni.gameserver.messages.core.Coordinates;
import com.badlogic.gdx.math.Rectangle;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

public abstract class AbstractActor {
    @Getter
    protected long timeAlive;
    @Getter
    protected Rectangle rectangle;

    public void stop(int secondsDelay){}
    public boolean canBeMoved(){return true;}
    public void calculateNewCoordinates(){
    }
    public Coordinates getCoordinates(){
        return new Coordinates(rectangle.x, rectangle.y);
    }
    public void setLevel(int level){}
    public void resetTimeAlive(){timeAlive=0;}
    public boolean shouldBeDeletedFromGame(){
        return false;
    }
    public void pause(){}
    public void resume(){}
    public Object getState(){return null;}
    public abstract String getId();
    public void onGameLevelAdd(){};


}
