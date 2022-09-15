package by.ojantoni.gameserver.game;

import lombok.Getter;

import java.util.List;

public enum GameLevel {
    ONE(List.of("jstar", "wilk")),
    TWO(List.of("jstar", "wilk")),
    THREE(List.of("jstar", "wilk")),
    FOUR(List.of("jstar", "wilk"));

    public static final int LEVELS_NUMBER = 4;
    @Getter
    private final List<String> actorNamesInGame;

    GameLevel(List<String> actorNamesInGame) {
        this.actorNamesInGame = actorNamesInGame;
    }

    public static GameLevel getLevel(int level){
        switch (level){
            case 2: return TWO;
            case 3: return THREE;
            case 4: return FOUR;
            default: return ONE;
        }
    }
}
