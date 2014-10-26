package game;

import editor.MapEditor;
import entity.Player;
import game.util.FileIO;
import game.util.LevelData;
import input.EditorInput;
import input.PlayerInput;
import level.*;
import level.Level;
import menu.MainMenu;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class Game extends GameLoop {

    public long time = 0;

    private static ScreenManager screenManager;
    private static Level level;
    private static MapEditor mapEditor;
    private static MainMenu mainMenu;
    private static Player player;
    private static PlayerInput playerInput;
    private static EditorInput editorInput;

    private static int xOffset, yOffset;

    private static int state = 0;

    public static final int MAIN_MENU = 0, GAME = 1, MAP_EDITOR = 2;

    public void init() {
        super.init();
        player = new Player();
        level = new Level();
        mapEditor = new MapEditor();
        mainMenu = new MainMenu();
        playerInput = new PlayerInput();
        editorInput = new EditorInput(mapEditor);
        screenManager = new ScreenManager();
        screenManager.addScreen(Level.NAME, level);
        screenManager.addScreen(MapEditor.NAME, mapEditor);
        screenManager.addScreen(MainMenu.NAME, mainMenu);
        screenManager.setCurrentLevel(MainMenu.NAME);
        loadMap("TestMap");
    }

    public void update(long delta) {
        switch (state) {
            case MAIN_MENU:
                mainMenu.update(delta);
                break;
            case GAME:
                playerInput.update();
                xOffset = (int) (player.getX() - (Display.getWidth() / 2 - 32));
                yOffset = (int) player.getY() - (Display.getHeight() / 2 - 32);
                break;
            case MAP_EDITOR:
                editorInput.update();
                xOffset = mapEditor.getX() - (Display.getWidth() / 2 - 32);
                yOffset = mapEditor.getY() - (Display.getHeight() / 2 - 32);
                mapEditor.update(delta);
                break;
        }

        updateTime(delta);
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT);
        screenManager.render();
    }

    public static void loadGame(String name) {
        LevelData data = (LevelData) FileIO.loadClass(name);
        level.loadLevel(data.map, data.player);
        player = data.player;
        PlayerInput.setPlayer(data.player);
        screenManager.setCurrentLevel(Level.NAME);
        setState(GAME);
    }

    public static void loadMap(String name) {
        Map map = (Map) FileIO.loadClass(name);
        mapEditor.loadEditor(map);
        screenManager.setCurrentLevel(MapEditor.NAME);
        setState(MAP_EDITOR);
    }

    public void updateTime(long delta) {
        time += delta;
        if (delta >= 2100) delta = 0;
    }

    public void resized() {
        mapEditor.updateScreenSize(Display.getWidth(), Display.getHeight());
    }

    public void dispose() {
        if (state == MAP_EDITOR) {
            mapEditor.save();
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
