package game;

import game.graphics.*;
import menu.*;
import org.lwjgl.opengl.Display;

public class MainScreen implements Screen {

    public TextureAtlas atlas = new TextureAtlas(TextureAtlas.SMALL);
    public SpriteBatch batch;
    private Game game;

    public static final String NAME = "MainMenu";

    private String[] savedMaps;

    private String[] savedGames;

    private static String[] options = new String[]{"load game", "new game", "load map", "new map"};


    private int[] background;

    public MainScreen(Game game) {
        this.game = game;
        background = atlas.addTexture("/menu/main_background");
        batch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(atlas), 1000);
    }

    public void load(String[] savedGames, String[] savedMaps) {
        this.savedMaps = savedMaps;
        this.savedGames = savedGames;
        MainMenu menu = new MainMenu(25, 15, 16, Menu.NORMAL_TILE_SET, savedGames, savedMaps, game);
        menu.open(Display.getWidth() / 2 - 200, Display.getHeight() / 2 - 300);
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

    @Override
    public void returnResult(Result result) {

    }

    @Override
    public void screenResized(int width, int height) {

    }
}
