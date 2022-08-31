package by.ojantoni.gameserver.actors;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PlayersRegistry {
    private final Set<Player> players = Collections.synchronizedSet(new HashSet<>());
    public void add(Player player){
        players.add(player);
    }

    public Player get(String id){
        for (Player player : players) {
            if(player.getId().equals(id)){
                return player;
            }
        }
        return null;
    }

    public void remove(String id){
        players.removeIf(p -> p.getId().equals(id));
    }

    public List<Player> getAll(){
        return new ArrayList<>(players);
    }
}
