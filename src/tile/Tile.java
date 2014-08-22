package tile;

import entity.Entity;
import game.graphics.SpriteBatcher;
import item.tool.Tool;
import level.Level;
import testing.TestTile;

public class Tile {


    public static Tile voidTile = new VoidTile("VoidTile", 0, 1);
    public static Tile water = new WaterTile("Water", 3 ,0);
    public static Tile grass = new GrassTile("Grass", 0, 0);
    public static Tile dirt = new DirtTile("Dirt", 1, 0);
    public static Tile stone = new StoneTile("Stone", 2, 0);

    /**
     * An array of all the tiles
     */
    private static Tile[] tiles;

    /**
     * The position in the tiles array that the next tile will be added
     */
    private static int tilePosition = 0;

    /**
     * The size of the tile in the sprite sheet
     */
    protected static final int tileSize = 64;

    /**
     * The sprites starting position on the sprite sheet
     */
    protected final int xOffset, yOffset;

    protected String name;

    /**
     * The position of the tile in the {@code tiles} array
     */
    protected int id;

    protected int durability;

    /**
     * Creates a new Tile. use the static method {@code addTile} to set the {@code id} of the Tile. Which will also
     * add the tile to the tiles array. Default durability is 1.
     *
     * @param name    The of the Tile
     * @param xOffset Where the sprites starting X position is in the sprite sheet
     * @param yOffset Where the sprites starting Y position is in the sprite sheet
     */
    public Tile(String name, int xOffset, int yOffset) {
        this.name = name;
        this.xOffset = xOffset * tileSize;
        this.yOffset = yOffset * tileSize;
        durability = 1;

        if (tiles == null) {
            tiles = new Tile[1000];
        }
    }

    /**
     * Interacts with this tile
     * @param level The level the tile is in
     * @param entity The entity interacting with the tile
     * @param tool The tool being used
     * @param x The x position of the tile
     * @param y The y position of the tile
     */
    public void interact(Level level, Entity entity, Tool tool, int x, int y) {

    }

    /**
     * Sets whether entities can walk through this tile
     */
    public boolean solid() {
        return false;
    }

    /**
     * Sets whether this tile can be broken
     */
    public boolean brakeable() {
        return false;
    }

    /**
     * Sets the durability of this tile
     */
    public void setDurability(int durability) {
        this.durability = durability;
    }

    /**
     * @return The durability of this tile
     */
    public int getDurability() {
        return durability;
    }

    /**
     * @return id of this tile
     */
    public int getID() {
        return id;
    }

    /**
     * @param id The id of the Tile to be returned
     * @return The Tile associated with the id.
     */
    public static Tile getTile(int id) {
        return tiles[id];
    }


    /**
     * Adds the tile to the {@code tiles} array at the next open position.
     * @param tile The tile to be added
     * @return The position the tile was added to the array
     */
    protected static int addTile(Tile tile) {
        tilePosition++;
        tiles[tilePosition] = tile;
        return tilePosition;
    }

    /**
     * Renders the tiles image at x, y
     * @param batcher The SpriteBatcher this tile will be rendered with
     * @param x The x position on the screen this tile will be rendered at
     * @param y The y position on the screen this tile will be rendered at
     */
    public void render(SpriteBatcher batcher, int x, int y) {
        batcher.draw(x, y, tileSize, tileSize, xOffset, yOffset);
    }
}
