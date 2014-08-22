package level;

public class Map {

    public int[] tiles;

    public int[] tileData;

    public int width, height;

    public Map(int[] tiles, int[] tileData,  int w, int h) {
        this.tiles = tiles;
        this.tileData = tileData;
        this.width = w;
        this.height = h;
    }
}
