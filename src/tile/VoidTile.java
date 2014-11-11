package tile;

import collision.shapes.Rectangle;
import math.Vector2;

public class VoidTile extends Tile {

    public VoidTile(String name) {
        super(name);
        id = addTile(this);
        setShape(new Rectangle(new Vector2(0, 0), 64, 64));
    }

    public boolean solid(int x, int y) {
        return true;
    }
}
