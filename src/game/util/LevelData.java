package game.util;

import entity.Player;
import level.Map;

import java.io.Serializable;

public class LevelData implements Serializable{

    private String name;
    private Map map;
    private Player player;

    public LevelData(Map map, Player player) {
        this.map = map;
        this.player = player;
        this.name = map.getName();
    }

    public String getName() {
        return name;
    }

    public Map getMap() {
        return map;
    }

    public Player getPlayer() {
        return player;
    }
}
