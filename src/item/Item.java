package item;

import game.graphics.ImageManager;
import game.graphics.TextureAtlas;

import java.awt.image.BufferedImage;

public class Item {

    public static TextureAtlas itemAtlas;

    protected int[]  imagePosition;

    protected BufferedImage image;

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

    public Item(String name) {
        this.name = name;

        if (items == null) {
            items = new Item[1000];
            itemAtlas = new TextureAtlas(TextureAtlas.SMALL);
            image = ImageManager.getImage("/items/void_item");
            imagePosition = itemAtlas.addTexture(image);
        }

        this.name = name;
    }

    public void setTexture(String image) {
        this.image = ImageManager.getImage("/items/" + image);
        imagePosition = itemAtlas.addTexture(this.image);
    }

    /**
     * Adds an item to the items array.
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
}
