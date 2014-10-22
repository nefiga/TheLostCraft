package level;

import tile.Tile;

import java.util.Random;

public class RandomMapGenerator {

    private static int[] tiles;

    private static int[] tileData;

    static Random r = new Random();

    public static Map generateMap(int size) {
        tiles = new int[size * size];
        tileData = new int[size * size];
        generateGrass();
        generateDirt();
        generateStone();
        generateWater();

        return new Map("Map1", tiles, tileData, size, size);
    }

    private static void generateDirt() {
        for (int i = 0; i < tiles.length; i++) {
            int place = r.nextInt(10);
            if (place == 1) {
                tiles[i] = Tile.dirt.getID();
                tileData[i] = Tile.dirt.getDurability();
            }
        }
    }

    private static void generateStone() {
        for (int i = 0; i < tiles.length; i++) {
            int place = r.nextInt(15);
            if (place == 1) {
                tiles[i] = Tile.stone.getID();
                tileData[i] = Tile.stone.getDurability();
            }
        }
    }

    private static void generateGrass() {
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = Tile.grass.getID();
            tileData[i] = Tile.grass.getDurability();
        }
    }

    private static void generateWater() {
        for (int i= 0; i < tiles.length; i++){
            int place = r.nextInt(10);
            if (place == 1) {
                tiles[i] = Tile.water.getID();
                tileData[i] = Tile.water.getDurability();
            }
        }
    }
}
