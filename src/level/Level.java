package level;

import entity.Entity;
import entity.ItemEntity;
import entity.LivingEntity;
import entity.Player;
import game.Game;
import game.graphics.SpriteBatcher;
import game.graphics.TextureManager;
import item.tool.Tool;
import org.lwjgl.opengl.Display;
import testing.MoveableEntity;
import tile.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Level {

    /**
     * A SpriteBatcher for Tiles
     */
    SpriteBatcher tileBatch;

    /**
     * A SpriteBatcher for entities
     */
    SpriteBatcher entityBatch;

    /**
     * A list of all the tiles in the level
     */
    private int[] tiles;

    /**
     * Holds the durability of each tile
     */
    private int[] tileData;

    LivingEntity player;

    /**
     * A rectangle used to see if a action intersects with any other entities rectangle
     */
    Rectangle interactArea;

    /**
     * A list of all the entities
     */
    private List<Entity> entities = new ArrayList<Entity>();

    /**
     * Width and height of the tile map
     */
    private int width, height;

    /**
     * Every Level should be created with a public static final String "name" variable for a quick and easy way
     * to reference them in the LevelManager class.
     *
     * @param map    The map object that contains the data this level will use
     * @param player The player
     */
    public Level(Map map, Player player) {
        this.tiles = map.tiles;
        this.tileData = map.tileData;
        this.height = map.height;
        this.width = map.width;
        this.player = player;
        player.setLevel(this);
        tileBatch = new SpriteBatcher(1000, TextureManager.loadTexture("tiles.png"));

        entityBatch = new SpriteBatcher(100, TextureManager.loadTexture("normal_sprites.png"));
        interactArea = new Rectangle();
        addEntity(new MoveableEntity(100, 100, 64, 0, 64, 64));
    }

    public void update(long delta) {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).update(delta);
        }
        player.update(delta);
    }

    public void render() {
        tileBatch.begin();
        renderTiles();
        tileBatch.end();

        entityBatch.begin();
        renderEntities();
        entityBatch.end();
    }

    protected void renderEntities() {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).render(entityBatch);
        }
        player.render(entityBatch);
    }

    /**
     * Adds the entity to this level. Also sets the entities current level to this
     *
     * @param entity The entity to be added
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    /**
     * Removes the entity from this level and sets its current level to null
     *
     * @param entity The entity to be removed from this level
     */
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    /**
     * Tries to interact with an entity at x, y. If there is no entity interacts with the tile at x, y
     *
     * @param entity The interacting entity
     * @param x      The x position of the action
     * @param y      The y position of the action
     * @param width  The width of the action
     * @param height The height of the action
     * @return True if there was an entity to interact with else return false
     */
    public void interact(Entity entity, Tool tool, int x, int y, int width, int height) {
        int centerX = x - width / 2;
        int centerY = y - height / 2;
        interactArea.setBounds(centerX, centerY, width, height);
        for (int i = 0; i < entities.size(); i++) {
            Rectangle rect1 = entities.get(i).getRect();
            if (interactArea.intersects(rect1)) {
                entities.get(i).interact(this, entity);
                // If there is a entity method will return without interacting with the tile under the entity
                return;
            }
        }
        getTile(x, y, false).interact(this, entity, tool, x, y);
    }

    /**
     * Returns the tile at x, y
     *
     * @param x             The x position of the tile
     * @param y             The y position of the tile
     * @param tilePrecision True if the x, y params are in tile precision
     * @return The tile at x + y * map width in the {@code tiles} array.
     */
    public Tile getTile(int x, int y, boolean tilePrecision) {
        if (!tilePrecision) {
            x = Game.pixelToTile(x);
            y = Game.pixelToTile(y);
        }
        if (x < 0 || y < 0 || x >= width || y >= height)
            return Tile.voidTile;

        return Tile.getTile(tiles[x + y * width]);
    }

    /**
     * @param x             The x position of the Tile
     * @param y             The y position of the Tile
     * @param tilePrecision True if the x, y params are in tile precision
     * @return Data for the Tile at x + y * map width in the {@code tiles}  array
     */
    public int getTileData(int x, int y, boolean tilePrecision) {
        if (!tilePrecision) {
            x = Game.pixelToTile(x);
            y = Game.pixelToTile(y);
        }
        //Player should not be able to access tiles outside of the map
        if (x < 0 || y < 0 || x >= width || y >= height)
            return 0;

        return tileData[x + y * width];
    }

    /**
     * Renders all the visible tiles
     */
    protected void renderTiles() {
        int startX = Game.pixelToTile(Game.getXOffset()) - 1;
        int endX = Game.pixelToTile(Display.getWidth() + Game.getXOffset()) + 1;
        int startY = Game.pixelToTile(Game.getYOffset()) - 1;
        int endY = Game.pixelToTile(Display.getHeight() + Game.getYOffset()) + 1;
        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                getTile(x, y, true).render(tileBatch, Game.tileToPixel(x) - Game.getXOffset(), Game.tileToPixel(y) - Game.getYOffset());
            }
        }
    }

    /**
     * @return True if there is a solid tile at x, y else return false
     */
    public boolean tileCollision(int xa, int ya) {
        if (getTile(xa, ya, false).solid()) return true;
        if (getTile(xa + 63, ya, false).solid()) return true;
        if (getTile(xa, ya + 63, false).solid()) return true;
        if (getTile(xa + 63, ya + 63, false).solid()) return true;
        return false;
    }

    /**
     * Calculates the max distance that can be moved without colliding with any solid entities or tiles
     *
     * @param entity        The entity attempting to move
     * @param moveX         The desired amount to be moved in the x axis
     * @param excludeEntity An entity to be excluded from collision detection
     * @return The amount the entity can move with out colliding with a solid entity or tile
     */
    public int getMaxMoveX(Entity entity, int moveX, Entity excludeEntity) {
        int distance = moveX;
        int push = moveX;

        // Checking Tile collision
        int xa = (int) entity.getX() + distance;
        int ya = (int) entity.getY();

        while (tileCollision(xa, ya)) {
            push = distance = (int) Math.nextAfter(distance, 0);
            xa = (int) entity.getX() + distance;
        }


        // Checking Rectangle collision
        Rectangle rect = entity.getRect();
        // Moves the Rectangle prior to check if there are any collisions.
        rect.translate(distance, 0);

        for (int i = 0; i < entities.size(); i++) {
            // Skips checks on its self and the exclude entity.
            if (entities.get(i) == entity || entities.get(i) == excludeEntity) continue;
            Entity entity1 = entities.get(i);
            Rectangle rect1 = entity1.getRect();

            if (rect.intersects(rect1)) {
                // Tries to collect the entity
                if (entity instanceof LivingEntity && entity1 instanceof ItemEntity) {
                    if (((LivingEntity) entity).canCollect() && ((ItemEntity) entity1).collectable()) {
                        ((LivingEntity) entity).collectItem(((ItemEntity) entity1).getItem());
                        removeEntity(entity1);
                        continue;
                    }
                }

                while (rect.intersects(rect1)) {
                    distance = (int) Math.nextAfter(distance, 0);
                    rect.setLocation((int) entity.getX() + distance, (int) entity.getY());
                }

                if (entity1.isMoveable()) {
                    distance = entity1.pushX(this, entity, push);
                }

                rect.setLocation((int) entity.getX() + distance, (int) entity.getY());
            }
        }

        return distance;
    }

    /**
     * Calculates the max distance that can be moved without colliding with any solid entities or tiles
     *
     * @param entity        The entity attempting to move
     * @param moveY         The desired amount to be moved in the Y axis
     * @param excludeEntity An entity to be excluded from collision detection
     * @return The amount the entity can move with out colliding with a solid entity or tile
     */
    public int getMaxMoveY(Entity entity, int moveY, Entity excludeEntity) {
        int distance = moveY;
        int push = moveY;

        // Checking Tile collision
        int xa = (int) entity.getX();
        int ya = (int) entity.getY() + distance;

        while (tileCollision(xa, ya)) {
            push = distance = (int) Math.nextAfter(distance, 0);
            ya = (int) entity.getY() + distance;
        }


        // Checking Rectangle collision
        Rectangle rect = entity.getRect();
        // Moves the Rectangle prior to check if there are any collisions.
        rect.translate(0, distance);

        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i) == entity || entities.get(i) == excludeEntity) continue;
            Entity entity1 = entities.get(i);
            Rectangle rect1 = entity1.getRect();

            if (rect.intersects(rect1)) {

                // Tries to collect the entity
                if (entity instanceof LivingEntity && entity1 instanceof ItemEntity) {
                    if (((LivingEntity) entity).canCollect() && ((ItemEntity) entity1).collectable()) {
                        ((LivingEntity) entity).collectItem(((ItemEntity) entity1).getItem());
                        removeEntity(entity1);
                        continue;
                    }
                }

                while (rect.intersects(rect1)) {
                    distance = (int) Math.nextAfter(distance, 0);
                    rect.setLocation((int) entity.getX(), (int) entity.getY() + distance);
                }

                if (entity1.isMoveable()) {
                    distance = entity1.pushY(this, entity, push);
                }

                rect.setLocation((int) entity.getX(), (int) entity.getY() + distance);
            }
        }

        return distance;
    }
}
