package level;

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

    public Map(String name) {
        this.name  = name;
        this.tiles = new int[500 * 500];
        this.tileData = new int[500 * 500];
        this.width = 500;
        this.height = 500;
        for (int i = 0; i < this.tiles.length; i++) {
            this.tiles[i] = Tile.grass.getID();
        }
    }

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

    public void setName(String name) {this.name = name;}
}
