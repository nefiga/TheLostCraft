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
     * The amounts correspond with the entities direction, they are in this order UP, RIGHT, DOWN, LEFT.
     */
    protected int[] interactX = new int[]{32, 96, 32, -32};
    protected int[] interactY = new int[]{-32, 32, 96, 32};

    public LivingEntity(int positionX, int positionY) {
        super(positionX, positionY);
        if (livingEntityAtlas == null) {
            livingEntityAtlas = new TextureAtlas(TextureAtlas.LARGE);
            image = ImageManager.getImage("/sprites/void_entity");
            imagePosition = livingEntityAtlas.addTexture(image);
        }
        this.width = this.height = entitySize;
    }

    public void setTexture(String image) {
        this.image = ImageManager.getImage("/sprites/" + image);
        imagePosition = livingEntityAtlas.addTexture(this.image);
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
        level.interact(this, currentTool, (int) x + interactX[direction], (int) y + interactY[direction], 10, 64);
    }

    /**
     * Attempts to move the entity
     *
     * @param velocityX The amount to be moved on the x axis
     * @param velocityY The amount to be moved on the y axis
     */
    public void move(int velocityX, int velocityY) {
        if (velocityX > 0) direction = RIGHT;
        if (velocityX < 0) direction = LEFT;
        if (velocityY > 0) direction = DOWN;
        if (velocityY < 0) direction = UP;

        x += level.getMaxMoveX(this, velocityX, null);
        y += level.getMaxMoveY(this, velocityY, null);
    }

    public void render(SpriteBatch batch) {
        batch.draw(x - Game.getXOffset(), y - Game.getYOffset(), imagePosition[0], imagePosition[1], imagePosition[2], imagePosition[3]);
    }

    public void render(SpriteBatch batch, int x, int y) {

    }
}
