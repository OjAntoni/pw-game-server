package by.ojantoni.gameserver.actors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class PlayersRegistry {
    private final Set<Player> players = Collections.synchronizedSet(new HashSet<>());
    public void add(Player player){
        players.add(player);
        log.info(players.toString());
    }

    public Player get(int id){
        for (Player player : players) {
            if(player.getId()==id){
                return player;
            }
        }
        return null;
    }

    public void remove(int id){
        players.removeIf(p -> p.getId()==id);
    }

    public List<Player> getAll(){
        return new ArrayList<>(players);
    }
}
