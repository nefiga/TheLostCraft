package editor;

import game.Game;
import game.graphics.ImageManager;
import game.graphics.SpriteBatch;
import game.graphics.Texture;
import game.graphics.TextureAtlas;
import level.Map;
import org.lwjgl.opengl.Display;
import tile.Tile;

import java.util.Random;

public class Editor {

    SpriteBatch editorBatch, tileBatch;
    TextureAtlas editorAtlas;
    EditorMap editorMap;

    private int[] menuTop, menuBottom;

    private int[] tiles;

    private int[][] pageLayout = new int[][]{{100, 100}, {250, 350, 450}};

    private Tile[][] tilePage;

    private int screenWidth, screenHeight;

    public static final int X1 = 8, X2 = 16, X3 = 32, X4 = 64;

    private int zoom = X4;

    private int page = 0, pages;

    private int x = 1000, y = 1000;

    public Editor() {
        editorAtlas = new TextureAtlas(TextureAtlas.MEDIUM);
        editorMap = new EditorMap();
        createTextureAtlas();
        tileBatch = new SpriteBatch(new Texture(Tile.tileAtlas), 1500);
        editorBatch = new SpriteBatch(new Texture(editorAtlas), 100);
        screenWidth = Display.getWidth();
        screenHeight = Display.getHeight();
        tiles = new int[500 * 500];
        Random random = new Random();
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = random.nextInt(5)+1;

        }
    }

    public Editor(Map map) {
        editorAtlas = new TextureAtlas(TextureAtlas.MEDIUM);
        editorMap = new EditorMap();
        createTextureAtlas();
        tileBatch = new SpriteBatch(new Texture(Tile.tileAtlas), 1500);
        editorBatch = new SpriteBatch(new Texture(editorAtlas), 100);
        tiles = map.tiles;
        screenWidth = Display.getWidth();
        screenHeight = Display.getHeight();
    }

    private void loadTileList() {
        Tile[] tiles = Tile.getTiles();
        pages = tiles.length / 6;
        for (int p = 0; p < pages; p++) {
            for (int t = 0; t < 6; t++) {
                tilePage[p][t] = tiles[t + p * pages];
            }
        }
    }

    public void update() {
    }

    public void render() {
        tileBatch.begin();
        renderTiles();
        tileBatch.end();

        editorBatch.begin();
        renderMenu();
        editorBatch.end();

        tileBatch.begin();
        renderSelectableTiles();
        tileBatch.end();
    }

    int test;
    public void renderMenu() {
        editorBatch.draw(screenWidth - 400, 0, menuTop[2], screenHeight / 2, menuTop[0], menuTop[1], menuTop[2], menuTop[3]);
        editorBatch.draw(screenWidth - 400, screenHeight / 2, menuBottom[2], screenHeight / 2, menuBottom[0], menuBottom[1], menuBottom[2], menuBottom[3]);
    }

    public void renderSelectableTiles() {

    }

    public Tile getTile(int x, int y, boolean tilePrecision) {
        if (!tilePrecision) {
            x = x / zoom;
            y = y / zoom;
        }
        if (x < 0 || y < 0 || x >= 500 || y >= 500)
            return Tile.voidTile;

        return Tile.getTile(tiles[x + y * 500]);
    }

    protected void renderTiles() {
        int startX = Game.getXOffset() / zoom - 1;
        int endX = (Display.getWidth() + Game.getXOffset()) / zoom + 1;
        int startY = Game.getYOffset() / zoom - 1;
        int endY = (Display.getHeight() + Game.getYOffset()) / zoom + 1;
        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                getTile(x, y, true).render(tileBatch, x * zoom - Game.getXOffset(), y * zoom - Game.getYOffset(), zoom, zoom);
            }
        }
    }

    private void createTextureAtlas() {
        menuTop = editorAtlas.addTexture(ImageManager.getImage("/editor/menu_top"));
        menuBottom = editorAtlas.addTexture(ImageManager.getImage("/editor/menu_bottom"));
    }

    public void zoomIn() {
        switch (zoom) {
            case X1:
                zoom = X2;
                break;
            case X2:
                zoom = X3;
                break;
            case X3:
                zoom = X4;
                break;
            case X4:
                zoom = X1;
                break;
        }
    }

    public void zoomOut() {
        switch (zoom) {
            case X1:
                zoom = X4;
                break;
            case X2:
                zoom = X1;
                break;
            case X3:
                zoom = X2;
                break;
            case X4:
                zoom = X3;
                break;
        }
    }

    public void nextPage() {
        page++;
        if (page > pages) page = 0;
    }

    public void previousPage() {
        page--;
        if (page < 0) page = pages;
    }

    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void updateScreenSize(int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
