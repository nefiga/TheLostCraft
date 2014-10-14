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

    private int[] menuTop, menuBottom, currentTile;

    private int[] tiles;
    private int[] tileData;

    private int tilePageSize;

    private int[] pageX = new int[3];
    private int[] pageY = new int[8];

    private int selectTileX, selectTileY;
    private Tile inHand = Tile.voidTile;

    private int[][] tilePage;

    private int screenWidth, screenHeight;

    private int[] menuTopLocation = new int[4];
    private int[] menuBottomLocation = new int[4];

    public static final int X1 = 16, X2 = 32, X3 = 64;

    private int zoom = X3;

    private int page = 0, pages;

    private int x = 1000, y = 1000;

    public Editor() {

        tiles = new int[500 * 500];
        tileData = new int[500 * 500];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = Tile.emptyTile.getID();
            tileData[i] = 0;
        }
        init();
    }

    public Editor(Map map) {
        tiles = map.tiles;
        tileData = map.tileData;
        init();
    }

    private void init() {
        loadTileList();
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

    public void update() {
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
    }

    public void renderMenuBackground() {
        editorBatch.draw(menuTopLocation[0], menuTopLocation[1], menuTopLocation[2], menuTopLocation[3], menuTop[0], menuTop[1], menuTop[2], menuTop[3]);
        editorBatch.draw(menuBottomLocation[0], menuBottomLocation[1], menuBottomLocation[2], menuBottomLocation[3], menuBottom[0], menuBottom[1], menuBottom[2], menuBottom[3]);
    }

    private void renderMenu() {
        editorBatch.draw(pageX[selectTileX], pageY[selectTileY], tilePageSize, tilePageSize, currentTile[0], currentTile[1], currentTile[2], currentTile[3]);
    }

    public void renderSelectableTiles() {
            for (int y = 0; y < pageY.length; y++) {
                for (int x = 0; x < pageX.length; x++) {
                    if (x + y * 3 >= tilePage[page].length) continue;
                    Tile.getTile(tilePage[page][x + y * 3]).render(tileBatch, pageX[x], pageY[y], tilePageSize, tilePageSize);
                }
            }
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

    public int getTileData(int x, int y, boolean tilePrecision) {
        if (!tilePrecision) {
            x = x / zoom;
            y = y / zoom;
        }
        if (x < 0 || y < 0 || x >= 500 || y >= 500)
            return 0;

        return tileData[x + y * 500];
    }


    protected void renderTiles() {
        int startX = Game.getXOffset() / zoom - 1;
        int endX = (Display.getWidth() + Game.getXOffset()) / zoom + 1;
        int startY = Game.getYOffset() / zoom - 1;
        int endY = (Display.getHeight() + Game.getYOffset()) / zoom + 1;
        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                getTile(x, y, true).render(tileBatch, x * zoom - Game.getXOffset(), y * zoom - Game.getYOffset(), zoom, zoom, (getTileData(x, y, true) & 0xff00) >> 8);
            }
        }
    }

    private void createTextureAtlas() {
        menuTop = editorAtlas.addTexture(ImageManager.getImage("/editor/menu_top"));
        menuBottom = editorAtlas.addTexture(ImageManager.getImage("/editor/menu_bottom"));
        currentTile = editorAtlas.addTexture(ImageManager.getImage("/editor/current_tile"));
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

    // Rotates the tile
    public void shiftClick(int x, int y) {
        // invert mouse y position
        y = Math.abs(y - Display.getHeight());
        x = (x + Game.getXOffset()) / zoom;
        y = (y + Game.getYOffset()) / zoom;
        if (x + y * 500 >= 0 && x + y * 500 < tiles.length) {
            int t = tileData[x + y * 500];
            int durability = (t & 0xff);
            int rotation = Tile.rotateTile((t & 0xff00) >> 8);
            tileData[x + y *  500] = rotation << 8 | durability;
        }
    }

    public void controlClick(int x, int y) {
        // invert mouse y position
        y = Math.abs(y - Display.getHeight());
        x = (x + Game.getXOffset()) / zoom;
        y = (y + Game.getYOffset()) / zoom;
        if (x + y * 500 >= 0 && x + y * 500 < tiles.length) inHand = Tile.getTile(tiles[x + y * 500]);
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
                        selectTileX = xp;
                        selectTileY = yp;
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

    private void clickScreen(int button, int x, int y) {
        // Left click
        if (button == 0) {
            x = (x + Game.getXOffset()) / zoom;
            y = (y + Game.getYOffset()) / zoom;
            if (x + y * 500 >= 0 && x + y * 500 < tiles.length) {
                tiles[x + y * 500] = inHand.getID();
                tileData[x + y * 500] = inHand.getDurability();
            }
        }
        // Right click
        else {
            x = (x + Game.getXOffset()) / zoom;
            y = (y + Game.getYOffset()) / zoom;
            if (x + y * 500 >= 0 && x + y * 500 < tiles.length) {
                tiles[x + y * 500] = Tile.emptyTile.getID();
                tileData[x + y * 500] = inHand.getDurability();
            }
        }
    }

    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void updateScreenSize(int width, int height) {
        screenWidth = width;
        screenHeight = height;
        int oneFifth = screenWidth / 5;
        int oneHalf = screenHeight / 2;

        menuTopLocation[0] = screenWidth - oneFifth;
        menuTopLocation[1] = 0;
        menuTopLocation[2] = oneFifth;
        menuTopLocation[3] = oneHalf;
        menuBottomLocation[0] = screenWidth - oneFifth;
        menuBottomLocation[1] = oneHalf;
        menuBottomLocation[2] = oneFifth;
        menuBottomLocation[3] = oneHalf + 1;
        tilePageSize = oneFifth / 4;
        for (int x = 0; x < 3; x++) {
            pageX[x] = (screenWidth - oneFifth) + (tilePageSize + tilePageSize / 3) * x + (tilePageSize / 3 / 2);
        }
        for (int y = 0; y < 8; y++) {
            pageY[y] = (tilePageSize + tilePageSize / 8) * y + 20;
        }

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
