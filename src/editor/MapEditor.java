package editor;

import game.Game;
import game.Screen;
import game.fonts.Font;
import game.graphics.*;
import game.util.FileIO;
import level.Map;
import org.lwjgl.opengl.Display;
import tile.Tile;

public class MapEditor implements Screen{

    SpriteBatch editorBatch, tileBatch;
    TextureAtlas editorAtlas;
    EditorMap editorMap;
    Map map;

    public static final String NAME = "Editor";

    private int[] menu;

    private int[] tiles;
    private int[] tileData;

    private int tilePageSize;

    private int[] pageX = new int[3];
    private int[] pageY = new int[8];

    private Tile inHand = Tile.voidTile;
    private int currentTileX, currentTileY = 30;

    private int[][] tilePage;

    private int screenWidth, screenHeight;

    private int[] menuBackgroundLocation = new int[4];

    public static final int X1 = 16, X2 = 32, X3 = 64;

    private int zoom = X3;

    private int page = 0, pages;

    private int x = 1000, y = 1000;

    private int chunkSize = 500;

    public void loadEditor(Map map) {
        this.map = map;
        this.tiles = map.tiles;
        this.tileData = map.tileData;
        init();
    }

    private void init() {
        loadTileList();
        editorAtlas = new TextureAtlas(TextureAtlas.MEDIUM);
        editorMap = new EditorMap();
        createTextureAtlas();
        tileBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(Tile.tileAtlas), 1500);
        editorBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(editorAtlas), 100);
        screenWidth = Display.getWidth();
        screenHeight = Display.getHeight();
        updateScreenSize(screenWidth, screenHeight);
    }

    private void loadTileList() {
        Tile[] tiles = Tile.getTiles();

        if (Tile.tilePosition < 30) {
            pages = 1;
            tilePage = new int[pages][Tile.tilePosition];

            for (int t = 0; t < Tile.tilePosition; t++) {
                tilePage[0][t] = tiles[t].getID();
            }
        } else {
            pages = tiles.length / 30;
        }
    }

    public void update(long delta) {

    }

    public void render() {
        tileBatch.begin();
        renderTiles();
        tileBatch.end();

        editorBatch.begin();
        renderMenuBackground();
        editorBatch.end();

        tileBatch.begin();
        renderSelectableTiles();
        tileBatch.end();

        editorBatch.begin();
        renderMenu();
        editorBatch.end();

        Font.generalFont.begin();
        Font.generalFont.DrawString("will a long text work", 100, 100);
        Font.generalFont.DrawString("testing a second string!", 100, 120);
        Font.generalFont.DrawString("we have some big problems!!!!!", 100, 140);
        Font.generalFont.end();
    }

    public void renderMenuBackground() {
        editorBatch.draw(menuBackgroundLocation[0], menuBackgroundLocation[1], menuBackgroundLocation[2], menuBackgroundLocation[3], menu[0], menu[1], menu[2], menu[3]);
    }

    private void renderMenu() {

    }

    public void renderSelectableTiles() {
        for (int y = 0; y < pageY.length; y++) {
            for (int x = 0; x < pageX.length; x++) {
                if (x + y * 3 >= tilePage[page].length) continue;
                Tile.getTile(tilePage[page][x + y * 3]).render(tileBatch, pageX[x], pageY[y], tilePageSize, tilePageSize);
            }
        }
        inHand.render(tileBatch, currentTileX, currentTileY, Tile.TILE_SIZE, Tile.TILE_SIZE);
    }

    protected void renderTiles() {
        int startX = Game.getXOffset() / zoom - 1;
        int endX = (Display.getWidth() + Game.getXOffset()) / zoom + 1;
        int startY = Game.getYOffset() / zoom - 1;
        int endY = (Display.getHeight() + Game.getYOffset()) / zoom + 1;
        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                getTile(x, y, true).render(tileBatch, x * zoom - Game.getXOffset(), y * zoom - Game.getYOffset(), zoom, zoom, Tile.getRotation(getTileData(x, y, true)));
            }
        }
    }

    public Tile getTile(int x, int y, boolean tilePrecision) {
        if (!tilePrecision) {
            x = x / zoom;
            y = y / zoom;
        }
        if (x < 0 || y < 0 || x >= chunkSize || y >= chunkSize)
            return Tile.voidTile;

        return Tile.getTile(tiles[x + y * chunkSize]);
    }

    public int getTileData(int x, int y, boolean tilePrecision) {
        if (!tilePrecision) {
            x = x / zoom;
            y = y / zoom;
        }
        if (x < 0 || y < 0 || x >= chunkSize || y >= chunkSize)
            return 0;

        return tileData[x + y * chunkSize];
    }

    private void createTextureAtlas() {
        menu = editorAtlas.addTexture(ImageManager.getImage("/editor/menu_top"));
    }

    public void updateScreenSize(int width, int height) {
        screenWidth = width;
        screenHeight = height;
        int tileMenuWidth = Tile.TILE_SIZE * 4;

        menuBackgroundLocation[0] = screenWidth - tileMenuWidth;
        menuBackgroundLocation[1] = 0;
        menuBackgroundLocation[2] = tileMenuWidth;
        menuBackgroundLocation[3] = height;
        tilePageSize = Tile.TILE_SIZE;
        currentTileX = width - (int) (Tile.TILE_SIZE * 2.5);
        for (int x = 0; x < 3; x++) {
            pageX[x] = (screenWidth - tileMenuWidth) + (tilePageSize + tilePageSize / 3) * x + (tilePageSize / 3 / 2);
        }
        for (int y = 0; y < 8; y++) {
            pageY[y] = (tilePageSize + tilePageSize / 8) * y + Tile.TILE_SIZE + 64;
        }

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Saves the current map and returns a String of where the map was saved.
     */
    public String save() {
        FileIO.SaveClass(map.getName(), map);
        return map.getName();
    }

    //-------------------------
    // INPUT
    //------------------------

    private void clickScreen(int button, int x, int y) {
        // Left click
        if (button == 0) {
            x = (x + Game.getXOffset()) / zoom;
            y = (y + Game.getYOffset()) / zoom;
            if (x + y * chunkSize >= 0 && x + y * chunkSize < tiles.length) {
                tiles[x + y * chunkSize] = inHand.getID();
                tileData[x + y * chunkSize] = Tile.getDurability(tileData[x + y * chunkSize]);
            }
        }
        // Right click
        else {
            x = (x + Game.getXOffset()) / zoom;
            y = (y + Game.getYOffset()) / zoom;
            if (x + y * chunkSize >= 0 && x + y * chunkSize < tiles.length) {
                tiles[x + y * chunkSize] = Tile.emptyTile.getID();
                tileData[x + y * chunkSize] = -1;
            }
        }
    }

    // Rotates the tile
    public void shiftClick(int x, int y) {
        // invert mouse y position
        y = Math.abs(y - Display.getHeight());
        x = (x + Game.getXOffset()) / zoom;
        y = (y + Game.getYOffset()) / zoom;
        if (x + y * chunkSize >= 0 && x + y * chunkSize < tiles.length) {
            tileData[x + y * chunkSize] = Tile.rotateTile(tileData[x + y * chunkSize]);
        }
    }

    public void controlClick(int x, int y) {
        // invert mouse y position
        y = Math.abs(y - Display.getHeight());
        x = (x + Game.getXOffset()) / zoom;
        y = (y + Game.getYOffset()) / zoom;
        if (x + y * chunkSize >= 0 && x + y * chunkSize < tiles.length) inHand = Tile.getTile(tiles[x + y * chunkSize]);
    }

    public void leftClick(int x, int y) {
        // invert mouse y position
        y = Math.abs(y - Display.getHeight());
        if (x < screenWidth / 5 * 4) clickScreen(0, x, y);
        else {
            for (int yp = 0; yp < pageY.length; yp++) {
                for (int xp = 0; xp < pageX.length; xp++) {
                    if (x > pageX[xp] && x < pageX[xp] + tilePageSize && y > pageY[yp] && y < pageY[yp] + tilePageSize) {
                        if (xp + yp * 3 >= tilePage[page].length) continue;
                        inHand = Tile.getTile(tilePage[page][xp + yp * 3]);
                    }
                }
            }
        }
    }

    public void rightClick(int x, int y) {
        // invert mouse y position
        y = Math.abs(y - Display.getHeight());
        if (x < screenWidth / 5 * 4) clickScreen(1, x, y);
    }

    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void zoomIn() {
        int tempX = x / zoom;
        int tempY = y / zoom;
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
        x = tempX * zoom;
        y = tempY * zoom;
    }

    public void zoomOut() {
        int tempX = x / zoom;
        int tempY = y / zoom;
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
        x = tempX * zoom;
        y = tempY * zoom;
    }

    public void nextPage() {
        page++;
        if (page > pages) page = 0;
    }

    public void previousPage() {
        page--;
        if (page < 0) page = pages;
    }
}
