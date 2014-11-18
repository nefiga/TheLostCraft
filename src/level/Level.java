package level;

import collision.CollisionDetection;
import collision.shapes.Shape;
import entity.Entity;
import entity.ItemEntity;
import entity.LivingEntity;
import entity.Player;
import game.Game;
import game.Screen;
import game.graphics.*;
import game.util.LevelData;
import gear.tool.Tool;
import item.Item;
import math.Vector2;
import menu.Menu;
import menu.Result;
import menu.SingleComponentMenu;
import menu.component.*;
import menu.component.MenuComponent;
import menu.component.MenuComponent.OnClickListener;
import org.lwjgl.opengl.Display;
import tile.Tile;

import java.awt.*;
import java.awt.Button;
import java.util.ArrayList;
import java.util.List;

public class Level implements Screen, OnClickListener {

    private SpriteBatch tileBatch;
    private SpriteBatch entityBatch;
    private SpriteBatch itemBatch;

    LevelData levelData;

    public static final String NAME = "Level";

    private int[] tiles, tileData;

    private int mapWidth, mapHeight;

    private boolean paused;

    Player player;

    /**
     * A rectangle used to see if a action intersects with any other entities rectangle
     */
    Rectangle interactArea;

    /**
     * A list of all the entities
     */
    private List<Entity> entities = new ArrayList<Entity>();
    private List<ItemEntity> itemEntities = new ArrayList<ItemEntity>();

    /**
     * Every Level should be created with a public static final String "name" variable for a quick and easy way
     * to reference them in the LevelManager class.
     */
    public Level() {
        interactArea = new Rectangle();
    }

