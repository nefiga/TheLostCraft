package tile;

public class VoidTile extends Tile{

    public VoidTile(String name, int xOffset, int yOffset) {
        super(name, xOffset, yOffset);
    }

    public boolean solid() {
        return true;
    }
}
