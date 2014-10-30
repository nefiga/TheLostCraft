package game.util;

import level.Map;

import java.io.Serializable;
import java.util.HashMap;

public class GameData implements Serializable {

    public static final String NAME = "game data";

    private java.util.Map<String, level.Map> savedMaps = new HashMap<String, level.Map>();
    private java.util.Map<String, LevelData> savedGames = new HashMap<String, LevelData>();

    public void saveMap(Map map) {
        savedMaps.put(map.getName(), map);
    }

    public void saveGame(LevelData data) {
        savedGames.put(data.getName(), data);
    }

    public Map getMap(String name) {
        return savedMaps.get(name);
    }

    public LevelData getLevelData(String name) {
        return savedGames.get(name);
    }

    public String[] getMapKeys() {
        Object[]  objects = savedMaps.keySet().toArray();
        String[] strings = new String[objects.length];
        for (int i =0; i < strings.length; i++) {
            strings[i] = (String) objects[i];
        }
        return strings;
    }

    public String[] getGameKeys() {
        Object[]  objects = savedGames.keySet().toArray();
        String[] strings = new String[objects.length];
        for (int i =0; i < strings.length; i++) {
            strings[i] = (String) objects[i];
        }
        return strings;
    }
}
