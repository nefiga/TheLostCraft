package level;

import game.Game;
import game.graphics.SpriteBatch;
import org.lwjgl.opengl.Display;
import tile.Tile;

public class Map {

    // An array of all the tiles on the map
    public int[] tiles;

    // An array that holds the durability of the tiles
    public int[] tileData;

    // An array of all the tiles visible by the mini map
    public int[] miniMap;

    // Width and height of the minimMap. each pixels equals one tile
    public static final int MINI_WIDTH = 320, MINI_HEIGHT = 256;

    // The offsets in the textureAtlas atlas for the miniMap image
    private int atlasS, atlasT;

    // Offsets used to position the miniMap
    private int offsetX, offsetY;

    // Width and height of the map
    public int width, height;

    public Map(int[] tiles, int[] tileData, int w, int h) {
        this.tiles = tiles;
        this.tileData = tileData;
        this.width = w;
        this.height = h;

        miniMap = new int[MINI_WIDTH * MINI_HEIGHT];
        int[] position = Game.misAtlas.addTexture(miniMap, MINI_WIDTH, MINI_HEIGHT);
        atlasS = position[0];
        atlasT = position[1];
        offsetX = Display.getWidth() - MINI_WIDTH - 20;
        offsetY = Display.getHeight() - MINI_HEIGHT - 20;
    }

    /**
     * Renders the miniMap. Image is stored in the misBatch in the Game class. Each tile is 4 pixels on the mini map.
     *
     * @param startX The x position in the map that the miniMap will start rendering at
     * @param startY The y position in the map that the miniMap will start rendering at
     */
    int timer = 0;

    public void renderMiniMap(SpriteBatch batch, int startX, int startY) {
        timer++;
        // Only update the mini map image every 20 renders, since it takes 21 renders for the player to move 1 tile
        if (timer > 20) {
            timer = 0;
            for (int y = 0; y < MINI_HEIGHT / 2 - 1; y++) {
                for (int x = 0; x < MINI_WIDTH / 2 - 1; x++) {
                    if (startX + x < 0 || startX + x >= width || startY + y < 0 || startY + y >= height) {
                        miniMap[(x * 2) + (y * 2) * MINI_WIDTH] = 0xff000000;
                        miniMap[(x * 2 + 1) + (y * 2) * MINI_WIDTH] = 0xff000000;
                        miniMap[(x * 2) + (y * 2 + 1) * MINI_WIDTH] = 0xff000000;
                        miniMap[(x * 2 + 1) + (y * 2 + 1) * MINI_WIDTH] = 0xff000000;
                    } else {
                        int color = Tile.getTile(tiles[(x + startX) + (y + startY) * width]).getMapColor();
                        miniMap[(x * 2) + (y * 2) * MINI_WIDTH] = color;
                        miniMap[(x * 2 + 1) + (y * 2) * MINI_WIDTH] = color;
                        miniMap[(x * 2) + (y * 2 + 1) * MINI_WIDTH] = color;
                        miniMap[(x * 2 + 1) + (y * 2 + 1) * MINI_WIDTH] = color;
                    }
                }
            }
            batch.subTexture(miniMap, atlasS, atlasT, MINI_WIDTH, MINI_HEIGHT);
        }
        // Still need to draw the minim map ever render
        batch.draw(MINI_WIDTH, MINI_HEIGHT, offsetX, offsetY, atlasS, atlasT);
    }
}
