package menu;

import game.Game;
import game.Screen;
import game.graphics.*;
import menu.result.Result;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;
import java.util.List;

public class MainMenu implements Screen {

    public TextureAtlas atlas = new TextureAtlas(TextureAtlas.SMALL);
    public SpriteBatch batch;

    public static final String NAME = "MainMenu";

    private final int MAIN_MENU = 0, LOAD_MENU = 1, EDITOR_MENU = 2;

    private int state = MAIN_MENU;

    private static List<String> savedMaps = new ArrayList<String>();

    private static List<String> savedGames = new ArrayList<String>();

    private static List<String> options = new ArrayList<String>();

    private int currentCursorLocation;

    private int[] background, cursor;

    private int[][] cursorPosition = new int[3][1];

    private int[] newGame = new int[2], loadGame = new int[2], mapEditor = new int[2];

    public MainMenu() {
        background = atlas.addTexture(ImageManager.getImage("/menu/main_background"));
        batch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(atlas), 1000);
        updateScreen(Display.getWidth(), Display.getHeight());
        options.add("new game");
        options.add("load game");
        options.add("map editor");
        savedMaps.add("new map");
        Menu menu = new StringMenu(25, 11, 16, 16, "corner", "side", "middle");
        menu.setVerticalSpacing(40);
        Result result = new Result();
        result.setStringList(options);
        menu.openForResult(result, this, Display.getWidth() / 2 - 200, Display.getHeight() / 2 - 300);
    }

    @Override
    public void update(long delta) {
    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(0, 0, Display.getWidth(), Display.getHeight(), background[0], background[1], background[2], background[3]);
        batch.end();
    }

    public void updateScreen(int width, int height) {

    }

    @Override
    public void returnResult(Result result) {
        switch (state) {
            case MAIN_MENU:
                int selection =  result.getSelection();
                if (selection == 0) {
                    Game.closeMenu();
                    Game.loadNewGame("new Game");
                }
                else if (selection == 1) {
                    if (savedGames.size() > 0) {
                        Game.closeMenu();
                        Menu menu = new StringMenu(25, 11, 16, 16, "corner", "side", "middle");
                        Result r = new Result();
                        r.setStringList(savedGames);
                        menu.openForResult(r, this, Display.getWidth() / 2 - 200, Display.getHeight() / 2 - 300);
                        state = LOAD_MENU;
                    }
                }
                else if (selection == 2) {
                    if (savedMaps.size() > 0) {
                        Game.closeMenu();
                        Menu menu = new StringMenu(25, 11, 16, 16, "corner", "side", "middle");
                        Result r = new Result();
                        r.setStringList(savedMaps);
                        menu.openForResult(r, this, Display.getWidth() / 2 - 200, Display.getHeight() / 2 - 300);
                        state = EDITOR_MENU;
                    }
                }
                break;
            case LOAD_MENU:

                break;
            case EDITOR_MENU:
                if (result.getReturnString().equals("new map")) {
                    Game.closeMenu();
                    Game.loadNewMap("new map");
                }
                else {
                    Game.loadMap(result.getReturnString());
                }
                break;
        }
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
