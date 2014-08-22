package tile;

public class DirtTile extends Tile{

    public DirtTile(String name, int xOffset, int yOffset) {
        super(name, xOffset, yOffset);
        this.id = addTile(this);
    }
}
