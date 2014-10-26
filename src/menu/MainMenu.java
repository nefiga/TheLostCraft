package menu;

import game.Screen;

import java.util.ArrayList;
import java.util.List;

public class MainMenu implements Screen{

    public static final String NAME = "MainMenu";

    private static List<String> savedMaps = new ArrayList<String>();

    private static List<String> savedGames = new ArrayList<String>();

    @Override
    public void update(long delta) {

    }

    @Override
    public void render() {
    }

    public static void saveMap(String name) {
        savedMaps.add(name);
    }

    public static void saveGame(String name) {
        savedGames.add(name);
    }

    public String getMap(int location) {
        return savedMaps.get(location);
    }

    public String getGame(int location) {
        return savedGames.get(location);
    }
}
