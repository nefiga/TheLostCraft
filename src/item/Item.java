package item;

import collision.shapes.Shape;
import game.graphics.ImageManager;
import game.graphics.Sprite;
import game.graphics.SpriteBatch;
import game.graphics.TextureAtlas;
import item.resource.StoneResource;

import java.awt.image.BufferedImage;

public class Item {

    public static TextureAtlas itemAtlas;

    protected Shape shape;

    protected Sprite imagePosition;

    protected BufferedImage image;

    protected String imageString;

    int itemSize = 32;

    /**
     * A list of all the items
     */
    private static Item[] items;

    protected String name;

    /**
     * The next open position in the items array
     */
    private static int itemPosition;

    /**
     * The id of this item
     */
    protected int id;

    protected int maxStackSize = 64;

    public Item(String name) {
        this.name = name;

        if (items == null) {
            items = new Item[1000];
            itemAtlas = new TextureAtlas(TextureAtlas.MEDIUM);
            image = ImageManager.getImage("/items/void_item");
            imagePosition = itemAtlas.addTexture("/items/void_item");
        }

        this.name = name;
    }

    public void setTexture(String image) {
        // Gear images can't be loaded with this setTexture see the Gear class setTexture
        this.image = ImageManager.getImage("/items/" + image);
        this.imageString = image;
        imagePosition = itemAtlas.addTexture("/items/" + image);
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public void setMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
    }

    public Shape getShape() {
        return shape;
    }

    /**
     * Adds an item to the items array.
     *
     * @param item Item to be added.
     * @return The position of the item in the items array. The items id should be set to this.
     */
    public static int addItem(Item item) {
        itemPosition++;
        items[itemPosition] = item;
        return itemPosition;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Sprite getImagePosition() {
        return imagePosition;
    }

    public String getImageString() {
        return imageString;
    }

    /**
     * @param id The id of the item
     * @return The item at position id in the items array
     */
    public static Item getItem(int id) {
        return items[id];
    }

    /**
     * The id of the item
     */
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public void render(SpriteBatch batch, int x, int y) {
        batch.draw(x, y, imagePosition);
    }

    public void render(SpriteBatch batch, int x, int y, int rotation) {
        batch.draw(x, y, imagePosition, rotation);
    }

    public void render(SpriteBatch batch, int x, int y, int width, int height) {
        batch.draw(x, y, imagePosition, width, height);
    }

    public void render(SpriteBatch batch, int x, int y, int width, int height, int rotation) {
        batch.draw(x, y, imagePosition, width, height, rotation);
    }
}
