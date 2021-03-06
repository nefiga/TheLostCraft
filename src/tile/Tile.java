package tile;

import collision.shapes.Shape;
import entity.Entity;
import game.graphics.ImageManager;
import game.graphics.SpriteBatch;
import game.graphics.TextureAtlas;
import gear.tool.Tool;
import level.Level;

import java.awt.image.BufferedImage;

public class Tile {


    public static Tile voidTile = new VoidTile("VoidTile");
    public static Tile emptyTile = new EmptyTile("EmptyTile");
    public static Tile water = new WaterTile("Water");
    public static Tile grass = new GrassTile("Grass");
    public static Tile dirt = new DirtTile("Dirt");
    public static Tile stone = new StoneTile("Stone");

    /**
     * The TextureAtlas for all Tiles
     */
    public static TextureAtlas tileAtlas;

    public static final int TILE_SIZE = 64;

    //Stores the images position in TextureAtlas in this order screenX, screenY, width, height
    protected int[] imagePosition;

    protected BufferedImage image;

    /**
     * An array of all the tiles
     */
    private static Tile[] tiles;

    /**
     * The position in the tiles array that the next tile will be added
     */
    public static int tilePosition;

    protected String name;

    /**
     * The position of the tile in the {@code tiles} array
     */
    protected int id;

    /**
     * The durability of this tile when it is harvested
     */
    protected int durability;

    /**
     * Used for collision detection
     */
    protected Shape shape;

    /**
     * Creates a new Tile. use the static method {@code addTile} to set the {@code id} of the Tile. Which will also
     * add the tile to the tiles array. Default durability is 1.
     */
    public Tile(String name) {
        this.name = name;
        durability = 1;
        shape = null;

        if (tiles == null) {
            tiles = new Tile[1000];
            tileAtlas = new TextureAtlas(TextureAtlas.SMALL);

            // The default tile image
            image = ImageManager.getImage("/tiles/void_tile");
            imagePosition = tileAtlas.addTexture("/tiles/void_tile");
        }
    }

    /**
     * Sets the image for this tile. If no image is set the default image will be used
     *
     * @param image The file location of the image.
     */
    public void setImage(String image) {
        this.image = ImageManager.getImage("/tiles/" + image);
        imagePosition = tileAtlas.addTexture("/tiles/" + image);
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    /**
     * Interacts with this tile
     *
     * @param level  The level the tile is in
     * @param entity The entity interacting with the tile
     * @param tool   The tool being used
     * @param x      The screenX position of the tile
     * @param y      The screenY position of the tile
     */
    public void interact(Level level, Entity entity, Tool tool, int x, int y) {
    }

    /**
     * Sets whether entities can walk through this tile
     */
    public boolean solid(int x, int y) {
        return false;
    }

    /**
     * Sets whether this tile can be broken
     */
    public boolean brakeable() {
        return false;
    }

    public void breakTile(Level level, int x, int y) {

    }

    /**
     * Sets the durability this tile will start with
     */
    public void setStartDurability(int durability) {
        this.durability = durability;
    }

    /**
     * @return The durability this tile will start with
     */
    public int getStartDurability() {
        return durability;
    }

    /**
     * Extracts the durability from the tile data and returns it
     */
    public static int getDurability(int tileData) {
        return tileData & 0xffffff;
    }

    /**
     * Takes the durability and inserts it into the tileData.
     * Then returns the new tileData.
     */
    public static int setDurability(int durability, int tileData) {
        int rotation = (tileData & 0xff000000) >> 24;
        return rotation << 24 | durability;
    }

    public static int damageTile(int damage, int tileData) {
        int rotation = (tileData & 0xff000000) >> 24;
        int pDurability = tileData & 0xffffff;
        if (pDurability - damage <= 0) return rotation << 24;
        return rotation << 24 | pDurability - damage;
    }

    /**
     * Extracts the rotation from the tileData and returns it
     */
    public static int getRotation(int tileData) {
        return (tileData & 0xff000000) >> 24;
    }

    /**
     * Takes the rotation and inserts it into the tileData.
     * Then returns the new tileData
     */
    public static int setRotation(int rotation, int tileData) {
        int durability = tileData & 0xffffff;
        return rotation << 24 | durability;
    }

    /**
     * Changes the rotation clockwise in the tileData by one.
     * Returns the new tileData
     */
    public static int rotateTile(int tileData) {
        int direction = (tileData & 0xff000000) >> 24;
        int durability = tileData & 0xffffff;
        direction++;
        if (direction > 3) direction = 0;
        return direction << 24 | durability;
    }

    /**
     * @return id of this tile
     */
    public int getID() {
        return id;
    }

    public Shape getShape(int x, int y) {
        int xa = x / 64 * 64;
        int ya = y / 64 * 64;
        shape.setPosition(xa, ya);
        return shape;
    }

    public String getName() {
        return name;
    }

    /**
     * Retrieves the tile corresponding to the id from the tiles array
     *
     * @param id The id of the Tile to be returned
     * @return The Tile associated with the id.
     */
    public static Tile getTile(int id) {
        return tiles[id];
    }

    /**
     * Returns an array of all the tiles
     */
    public static Tile[] getTiles() {
        return tiles;
    }


    /**
     * Adds the tile to the {@code tiles} array at the next open position.
     *
     * @param tile The tile to be added
     * @return The position the tile was added to the array
     */
    protected static int addTile(Tile tile) {
        tiles[tilePosition] = tile;
        tilePosition++;
        return tilePosition - 1;
    }

    /**
     * Renders the tiles image at screenX, screenY
     *
     * @param batch The SpriteBatch used to rendered
     * @param x     The screenX position on the screen this tile will be rendered at
     * @param y     The screenY position on the screen this tile will be rendered at
     */
    public void render(SpriteBatch batch, int x, int y) {
        batch.draw(x, y, imagePosition[0], imagePosition[1], imagePosition[2], imagePosition[3]);
    }

    public void render(SpriteBatch batch, int x, int y, int rotation) {
        batch.draw(x, y, imagePosition[0], imagePosition[1], imagePosition[2], imagePosition[3], rotation);
    }

    /**
     * @param batch The The SpriteBatch used to rendered
     * @param x     The screenX position of the tile
     * @param y     The screenY position of the tile
     * @param w     The width the tile will be rendered at
     * @param h     The height the tile will be rendered at
     */
    public void render(SpriteBatch batch, int x, int y, int w, int h) {
        batch.draw(x, y, w, h, imagePosition[0], imagePosition[1], imagePosition[2], imagePosition[3]);
    }

    public void render(SpriteBatch batch, int x, int y, int w, int h, int rotation) {
        batch.draw(x, y, w, h, imagePosition[0], imagePosition[1], imagePosition[2], imagePosition[3], rotation);
    }
}
