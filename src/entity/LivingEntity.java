package entity;


import item.Item;
import item.tool.Tool;
import level.Level;

public class LivingEntity extends Entity {

    /**
     * The level this entity is currently in
     */
    Level level;

    protected Tool currentTool;

    protected int[] interactX = new int[]  {32, 96, 32 , -32};
    protected int[] interactY = new int[] {-32, 32, 96, 32};

    public LivingEntity(int positionX, int positionY, int textureX, int textureY, int width, int height) {
        super(positionX, positionY, textureX, textureY, width, height);
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    /**
     * @return True if this entity can collect Items
     */
    public boolean canCollect() {
        return true;
    }

    /**
     * Adds the item to this entities inventory
     *
     * @param item The item to be added to the inventory
     */
    public void collectItem(Item item) {
        // Still need to setup inventory
    }

    public void interactWith() {
        level.interact(this, currentTool, (int) x + interactX[dir], (int) y +interactY[dir], 10, 64);
    }

    /**
     * Attempts to move the entity
     *
     * @param velocityX The amount to be moved on the x axis
     * @param velocityY The amount to be moved on the y axis
     */
    public void move(int velocityX, int velocityY) {
        if (velocityX > 0) dir = 1;
        if (velocityX < 0) dir = 3;
        if (velocityY > 0) dir = 2;
        if (velocityY < 0) dir = 0;

        x += level.getMaxMoveX(this, velocityX, null);
        y += level.getMaxMoveY(this, velocityY, null);
    }

}
