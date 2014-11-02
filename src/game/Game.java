package game;

import editor.MapEditor;
import entity.Player;
import game.util.FileIO;
import game.util.GameData;
import game.util.LevelData;
import input.EditorInputReceiver;
import input.MenuInputReceiver;
import input.PlayerInputReceiver;
import level.*;
import level.Level;
import menu.Menu;
import org.lwjgl.opengl.Display;

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
    private static GameData gameData;
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
        gameData = (GameData) FileIO.loadClass(GameData.NAME);
        if (gameData == null) gameData = new GameData();
        mainScreen = new MainScreen(this);
        screenManager = new ScreenManager();
        screenManager.addScreen(Level.NAME, level);
        screenManager.addScreen(MapEditor.NAME, mapEditor);
        screenManager.addScreen(MainScreen.NAME, mainScreen);
        loadMainMenu();
    }

    public void update(long delta) {
        if (menuOpen) {
            menuInput.update();
            menu.update(delta);
        } else {
            switch (state) {
                case MAIN_MENU:
                    mainScreen.update(delta);
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
        }

        updateTime(delta);
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT);
        screenManager.render();

        if (menuOpen) menu.render();
    }

    public static void loadMainMenu() {
        mainScreen.load(gameData.getGameKeys(), gameData.getMapKeys());
        state = MAIN_MENU;
        screenManager.setCurrentLevel(MainScreen.NAME);
    }

    public void loadGame(String name) {
        LevelData data = gameData.getLevelData(name);
        player = data.getPlayer();
        level.loadLevel(data, player);
        PlayerInputReceiver.setPlayer(player);
        screenManager.setCurrentLevel(Level.NAME);
        setState(GAME);
    }

    public static void closeGame() {
        level.save();
        loadMainMenu();
    }

    public void loadNewGame(String map) {
        Player p = new Player(100, 100);
        level.loadNewLevel(new LevelData(gameData.getMap(map)), p);
        player = p;
        PlayerInputReceiver.setPlayer(player);
        screenManager.setCurrentLevel(Level.NAME);
        setState(GAME);
    }

    public void loadMapEditor(String name) {
        Map map = gameData.getMap(name);
        mapEditor.loadEditor(map);
        screenManager.setCurrentLevel(MapEditor.NAME);
        setState(MAP_EDITOR);
    }

    public static void closeMapEditor() {
        mapEditor.save();
        loadMainMenu();
    }

    public void loadNewMap(String name) {
        Map map = new Map(name);
        mapEditor.loadEditor(map);
        screenManager.setCurrentLevel(MapEditor.NAME);
        setState(MAP_EDITOR);
    }

    public void updateTime(long delta) {
        time += delta;
        if (delta >= 2100) delta = 0;
    }

    public void save() {
        FileIO.saveClass(GameData.NAME, gameData);
    }

    public void resized() {
        mapEditor.updateScreenSize(Display.getWidth(), Display.getHeight());
    }

    public void dispose() {
        if (state == MAP_EDITOR) {
            mapEditor.save();
        } else if (state == GAME) {
            level.save();
        }
        save();
    }

    public static void openMenu(Menu m) {
        menu = m;
        menuInput.setMenu(menu);
        menuOpen = true;
    }

    public static void closeMenu() {
        menuOpen = false;
    }

    public static void setState(int state) {
        Game.state = state;
    }

    public static GameData getGameData() {return gameData;}

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
