package editor;

import game.Game;
import game.graphics.ImageManager;
import game.graphics.SpriteBatch;
import game.graphics.Texture;
import game.graphics.TextureAtlas;
import level.Map;
import org.lwjgl.opengl.Display;
import tile.Tile;

public class Editor {

    SpriteBatch editorBatch, tileBatch;
    TextureAtlas editorAtlas;
    Map map;

    private int[] menuTop, menuBottom;

    private int[] tiles;

    private int screenWidth, screenHeight;

    public Editor() {
        editorAtlas = new TextureAtlas(TextureAtlas.MEDIUM);
        createTextureAtlas();
        tileBatch = new SpriteBatch(new Texture(Tile.tileAtlas), 1500);
        editorBatch = new SpriteBatch(new Texture(editorAtlas), 1000);
        tiles = new int[500 * 500];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = Tile.emptyTile.getID();
        }
    }

    public void update() {
        screenWidth = Display.getWidth();
        screenHeight = Display.getHeight();
    }

    public void render() {
        tileBatch.begin();
        renderTiles();
        tileBatch.end();

        editorBatch.begin();
        editorBatch.draw(screenWidth - 405, 0, menuTop[2], screenHeight / 2, menuTop[0], menuTop[1], menuTop[2], menuTop[3]);
        editorBatch.draw(screenWidth - 405, screenHeight / 2, menuBottom[2], screenHeight / 2, menuBottom[0], menuBottom[1], menuBottom[2], menuBottom[3]);
        editorBatch.end();
    }

    public Tile getTile(int x, int y, boolean tilePrecision) {
        if (!tilePrecision) {
            x = Game.pixelToTile(x);
            y = Game.pixelToTile(y);
        }
        if (x < 0 || y < 0 || x >= 500 || y >= 500)
            return Tile.voidTile;

        return Tile.getTile(tiles[x + y * 500]);
    }

    protected void renderTiles() {
        for (int y = 0; y < 12; y++) {
            for (int x = 0; x < 16; x++) {
                Tile.getTile(tiles[x + y * 500]).render(tileBatch, x * 64, y * 64);
            }
        }
    }

    private void createTextureAtlas() {
        menuTop = editorAtlas.addTexture(ImageManager.getImage("/editor/menu_top"));
        menuBottom = editorAtlas.addTexture(ImageManager.getImage("/editor/menu_bottom"));
    }
}
