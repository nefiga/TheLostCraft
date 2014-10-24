package game;

import editor.Editor;
import entity.Player;
import game.graphics.TextureAtlas;
import game.util.FileIO;
import input.EditorInput;
import input.Input;
import input.PlayerInput;
import level.Level;
import level.LevelManager;
import level.Map;
import level.RandomMapGenerator;
import org.lwjgl.opengl.Display;
import tile.Tile;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

public class Game extends GameLoop {

    public long time = 0;

    LevelManager levelManager;
    Editor editor;
    Player player;
    Input playerInput, editorInput;

    private static int xOffset, yOffset;

    private static int state = 1;

    public static final int MAIN_MENU = 0, GAME = 1, MAP_EDITOR = 2;

    public void init() {
        player = new Player(64 * 50, 64 * 50);
        playerInput = new PlayerInput(player);

        Map loadMap = (Map) FileIO.loadClass("map1");
        if (loadMap != null) {
            editor = new Editor(loadMap);
        }
        else {
            editor = new Editor();
        }

        editorInput = new EditorInput(editor);

        levelManager = new LevelManager();
        levelManager.addLevel("Testing", new Level(RandomMapGenerator.generateMap(1000), player));
        levelManager.setCurrentLevel("Testing");
        glClearColor(255, 255, 255, 1);
    }

    public void update(long delta) {
        switch (state) {
            case MAIN_MENU:

                break;
            case GAME:
                playerInput.update();
                xOffset = (int) (player.getX() - (Display.getWidth() / 2 - 32));
                yOffset = (int) player.getY() - (Display.getHeight() / 2 - 32);
                levelManager.update(delta);
                break;
            case MAP_EDITOR:
                editorInput.update();
                xOffset = editor.getX() - (Display.getWidth() / 2 - 32);
                yOffset = editor.getY() - (Display.getHeight() / 2 - 32);
                editor.update(delta);
                break;
        }

        updateTime(delta);
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT);
        switch (state) {
            case MAIN_MENU:

                break;
            case GAME:
                levelManager.render();
                break;
            case MAP_EDITOR:
                editor.render();
                break;
        }
    }

    public void updateTime(long delta) {
        time += delta;
        if (delta >= 2100) delta = 0;
    }

    public void resized() {
        editor.updateScreenSize(Display.getWidth(), Display.getHeight());
    }

    public void dispose() {
        if (state == MAP_EDITOR) {
            editor.save();
        }
    }

    public static void setState(int state) {
        Game.state = state;
    }

    public static int getXOffset() {
        return xOffset;
    }

    public static int getYOffset() {
        return yOffset;
    }

    public static int tileToPixel(int tile) {
        return tile << 6;
    }

    public static int pixelToTile(int pixel) {
        return pixel >> 6;
    }

    public static void main(String[] args) {
        GameLoop game = new Game();
    }
}
