package tile;

import collision.shapes.Square;
import collision.shapes.Triangle;
import math.Vector2;

public class StoneTile extends Tile {

    public StoneTile(String name) {
        super(name);
        setImage("stone_tile");
        setShape(new Square(new Vector2(0, 0), 64, 64));
        id = addTile(this);
        setStartDurability(1000);
    }

    public boolean solid(int x, int y) {
        return true;
    }
}
