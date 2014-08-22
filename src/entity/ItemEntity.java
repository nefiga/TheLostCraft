package entity;

import item.Item;

public class ItemEntity extends Entity{

    Item item;

    public ItemEntity(Item item, int positionX, int positionY, int textureX, int textureY, int width, int height) {
        super(positionX, positionY, textureX, textureY, width, height);
        this.item = item;
    }

    /**
     * @return True if this entity can be collected
     */
    public boolean collectable() {
        return true;
    }

    /**
     * Returns an item and sets removed to true if this entity is collectible
     *
     * @return The item of this entity
     */
    public Item getItem() {
        remove();
        return item;
    }
}
