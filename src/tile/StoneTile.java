package tile;

public class StoneTile extends Tile{

    public StoneTile(String name, int xOffset, int yOffset) {
        super(name, xOffset, yOffset);
        id = addTile(this);
        setDurability(1000);
    }

    public boolean solid() {
        return true;
    }
}
