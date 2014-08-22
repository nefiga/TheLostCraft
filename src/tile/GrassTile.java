package tile;

public class GrassTile extends Tile{

    public GrassTile(String name, int xOffset, int yOffset) {
        super(name, xOffset, yOffset);
        this.id = addTile(this);
    }
}
