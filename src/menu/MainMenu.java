package menu;

import game.Game;
import game.Screen;
import game.graphics.*;
import menu.result.Result;
import org.lwjgl.opengl.Display;

public class MainMenu implements Screen {

    public TextureAtlas atlas = new TextureAtlas(TextureAtlas.SMALL);
    public SpriteBatch batch;
    private Game game;

    public static final String NAME = "MainMenu";

    private final int MAIN_MENU = 0, LOAD_GAME = 1, NEW_GAME = 2, LOAD_MAP = 3, NEW_MAP = 4;

    private int state = MAIN_MENU;

    private String[] savedMaps;

    private String[] savedGames;

    private static String[] options = new String[]{"load game", "new game", "load map", "new map"};


    private int[] background;

    public MainMenu(Game game) {
        this.game = game;
        background = atlas.addTexture(ImageManager.getImage("/menu/main_background"));
        batch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(atlas), 1000);
        updateScreen(Display.getWidth(), Display.getHeight());
    }

    public void load(String[] savedGames, String[] savedMaps) {
        this.savedMaps = savedMaps;
        this.savedGames = savedGames;
        state = MAIN_MENU;
        Menu menu = new StringMenu(25, 15, 16, 16, "corner", "side", "middle");
        menu.setVerticalSpacing(40);
        Result result = new Result();
        result.setStringArray(options);
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
                int selection = result.getSelection();
                System.out.println(selection);
                // Load Game
                if (selection == 0) {
                    if (savedGames.length > 0) {
                        Game.closeMenu();
                        Menu menu = new StringMenu(25, 11, 16, 16, "corner", "side", "middle");
                        Result r = new Result();
                        r.setStringArray(savedGames);
                        menu.openForResult(r, this, Display.getWidth() / 2 - 200, Display.getHeight() / 2 - 300);
                        state = LOAD_GAME;
                    }
                }
                // New Game
                else if (selection == 1) {
                    Game.closeMenu();
                    Menu menu = new StringMenu(20, 10, 16, 16, "corner", "side", "middle");
                    Result r = new Result();
                    r.setStringArray(savedMaps);
                    menu.openForResult(r, this, Display.getWidth() / 2 - 200, Display.getHeight() / 2 - 300);
                    state = NEW_GAME;
                }
                // Load Map Editor
                else if (selection == 2) {
                    if (savedMaps.length > 0) {
                        Game.closeMenu();
                        Menu menu = new StringMenu(25, 11, 16, 16, "corner", "side", "middle");
                        Result r = new Result();
                        r.setStringArray(savedMaps);
                        menu.openForResult(r, this, Display.getWidth() / 2 - 200, Display.getHeight() / 2 - 300);
                        state = LOAD_MAP;
                    }
                }
                // New Map Editor
                else if (selection == 3) {
                    Game.closeMenu();
                    Menu menu = new TextViewMenu(25, 10, 16, 16, "corner", "side", "middle");
                    Result r = new Result();
                    menu.openForResult(r, this, Display.getWidth() / 2 - 200, Display.getHeight() / 2 - 300);
                    state = NEW_MAP;
                }
                break;
            case LOAD_GAME:
                if (result.getString() != null) {
                    Game.closeMenu();
                    game.loadGame(result.getString());
                }
                break;
            case NEW_GAME:
                if (savedMaps.length > 0) {
                    Game.closeMenu();
                    game.loadNewGame(result.getString());
                }
                break;
            case LOAD_MAP:
                if (result.getString() != null) {
                    Game.closeMenu();
                    game.loadMapEditor(result.getString());
                }
                break;
            case NEW_MAP:
                Game.closeMenu();
                game.loadNewMap(result.getString());
                break;
        }
    }
}
