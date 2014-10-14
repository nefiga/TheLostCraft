package tile;

import game.graphics.SpriteBatch;

public class VoidTile extends Tile{

    public VoidTile(String name) {
        super(name);
        id = addTile(this);
    }

    public boolean solid(int x, int y) {
        return true;
    }
}
