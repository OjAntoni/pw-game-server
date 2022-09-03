package by.ojantoni.gameserver.util;

import by.ojantoni.gameserver.game.GameSessionListener;
import com.badlogic.gdx.utils.Timer;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class InGameTimer implements GameSessionListener {
    @Getter
    private int timeMillis;
    private Timer.Task timer;


    public void start(){
        timer = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                timeMillis++;
            }
        }, 0.001f, 0.001f);
    }

    public int getTime(){
        return timeMillis/1000;
    }


    public void stop(){
        timer.cancel();
    }

    public void clear(){
        timeMillis =0;
    }


    @Override
    public void onGameSessionCreate() {
        timer = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                timeMillis++;
            }
        }, 0.001f, 0.001f);
    }

    @Override
    public void onGameSessionEnd() {
        if (timer!=null){
            timer.cancel();
        }
    }
}