    public void loadLevel(LevelData levelData, Player player) {
        this.levelData = levelData;
        tiles = levelData.getTiles();
        tileData = levelData.getTileData();
        mapWidth = levelData.getMapWidth();
        mapHeight = levelData.getMapHeight();
        this.player = player;
        this.player.setLevel(this);
        tileBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(Tile.tileAtlas), 700);
        entityBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(LivingEntity.livingEntityAtlas), 100);
        itemBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(Item.itemAtlas), 100);
    }

    public void loadNewLevel(String name, LevelData levelData, Player player) {
        this.levelData = levelData;
        levelData.setName(name);
        tiles = levelData.getTiles();
        tileData = levelData.getTileData();
        mapWidth = levelData.getMapWidth();
        mapHeight = levelData.getMapHeight();
        this.player = player;
        this.player.setLevel(this);
        tileBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(Tile.tileAtlas), 700);
        entityBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(LivingEntity.livingEntityAtlas), 100);
        itemBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(Item.itemAtlas), 100);
    }

    public void update(long delta) {
        if (!paused) {
            for (int i = 0; i < entities.size(); i++) {
                entities.get(i).update(delta);
                if (entities.get(i).isRemoved()) removeEntity(i);
            }
            for (int i = 0; i < itemEntities.size(); i++) {
                itemEntities.get(i).update(delta);
                if (itemEntities.get(i).isRemoved()) removeItemEntity(i);
            }
        }
        player.update(delta);
    }

    public void render() {
        tileBatch.begin();
        renderTiles();
        tileBatch.end();

        itemBatch.begin();
        renderItems();
        itemBatch.end();

        entityBatch.begin();
        renderEntities();
        entityBatch.end();
    }

    protected void renderEntities() {
        player.render(entityBatch);
    }

    protected void renderItems() {
        for (int i = 0; i < itemEntities.size(); i++) {
            itemEntities.get(i).render(itemBatch);
        }
    }

    /**
     * Adds the entity to this level.
     *
     * @param entity The entity to be added
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void addItemEntity(ItemEntity entity) {
        itemEntities.add(entity);
    }

    /**
     * Removes the entity from this level
     */
    public void removeEntity(int position) {
        entities.remove(position);
    }

    public void removeItemEntity(int position) {
        itemEntities.remove(position);
    }

    /**
     * Tries to interact with an entity at screenX, screenY. If there is no entity it will interact with the tile at screenX, screenY
     *
     * @param entity The interacting entity
     * @param x      The screenX position of the action
     * @param y      The screenY position of the action
     * @param width  The width of the action
     * @param height The height of the action
     * @return True if there was an entity to interact with else return false
     */
    public void interact(Entity entity, Tool tool, int x, int y, int width, int height) {
        interactArea.setBounds(x, y, width, height);
        for (int i = 0; i < entities.size(); i++) {
            Rectangle rect1 = entities.get(i).getRect();
            if (interactArea.intersects(rect1)) {
                entities.get(i).interact(this, entity);
                return;
            }
        }
        getTile(x, y, false).interact(this, player, tool, x, y);
    }

    public void damageTile(int x, int y, int damage) {
        x = Game.pixelToTile(x);
        y = Game.pixelToTile(y);
        tileData[x + y * mapWidth] = Tile.damageTile(damage, getTileData(x, y, true));
    }

    public void replaceTile(Tile tile, int x, int y) {
        x = Game.pixelToTile(x);
        y = Game.pixelToTile(y);
        tiles[x + y * mapWidth] = tile.getID();
        tileData[x + y * mapWidth] = tile.getStartDurability();
    }

    /**
     * Returns the tile at screenX, screenY
     *
     * @param x             The screenX position of the tile
     * @param y             The screenY position of the tile
     * @param tilePrecision True if the screenX, screenY params are in tile precision
     * @return The tile at screenX + screenY * map width in the {@code tiles} array.
     */
    public Tile getTile(int x, int y, boolean tilePrecision) {
        if (!tilePrecision) {
            x = Game.pixelToTile(x);
            y = Game.pixelToTile(y);
        }
        if (x < 0 || y < 0 || x >= mapWidth || y >= mapHeight)
            return Tile.voidTile;

        return Tile.getTile(tiles[x + y * mapWidth]);
    }

    /**
     * @param x             The screenX position of the Tile
     * @param y             The screenY position of the Tile
     * @param tilePrecision True if the screenX, screenY params are in tile precision
     * @return Data for the Tile at screenX + screenY * map width in the {@code tiles}  array
     */
    public int getTileData(int x, int y, boolean tilePrecision) {
        if (!tilePrecision) {
            x = Game.pixelToTile(x);
            y = Game.pixelToTile(y);
        }
        //Player should not be able to access tiles outside of the map
        if (x < 0 || y < 0 || x >= mapWidth || y >= mapHeight)
            return 0;

        return tileData[x + y * mapWidth];
    }

    public int getTileDurability(int x, int y, boolean tilePrecision) {
        if (!tilePrecision) {
            x = Game.pixelToTile(x);
            y = Game.pixelToTile(y);
        }
        return Tile.getDurability(getTileData(x, y, true));
    }

    public int getTileRotation(int x, int y, boolean tilePrecision) {
        return Tile.getRotation(getTileData(x, y, tilePrecision));
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
            // Break out early if player is out of the map bounds
            if (vertex.x < 0 || vertex.y < 0 || vertex.x >= mapWidth * Tile.TILE_SIZE || vertex.y >= mapHeight * Tile.TILE_SIZE) {
                overlap = moveX;
                break;
            }
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
            // Break out early if player is out of the map bounds
            if (vertex.x < 0 || vertex.y < 0 || vertex.x >= mapWidth * Tile.TILE_SIZE || vertex.y >= mapHeight * Tile.TILE_SIZE) {
                overlap = moveY;
                break;
            }
            Tile tile = getTile((int) vertex.x, (int) vertex.y, false);
            if (!tile.solid(0, 0)) continue;
            int collision = (int) CollisionDetection.collision(entityShape, tile.getShape((int) vertex.x, (int) vertex.y)).y;
            if (Math.abs(collision) > Math.abs(overlap)) overlap = collision;
        }
        entityShape.move(0, -moveY);
        return moveY - overlap;
    }

    public void collectItems() {
        Rectangle rect1 = player.getRect();
        for (int i = 0; i < itemEntities.size(); i++) {
            ItemEntity itemEntity = itemEntities.get(i);
            if (rect1.intersects(itemEntity.getRect())) {
                if (player.canCollectItem(itemEntity.getItem()))
                    itemEntities.remove(i);
            }
        }
    }

    @Override
    public void returnResult(Result result) {
    }

    @Override
    public void screenResized(int width, int height) {

    }

    public void onEscapePressed() {
        paused = true;
        VerticalListView view = new VerticalListView(-1, 300, 200, new menu.component.Button("resume", 0, 150, 30), new menu.component.Button("save & exit", 1, 275, 30));
        view.setTopPadding(20);
        view.setSpacing(20);
        view.setOnClickListener(this);
        SingleComponentMenu menu = new SingleComponentMenu(Display.getWidth() / 2 - 150, Display.getHeight() / 2, view);

        Game.openMenu(menu);
    }

    public void save() {
        levelData.updateMap(tiles, tileData, mapWidth, mapHeight);
        levelData.updatePlayer((int) player.getX(), (int) player.getY());
        Game.saveGame(levelData);
    }

    @Override
    public void onLeftClick(MenuComponent c) {
        switch (c.getId()) {
            case 0:
                paused = false;
                Game.closeMenu();
                break;
            case 1:
                Game.closeMenu();
                paused = false;
                Game.closeGame();
                break;
        }
    }

    @Override
    public void onRightClick(MenuComponent c) {

    }
}
