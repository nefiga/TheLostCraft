package entity;


import game.Game;
import game.graphics.ImageManager;
import game.graphics.SpriteBatch;
import game.graphics.TextureAtlas;
import item.Item;
import gear.tool.Tool;
import level.Level;

import java.awt.image.BufferedImage;

public class LivingEntity extends Entity {

    public static TextureAtlas livingEntityAtlas;

    protected int[] imagePosition;

    protected BufferedImage image;

    private final int entitySize = 64;

    /**
     * The level this entity is currently in
     */
    Level level;

    /**
     * The tool the entity is currently holding
     */
    protected Tool currentTool;

    /**
     * How much to add to the entities x, y position when the entities interactWith method is called.
     * The amounts correspond with the entities direction, they are in this order NORTH, EAST, SOUTH, WEST.
     */
    protected int[] interactX = new int[]{32, 96, 32, -32};
    protected int[] interactY = new int[]{-32, 32, 96, 32};

    public LivingEntity(int positionX, int positionY) {
        super(positionX, positionY);
        if (livingEntityAtlas == null) {
            livingEntityAtlas = new TextureAtlas(TextureAtlas.LARGE);
        }
        image = ImageManager.getImage("/sprites/void_entity");
        imagePosition = livingEntityAtlas.addTexture("/sprites/void_entity");
        this.width = this.height = entitySize;
    }

    public void setTexture(String image) {
        this.image = ImageManager.getImage("/sprites/" + image);
        imagePosition = livingEntityAtlas.addTexture("/sprites/" + image);
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
    public boolean canCollectItem(Item item) {
        return false;
    }

    public void interactWith() {
        level.interact(this, currentTool, (int) x + interactX[direction], (int) y + interactY[direction], 10, 64);
    }

    /**
     * Attempts to move the entity
     *
     * @param velocityX The amount to be moved on the x axis
     * @param velocityY The amount to be moved on the y axis
     */
    public void move(int velocityX, int velocityY) {
        if (velocityX > 0) direction = EAST;
        if (velocityX < 0) direction = WEST;
        if (velocityY > 0) direction = SOUTH;
        if (velocityY < 0) direction = NORTH;

        int moveX = level.getMaxMoveX(this, velocityX);
        x += moveX;
        shape.move(moveX, 0);

        int moveY = level.getMaxMoveY(this, velocityY);
        y += moveY;
        shape.move(0, moveY);
        rect.translate(moveX, moveY);
    }

    public void render(SpriteBatch batch) {
        batch.draw(x - Game.getXOffset(), y - Game.getYOffset(), imagePosition[0], imagePosition[1], imagePosition[2], imagePosition[3], rotation);
    }

    public void render(SpriteBatch batch, int x, int y) {

    }
}
