package game.util;

import java.io.Serializable;

public class MapData implements Serializable{

    private String name;
    private int[] tiles;
    private int[] tileData;
    private int width, height;

    public MapData(String name, int[] tiles, int[] tileData, int width, int height) {
        this.name = name;
        this.tiles = tiles;
        this.tileData = tileData;
        this.width = width;
        this.height = height;
    }

    public String getName() {return name; }

    public int[] getTiles() {
        return tiles;
    }

    public int[] getTileData() {
        return tileData;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight()  {
        return height;
    }
}
