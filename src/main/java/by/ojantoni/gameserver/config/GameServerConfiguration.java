package by.ojantoni.gameserver;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameServerConfiguration {

    @Bean
    public HeadlessApplication getApplication(GameLoop gameLoop) {
        return new HeadlessApplication(gameLoop);
    }
}
