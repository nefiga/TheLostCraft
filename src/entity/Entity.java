package entity;

import collision.shapes.Shape;
import game.graphics.SpriteBatch;
import level.Level;

import java.awt.Rectangle;


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
    protected float x, y;

    /**
     * The direction the entity is facing
     */
    public int direction = 0;

    /**
     * The rotation to be put on the entity image when drawn
     */
    protected int rotation = SpriteBatch.NO_ROTATE;

    /**
     * The directions the entity can face
     */
    public static final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;

    /**
     * width and height of this entity
     */
    protected int width, height;

    protected Shape shape;

    /**
     * Creates a new entity
     *
     * @param positionX The x position on the map
     * @param positionY The y position on the map
     */
    public Entity(int positionX, int positionY) {
        this.x = positionX;
        this.y = positionY;
        rect = new Rectangle(positionX, positionY, width, height);
    }

    public void update(long delta) {

    }

    /**
     * Renders the entity with the SpriteBatch. They entities position will be decided by the entity
     */
    public void render(SpriteBatch batch) {

    }

    /**
     * Renders the entity with the SpriteBatch at x, y
     */
    public void render(SpriteBatch batch, int x, int y) {

    }

    public void setShape(Shape shape) {
        this.shape = shape;
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

    public Shape getShape() {
        return shape;
    }

    /**
     * @param pushingEntity The entity pushing this entity
     * @param moveX         The amount to be pushed
     * @return The amount this entity can be pushed
     */
    /*public int pushX(Level level, Entity pushingEntity, int moveX) {
        if (moveable && moveX != 0) {
            int moveDistance = level.getMaxMoveX(this, moveX, pushingEntity);
            x += moveDistance;
            shape.move(moveDistance, 0);
            return moveDistance;
        }
        return 0;
    }*/

    /**
     * @param pushingEntity The entity pushing this entity
     * @param moveY         The amount to be pushed
     * @return The amount this entity can be pushed
     */
    /*public int pushY(Level level, Entity pushingEntity, int moveY) {
        if (moveable && moveY != 0) {
            int moveDistance = level.getMaxMoveY(this, moveY, pushingEntity);
            y += moveDistance;
            shape.move(0, moveDistance);
            return moveDistance;
        }
        return 0;
    }*/

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
