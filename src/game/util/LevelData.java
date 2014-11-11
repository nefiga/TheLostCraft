package game.util;

import entity.Player;
import level.Map;

import java.io.Serializable;

public class LevelData implements Serializable {

    private String name;
    // Player Data
    private int playerX, playerY;

    // Map Data
    int[] tiles;
    int[] tileData;
    int mapWidth, mapHeight;

    public LevelData(MapData mapData, Player player) {
        playerX = (int) player.getX();
        playerY = (int) player.getY();
        tiles = mapData.getTiles();
        tileData = mapData.getTileData();
        mapWidth = mapData.getWidth();
        mapHeight = mapData.getHeight();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Player getPlayer() {
        return new Player(playerX, playerY);
    }

    public int[] getTiles() { return tiles;}

    public int[] getTileData() { return tileData; }

    public int getMapWidth() { return mapWidth; }

    public int getMapHeight() { return mapHeight; }

    public void updatePlayer(int x, int y) {
        this.playerX = x;
        this.playerY = y;
    }

    public void updateMap(int[] tiles, int[] tileData, int width, int height) {
        this.tiles = tiles;
        this.tileData = tileData;
        this.mapWidth = width;
        this.mapHeight = height;
    }
}
