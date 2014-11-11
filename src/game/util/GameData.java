package game.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameData implements Serializable {

    public static final String NAME = "game data";

    private List<String> savedMaps = new ArrayList<String>();
    private List<String> savedGames = new ArrayList<String>();

    public void saveMap(String map) {
        if (!savedMaps.contains(map)) savedMaps.add(map);
    }

    public void saveGame(String game) {
        if (!savedGames.contains(game)) savedGames.add(game);
    }

    public String[] getMapNames() {
        String[] strings = new  String[savedMaps.size()];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = savedMaps.get(i);
        }
        return strings;
    }

    public String[] getGameNames() {
        String[] strings = new  String[savedGames.size()];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = savedGames.get(i);
        }
        return strings;
    }
}
