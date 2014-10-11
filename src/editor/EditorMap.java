package editor;

import game.Game;
import game.graphics.SpriteBatch;
import org.lwjgl.opengl.Display;
import tile.Tile;

public class EditorMap {

    private int[] tiles;

    private int zoom;

    public void render(SpriteBatch spriteBatch, int zoom, int[] tiles) {
        this.tiles = tiles;
        this.zoom = zoom;
        renderTiles(spriteBatch);
    }

    public Tile getTile(int x, int y, boolean tilePrecision) {
        if (!tilePrecision) {
            x = Game.pixelToTile(x);
            y = Game.pixelToTile(y);
        }
        if (x < 0 || y < 0 || x >= 500 || y >= 500)
            return Tile.voidTile;

        return Tile.getTile(tiles[x + y * 500]);
    }

    protected void renderTiles(SpriteBatch spriteBatch) {
        int startX = Game.getXOffset() / zoom - 1;
        int endX = (Display.getWidth() + Game.getXOffset()) / zoom + 1;
        int startY = Game.getYOffset() / zoom - 1;
        int endY = (Display.getHeight() + Game.getYOffset()) / zoom + 1;
        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                getTile(x, y, true).render(spriteBatch, x * zoom - Game.getXOffset(), y * zoom - Game.getYOffset(), zoom, zoom);
            }
        }
    }
}
