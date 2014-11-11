package level;

import game.graphics.ShaderManager;
import game.graphics.SpriteBatch;
import game.graphics.Texture;
import org.lwjgl.opengl.Display;
import tile.Tile;

public class MiniMap {

    private SpriteBatch misBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(Tile.tileAtlas), 1500);

    // An array of all the tiles on the map
    public int[] tiles;

    // An array that holds the durability of the tiles
    public int[] tileData;

    // Width and height of the minimMap. each pixels equals one tile
    public static final int MINI_WIDTH = 50, MINI_HEIGHT = 30;

    // Width and height of the map
    public int width, height;

    // Location of the minim map on the screen
    int mapXLocation = Display.getWidth() - MINI_WIDTH * 8;

    // Location of the mini map on the screen
    int mapYLocation = Display.getHeight() - MINI_HEIGHT * 8;

    public MiniMap(int[] tiles, int[] tileData, int width, int height) {
        this.tiles = tiles;
        this.tileData = tileData;
        this.width = width;
        this.height = height;
    }

    /**
     * Renders the miniMap. Image is stored in the misBatch in the Game class. Each tile is 8 pixels on the mini map.
     *
     * @param startX The x position in the map that the miniMap will start rendering at
     * @param startY The y position in the map that the miniMap will start rendering at
     */

    public void renderMiniMap(int startX, int startY) {
     misBatch.begin();
        for (int y = 0; y < MINI_HEIGHT; y++) {
            for (int x = 0; x < MINI_WIDTH; x++) {
                if (startX + x < 0 || startX + x >= width || startY + y < 0 || startY + y >= height) {
                    Tile.voidTile.render(misBatch, mapXLocation + x * 8, mapYLocation + y * 8, 8, 8);
                } else {
                    Tile.getTile(tiles[(x + startX) + (y + startY) * width]).render(misBatch, mapXLocation + x * 8, mapYLocation + y * 8, 8, 8, Tile.getRotation(tileData[(x + startX) + (y + startY) * width]));
                }
            }
        }
        misBatch.end();
    }
}
