package level;

import collision.CollisionDetection;
import collision.shapes.Shape;
import com.sun.prism.ps.Shader;
import entity.Entity;
import entity.ItemEntity;
import entity.LivingEntity;
import entity.Player;
import game.Game;
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

public class Level {

    private SpriteBatch tileBatch;
    private SpriteBatch entityBatch;
    private SpriteBatch misBatch;
    private SpriteBatch menuBatch;

    Map map;

    /**
     * A list of all the tiles in the level
     */
    private int[] tiles;

    /**
     * Holds the durability of each tile
     */
    private int[] tileData;

    LivingEntity player;

    Menu menu;

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
        Map loadMap = (Map) FileIO.loadClass("map1");
        if (loadMap != null) {
            this.map = loadMap;
        }
        else {
            this.map = map;
        }

        this.tiles = this.map.tiles;
        this.tileData = this.map.tileData;
        this.height = this.map.height;
        this.width = this.map.width;
        this.player = player;
        player.setLevel(this);
        interactArea = new Rectangle();
        tileBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(Tile.tileAtlas), 700);
        entityBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE,new Texture(LivingEntity.livingEntityAtlas), 100);
        misBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE,new Texture(Tile.tileAtlas), 1500);

        menu = new Menu(30, 20, 16, "corner", "side", "middle");
        menuBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE,new Texture(Menu.menuAtlas), 1000);
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

        misBatch.begin();
        renderMis();
        misBatch.end();

        menuBatch.begin();
        menu.render(menuBatch, 10, 20);
        menuBatch.end();
    }

    protected void renderMis() {
        map.renderMiniMap(misBatch, Game.pixelToTile((int) player.getX()) - Map.MINI_WIDTH / 2, Game.pixelToTile((int) player.getY()) - Map.MINI_HEIGHT / 2);
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
                getTile(x, y, true).render(tileBatch, Game.tileToPixel(x) - Game.getXOffset(), Game.tileToPixel(y) - Game.getYOffset(), Tile.rotateTile(getTileData(x, y, true)));
            }
        }
    }

    /**
     * @return True if there is a solid tile at x, y else return false
     */
    public boolean tileCollision(int xa, int ya) {
        if (getTile(xa, ya, false).solid(xa, ya)) return true;
        if (getTile(xa + 63, ya, false).solid(xa + 63, ya)) return true;
        if (getTile(xa, ya + 63, false).solid(xa, ya + 63)) return true;
        if (getTile(xa + 63, ya + 63, false).solid(xa + 63, ya + 63)) return true;
        return false;
    }

    public Vector2 getMaxMovement(Entity entity, Vector2 movement) {
        Shape entityShape = entity.getShape();
        for (int i =0; i < entityShape.getVertices().length; i++) {
            Vector2 Vertex = entityShape.getVertices()[i];
            Tile tile = getTile((int) Vertex.x, (int) Vertex.y, false);
            if (!tile.solid(0, 0)) continue;
            return CollisionDetection.collision(entityShape, tile.getShape((int) Vertex.x, (int) Vertex.y));
        }
        return new Vector2(0, 0);
    }
}
