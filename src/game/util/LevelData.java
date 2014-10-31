package game.util;

import entity.Player;
import level.Map;

import java.io.Serializable;

public class LevelData implements Serializable{

    private int x, y;
    private String name;
    private Map map;

    public LevelData(Map map, int x, int y) {
        this.map = map;
        this.name = map.getName();
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public Map getMap() {
        return map;
    }

    public Player getPlayer() {
        return new Player(x, y);
    }
}
