package tile;

public class StoneTile extends Tile{

    public StoneTile(String name) {
        super(name);
        setImage("stone_tile");
        id = addTile(this);
        setDurability(1000);
    }

    public boolean solid() {
        return true;
    }
}
