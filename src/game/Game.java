package game;

import editor.MapEditor;
import entity.Player;
import game.util.FileIO;
import game.util.LevelData;
import game.util.MapData;
import input.EditorInputReceiver;
import input.MenuInputReceiver;
import input.PlayerInputReceiver;
import level.*;
import level.Level;
import menu.Menu;
import org.lwjgl.opengl.Display;

import java.io.File;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class Game extends GameLoop {

    public long time = 0;

    private static ScreenManager screenManager;
    private static Level level;
    private static MapEditor mapEditor;
    private static MainScreen mainScreen;
    private Player player;
    private PlayerInputReceiver playerInput;
    private EditorInputReceiver editorInput;
    private static MenuInputReceiver menuInput;
    private static Menu menu;

    private static int xOffset, yOffset;

    public static final int MAIN_MENU = 0, GAME = 1, MAP_EDITOR = 2;
    private static boolean menuOpen;

    private static int state = MAIN_MENU;

    public void init() {
        super.init();
        player = new Player(100, 100);
        playerInput = new PlayerInputReceiver();
        level = new Level();
        mapEditor = new MapEditor();
        editorInput = new EditorInputReceiver(mapEditor);
        menuInput = new MenuInputReceiver();
        mainScreen = new MainScreen(this);
        screenManager = new ScreenManager();
        screenManager.addScreen(Level.NAME, level);
        screenManager.addScreen(MapEditor.NAME, mapEditor);
        screenManager.addScreen(MainScreen.NAME, mainScreen);
        loadMainMenu();
    }

    public void update(long delta) {
        if (menuOpen) {
            menuInput.update(delta);
            menu.update(delta);
        }

        switch (state) {
            case MAIN_MENU:
                mainScreen.update(delta);
                break;
            case GAME:
                playerInput.update(delta);
                player.update(delta);
                xOffset = (int) (player.getX() - (Display.getWidth() / 2 - 32));
                yOffset = (int) player.getY() - (Display.getHeight() / 2 - 32);
                break;
            case MAP_EDITOR:
                editorInput.update(delta);
                xOffset = mapEditor.getMapX() - (Display.getWidth() / 2 - 32);
                yOffset = mapEditor.getMapY() - (Display.getHeight() / 2 - 32);
                mapEditor.update(delta);
                break;

        }

        updateTime(delta);
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT);
        screenManager.render();

        if (menuOpen) menu.render();
    }

    public static void loadMainMenu() {
        mainScreen.load(FileIO.getSavedGames(), FileIO.getSavedMaps());
        state = MAIN_MENU;
        screenManager.setCurrentScreen(MainScreen.NAME);
    }

    public void loadGame(String name) {
        LevelData data = (LevelData) FileIO.loadClass("games" + File.separator + name);
        player = data.getPlayer();
        PlayerInputReceiver.setPlayer(player);
        screenManager.setCurrentScreen(Level.NAME);
        setState(GAME);
        level.loadLevel(data, player);
    }

    public void loadNewGame(String name, String mapName) {
        MapData mapData = (MapData) FileIO.loadClass("maps" + File.separator + mapName);
        Player p = new Player(100, 100);
        player = p;
        PlayerInputReceiver.setPlayer(player);
        screenManager.setCurrentScreen(Level.NAME);
        setState(GAME);
        level.loadNewLevel(name, new LevelData(mapData, p), p);
    }

    public static void saveGame(LevelData levelData) {
        FileIO.saveClass("games" + File.separator + levelData.getName(), levelData);
    }

    public static void closeGame() {
        level.save();
        loadMainMenu();
    }

    public void loadMapEditor(String name) {
        MapData mapData = (MapData) FileIO.loadClass("maps" + File.separator + name);
        Map map = new Map(mapData.getName(), mapData.getTiles(), mapData.getTileData(), mapData.getWidth(), mapData.getHeight());
        mapEditor.loadEditor(map);
        screenManager.setCurrentScreen(MapEditor.NAME);
        setState(MAP_EDITOR);
    }

    public void loadNewMap(String name) {
        Map map = new Map(name);
        mapEditor.loadEditor(map);
        screenManager.setCurrentScreen(MapEditor.NAME);
        setState(MAP_EDITOR);
    }

    public static void saveMap(MapData mapData) {
        FileIO.saveClass("maps" + File.separator + mapData.getName(), mapData);
    }

    public static void closeMapEditor() {
        mapEditor.save();
        loadMainMenu();
    }

    public static void openMenu(Menu m) {
        menu = m;
        menuInput.setMenu(menu);
        menu.screenResized(Display.getWidth(), Display.getHeight());
        menuOpen = true;
    }

    public static void closeMenu() {
        menuOpen = false;
    }

    public void updateTime(long delta) {
        time += delta;
        if (delta >= 2100) delta = 0;
    }

    public void resized() {
        screenManager.screenResize(Display.getWidth(), Display.getHeight());
        if (menuOpen) menu.screenResized(Display.getWidth(), Display.getHeight());
    }

    public void dispose() {
        if (state == MAP_EDITOR) {
            mapEditor.save();
        } else if (state == GAME) {
            level.save();
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
