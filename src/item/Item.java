package item;

public class Item {

    /**
     * A list of all the items
     */
    private static Item[] items;

    /**
     * The next open position in the items array
     */
    private static int itemPosition;

    /**
     * The id of this item
     */
    protected int id;

    public Item() {
        if (items == null) {
            items = new Item[1000];
        }
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
}
