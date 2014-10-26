package level;

import collision.CollisionDetection;
import collision.shapes.Shape;
import com.sun.prism.ps.Shader;
import entity.Entity;
import entity.ItemEntity;
import entity.LivingEntity;
import entity.Player;
import game.Game;
import game.Screen;
import game.graphics.*;
import game.util.FileIO;
import gear.tool.Tool;
import math.Vector2;
import menu.Menu;
import org.lwjgl.opengl.Display;
import tile.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Level implements game.Screen {

    private SpriteBatch tileBatch;
    private SpriteBatch entityBatch;


    Map map;
    MiniMap miniMap;

    public static final String NAME = "Level";

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
     * Every Level should be created with a public static final String "name" variable for a quick and easy way
     * to reference them in the LevelManager class.
     */
    public Level() {
        interactArea = new Rectangle();
        tileBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(Tile.tileAtlas), 700);
        entityBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(LivingEntity.livingEntityAtlas), 100);
    }

    public void loadLevel(Map map, Player player) {
        this.map = map;
        this.player = player;
        miniMap = new MiniMap(this.map);
        player.setLevel(this);
    }

    public void update(long delta) {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).update(delta);
            if (entities.get(i).isRemoved()) removeEntity(entities.get(i));
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

        miniMap.renderMiniMap(Game.pixelToTile((int) player.getX()) - MiniMap.MINI_WIDTH / 2, Game.pixelToTile((int) player.getY()) - MiniMap.MINI_HEIGHT / 2);
    }

    protected void renderEntities() {
        player.render(entityBatch);
    }

    /**
     * Adds the entity to this level.
     *
     * @param entity The entity to be added
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    /**
     * Removes the entity from this level
     *
     * @param entity The entity to be removed from this level
     */
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    /**
     * Tries to interact with an entity at x, y. If there is no entity it will interact with the tile at x, y
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
                // NO reason to interact with tile if there is an entity to interact with
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
        if (x < 0 || y < 0 || x >= map.width || y >= map.height)
            return Tile.voidTile;

        return Tile.getTile(map.tiles[x + y * map.width]);
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
        if (x < 0 || y < 0 || x >= map.width || y >= map.height)
            return 0;

        return map.tileData[x + y * map.width];
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
                getTile(x, y, true).render(tileBatch, Game.tileToPixel(x) - Game.getXOffset(), Game.tileToPixel(y) - Game.getYOffset(), Tile.getRotation(getTileData(x, y, true)));
            }
        }
    }

    public int getMaxMoveX(Entity entity, int moveX) {
        Shape entityShape = entity.getShape();
        entityShape.move(moveX, 0);
        int overlap = 0;
        for (int i = 0; i < entityShape.getVertices().length; i++) {
            Vector2 vertex = entityShape.getVertices()[i];
            Tile tile = getTile((int) vertex.x, (int) vertex.y, false);
            if (!tile.solid(0, 0)) continue;
            int collision = (int) CollisionDetection.collision(entityShape, tile.getShape((int) vertex.x, (int) vertex.y)).x;
            if (Math.abs(collision) > Math.abs(overlap)) overlap = collision;
        }
        entityShape.move(-moveX, 0);
        return moveX - overlap;
    }

    public int getMaxMoveY(Entity entity, int moveY) {
        Shape entityShape = entity.getShape();
        entityShape.move(0, moveY);
        int overlap = 0;
        for (int i = 0; i < entityShape.getVertices().length; i++) {
            Vector2 vertex = entityShape.getVertices()[i];
            Tile tile = getTile((int) vertex.x, (int) vertex.y, false);
            if (!tile.solid(0, 0)) continue;
            int collision = (int) CollisionDetection.collision(entityShape, tile.getShape((int) vertex.x, (int) vertex.y)).y;
            if (Math.abs(collision) > Math.abs(overlap)) overlap = collision;
        }
        entityShape.move(0, -moveY);
        return moveY - overlap;
    }
}
