package testing;

import tile.Tile;

public class TestTile extends Tile {

    public TestTile(String name, int xOffset, int yOffset) {
        super(name, xOffset, yOffset);
        id = addTile(this);
    }

    public boolean solid() {
        return true;
    }
}
