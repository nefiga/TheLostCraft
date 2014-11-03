package entity;

import game.Game;
import game.graphics.SpriteBatch;
import item.Item;

public class ItemEntity extends Entity{

    Item item;

    public ItemEntity(Item item, int positionX, int positionY) {
        super(positionX, positionY);
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

    public void render(SpriteBatch batch) {
        item.render(batch, (int) x - Game.getXOffset(), (int) y - Game.getYOffset());
    }
}
