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

    private int[] pageX = new int[3];
    private int[] pageY = new int[10];

    private int[][] tilePage;

    private int screenWidth, screenHeight;

    private int[] menuTopLocation = new int[4];
    private int[] menuBottomLocation = new int[4];

    public static final int X1 = 16, X2 = 32, X3 = 64;

    private int zoom = X3;

    private int page = 0, pages;

    private int x = 1000, y = 1000;

    public Editor() {
        init();
        tiles = new int[500 * 500];
        Random random = new Random();
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = random.nextInt(5) + 1;

        }
    }

    public Editor(Map map) {
        tiles = map.tiles;
        init();
    }

    private void init() {

        editorAtlas = new TextureAtlas(TextureAtlas.MEDIUM);
        editorMap = new EditorMap();
        createTextureAtlas();
        tileBatch = new SpriteBatch(new Texture(Tile.tileAtlas), 1500);
        editorBatch = new SpriteBatch(new Texture(editorAtlas), 100);
        screenWidth = Display.getWidth();
        screenHeight = Display.getHeight();
        updateScreenSize(screenWidth, screenHeight);
    }

    private void loadTileList() {
        Tile[] tiles = Tile.getTiles();

        if (tiles.length < 30) pages = 1;
        else pages = tiles.length / 30;

        tilePage = new int[pages][30];

        for (int p = 0; p < pages; p++) {
            for (int t = 0; t < tiles.length; t++) {
                tilePage[p][t] = tiles[t + p * pages].getID();
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

    public void renderMenu() {
        editorBatch.draw(menuTopLocation[0], menuTopLocation[1], menuTopLocation[2], menuTopLocation[3], menuTop[0], menuTop[1], menuTop[2], menuTop[3]);
        editorBatch.draw(menuBottomLocation[0], menuBottomLocation[1], menuBottomLocation[2], menuBottomLocation[3], menuBottom[0], menuBottom[1], menuBottom[2], menuBottom[3]);
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
                zoom = X1;
                break;
        }
    }

    public void zoomOut() {
        switch (zoom) {
            case X1:
                zoom = X3;
                break;
            case X2:
                zoom = X1;
                break;
            case X3:
                zoom = X2;
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
        menuTopLocation[0] = screenWidth - screenWidth / 5;
        menuTopLocation[1] = 0;
        menuTopLocation[2] = screenWidth / 5;
        menuTopLocation[3] = screenHeight / 2;
        menuBottomLocation[0] = screenWidth - screenWidth / 5;
        menuBottomLocation[1] = screenHeight / 2;
        menuBottomLocation[2] = screenWidth / 5;
        menuBottomLocation[3] = screenHeight / 2 + 1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
