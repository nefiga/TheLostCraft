package entity;

import game.Game;
import game.graphics.SpriteBatcher;
import level.Level;

import java.awt.*;

public class Entity {

    /**
     * Rectangle used to track collisions with other entities
     */
    protected Rectangle rect;

    /**
     * True if this entity should be removed from the current level
     */
    protected boolean removed;

    /**
     * True if this entity can be pushed by other entities
     */
    protected boolean moveable;

    /**
     * x, y position on the map
     */
    protected int x, y;

    /**
     * The direction the entity is facing
     */
    public int direction = 0;

    /**
     * The directions the entity can face
     */
    public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;

    /**
     * width and height of this entity
     */
    protected int width, height;

    /**
     * The starting x and y position of the sprite in the sprite sheet
     */
    protected int textureX, textureY;

    /**
     * Creates a new entity
     *
     * @param positionX The x position on the map
     * @param positionY The y position on the map
     * @param textureX  The starting x position of the sprite on the sprite sheet
     * @param textureY  The starting y position of the sprite on the sprite sheet
     * @param width     The width of the sprite in the sprite sheet
     * @param height    The height of the sprite in the sprite sheet
     */
    public Entity(int positionX, int positionY, int textureX, int textureY, int width, int height) {
        this.x = positionX;
        this.y = positionY;
        this.textureX = textureX;
        this.textureY = textureY;
        this.width = width;
        this.height = height;
        rect = new Rectangle(positionX, positionY, width, height);
    }

    public void update(long delta) {

    }

    public void render(SpriteBatcher batcher) {
        batcher.draw(x - Game.getXOffset(), y - Game.getYOffset(), width, height, textureX, textureY);
    }

    /**
     * Sets removed to true
     */
    public void remove() {
        removed = true;
    }

    /**
     * @param level  The level the interacting entity is in
     * @param entity The entity interacting with this entity
     */
    public void interact(Level level, Entity entity) {
    }

    /**
     * Sets whether this entity can be pushed by other entities
     *
     * @param moveable
     */
    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }

    /**
     * @return True if this entity can be pushed by an other entity else return false
     */
    public boolean isMoveable() {
        return moveable;
    }

    /**
     * @return True if this entity is removed
     */
    public boolean isRemoved() {
        return removed;
    }

    /**
     * @param pushingEntity The entity pushing this entity
     * @param moveX         The amount to be pushed
     * @return The amount this entity can be pushed
     */
    public int pushX(Level level, Entity pushingEntity, int moveX) {
        if (moveable && moveX != 0) {
            int moveDistance = level.getMaxMoveX(this, moveX, pushingEntity);
            x += moveDistance;
            return moveDistance;
        }
        return 0;
    }

    /**
     * @param pushingEntity The entity pushing this entity
     * @param moveY         The amount to be pushed
     * @return The amount this entity can be pushed
     */
    public int pushY(Level level, Entity pushingEntity, int moveY) {
        if (moveable && moveY != 0) {
            int moveDistance = level.getMaxMoveY(this, moveY, pushingEntity);
            y += moveDistance;
            return moveDistance;
        }
        return 0;
    }

    public Rectangle getRect() {
        return rect;
    }

    public int getDirection() {
        return direction;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
