package by.ojantoni.gameserver.actors;

import by.ojantoni.gameserver.util.Properties;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import lombok.Getter;
import lombok.ToString;

@ToString
public class Player{
    private boolean isStopped;
    private final Rectangle rectangle;
    @Getter
    private final String id;
    public Player(CoordinatesDto coordinates, String id) {
        rectangle = new Rectangle();
        rectangle.width = 49;
        rectangle.height = 49;
        rectangle.x = coordinates.x;
        rectangle.y = coordinates.y;
        this.id = id;
    }

    public CoordinatesDto getCoordinatesDto() {
        return new CoordinatesDto(rectangle.x, rectangle.y, id);
    }

    public void setCoordinates(CoordinatesDto coordinates) {
        if(isStopped){
            return;
        }
        rectangle.x = coordinates.x;
        rectangle.y = coordinates.y;
        if (rectangle.x < 0)
            rectangle.x = 0;
        if (rectangle.x > Properties.SCREEN_WIDTH - rectangle.width)
            rectangle.x = Properties.SCREEN_WIDTH - rectangle.width;
        if(rectangle.y < 0)
            rectangle.y = 0;
        if(rectangle.y > Properties.SCREEN_HEIGHT - rectangle.height)
            rectangle.y = Properties.SCREEN_HEIGHT - rectangle.height;
    }


    public void stop(int secondsDelay) {
        isStopped = true;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                isStopped = false;
            }
        }, secondsDelay, 0, 1);
    }

    public boolean canBeMoved() {
        return !isStopped;
    }

}
