package game.util;

import entity.Player;
import level.Map;

import java.io.Serializable;

public class LevelData implements Serializable {

    private int x, y;
    private String name;
    private Map map;

    public LevelData(Map map) {
        this.map = map;
    }

    public void setName(String name) {
        this.name = name;
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

    public void updateData(Map map, Player player) {
        this.map = map;
        this.x = (int) player.getX();
        this.y = (int) player.getY();
    }
}
