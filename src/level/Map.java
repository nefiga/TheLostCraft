package level;

import game.graphics.SpriteBatch;
import org.lwjgl.opengl.Display;
import tile.Tile;

import java.io.Serializable;

public class Map implements Serializable{

    private String name;

    // An array of all the tiles on the map
    public int[] tiles;

    // An array that holds the durability of the tiles
    public int[] tileData;

    // Width and height of the map
    public int width, height;

    public Map(String name, int[] tiles, int[] tileData, int w, int h) {
        this.name = name;
        this.tiles = tiles;
        this.tileData = tileData;
        this.width = w;
        this.height = h;
    }

    public String getName() {
        return name;
    }
}
