package game.util;

import entity.Player;
import level.Map;

public class LevelData {

    public Map map;
    public Player player;

    public LevelData(Map map, Player player) {
        this.map = map;
        this.player = player;
    }
}
