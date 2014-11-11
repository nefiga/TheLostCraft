package level;

import game.Screen;

import java.util.HashMap;
import java.util.Map;

public class ScreenManager {

    /**
     * The Level to be rendered
     */
    private static Screen currentScreen = null;

    /**
     * A Map of all the levels. A level must be in this Map to be updated and rendered.
     */
    private Map<String, Screen> levels = new HashMap<String, Screen>();

    /**
     * Renders the Level at the {@code level}
     */
    public void render() {
        if (currentScreen != null) currentScreen.render();
    }

    /**
     * Updates the Level at the {@code level}
     */
    public void update(long delta) {
        if (currentScreen != null) currentScreen.update(delta);
    }

    /**
     * Adds a new Level to the {@code levels} HashMap.
     * @param name String key for the {@code levels} HashMap
     * @param screen Level to be added to the {@code levels} HashMap
     */
    public void addScreen(String name, Screen screen) {
        if (levels.containsKey(name)) System.out.println("levels already contains Level");
        levels.put(name, screen);
    }

    /**
     * Removes the specified Level
     * @param name The name of the Level to be removed
     */
    public void removeLevel(String name) {
        levels.remove(name);
    }

    /**
     * Sets the current Level to be updated and rendered
     * @param name The name of the level to be set as the current Level
     */
    public void setCurrentScreen(String name) {
        currentScreen = levels.get(name);
    }

    public void screenResize(int width, int height) {
        currentScreen.screenResized(width, height);
    }
}
