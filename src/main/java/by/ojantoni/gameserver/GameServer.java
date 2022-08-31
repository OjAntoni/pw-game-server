package by.ojantoni.gameserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class GameServer {
    public static void main(String[] args) {
        SpringApplication.run(GameServer.class, args);
    }
}
