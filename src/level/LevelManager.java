package level;

import java.util.HashMap;
import java.util.Map;

public class LevelManager {

    /**
     * The Level to be rendered
     */
    private static Level currentLevel = null;

    /**
     * A Map of all the levels. A level must be in this Map to be updated and rendered.
     */
    private Map<String, Level> levels = new HashMap<String, Level>();

    /**
     * Renders the Level at the {@code level}
     */
    public void render() {
        if (currentLevel != null) currentLevel.render();
    }

    /**
     * Updates the Level at the {@code level}
     */
    public void update(long delta) {
        if (currentLevel != null) currentLevel.update(delta);
    }

    /**
     * Adds a new Level to the {@code levels} HashMap.
     * @param name String key for the {@code levels} HashMap
     * @param level Level to be added to the {@code levels} HashMap
     */
    public void addLevel(String name, Level level) {
        // Create and exception for adding multiples of the same Level
        if (levels.containsKey(name)) System.out.println("levels already contains Level");
        levels.put(name, level);
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
    public void setCurrentLevel(String name) {
        currentLevel = levels.get(name);
    }
}
