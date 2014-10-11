package tile;

import entity.Entity;
import game.Game;
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

    //Stores the images position in TextureAtlas in this order x, y, width, height
    protected int[] imagePosition;

    protected BufferedImage image;

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
     * Creates a new Tile. use the static method {@code addTile} to set the {@code id} of the Tile. Which will also
     * add the tile to the tiles array. Default durability is 1.
     */
    public Tile(String name) {
        this.name = name;
        durability = 1;

        if (tiles == null) {
            tiles = new Tile[1000];
            tileAtlas = new TextureAtlas(TextureAtlas.SMALL);

            // The default tile image
            image = ImageManager.getImage("/tiles/void_tile");
            imagePosition = tileAtlas.addTexture(image);
        }
    }

    /**
     * Sets the image for this tile. If no image is set the default image will be used
     *
     * @param image The file location of the image.
     */
    public void setImage(String image) {
        this.image = ImageManager.getImage("/tiles/" + image);
        imagePosition = tileAtlas.addTexture(this.image);
    }

    /**
     * Interacts with this tile
     *
     * @param level  The level the tile is in
     * @param entity The entity interacting with the tile
     * @param tool   The tool being used
     * @param x      The x position of the tile
     * @param y      The y position of the tile
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
        tilePosition++;
        tiles[tilePosition] = tile;
        return tilePosition;
    }

    /**
     * Renders the tiles image at x, y
     *
     * @param batch The SpriteBatch used to rendered
     * @param x     The x position on the screen this tile will be rendered at
     * @param y     The y position on the screen this tile will be rendered at
     */
    public void render(SpriteBatch batch, int x, int y) {
        batch.draw(x, y, imagePosition[0], imagePosition[1], imagePosition[2], imagePosition[3]);
    }

    /**
     * @param batch The The SpriteBatch used to rendered
     * @param x The x position of the tile
     * @param y The y position of the tile
     * @param w The width the tile will be rendered at
     * @param h The height the tile will be rendered at
     */
    public void render(SpriteBatch batch, int x, int y, int w, int h) {
        batch.draw(x, y, w, h, imagePosition[0], imagePosition[1], imagePosition[2], imagePosition[3]);
    }
}
