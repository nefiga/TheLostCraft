package game.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameData implements Serializable {

    public static final String NAME = "game data";

    public static final int MAX_SAVES = 5;

    private String[] savedMaps = new String[]{"empty", "empty", "empty", "empty", "empty"};
    private String[] savedGames = new String[]{"empty", "empty", "empty", "empty", "empty"};

    public GameData() {

    }

    public void saveMap(String map) {
        int position = hasSpace(savedMaps, map);
        if (position > -1)
            savedMaps[position] = map;
        else {
            for (int i = 0; i < savedMaps.length; i++) {
                if (i < 3) savedMaps[i] = savedMaps[i + 1];
                else savedMaps[i] = map;
            }
        }
    }

    public void saveGame(String game) {
        int position = hasSpace(savedGames, game);
        if (position > -1)
            savedGames[position] = game;
        else {
            for (int i = 0; i < savedGames.length; i++) {
                if (i < 3) savedGames[i] = savedGames[i + 1];
                else savedGames[i] = game;
            }
        }
    }

    public String[] getMapNames() {
        return savedMaps;
    }

    public String[] getGameNames() {
        return savedGames;
    }

    private int hasSpace(String[] strings, String string) {
        int place = -1;
        for (int i = 0; i < strings.length; i++) {
            if (strings[i].equals("empty"))
                place = i;
        }
        for (int i = 0; i < strings.length; i++) {
            if (strings[i].equals(string))
                place = i;
        }
        return place;
    }

}
