package tile;

public class WaterTile extends Tile{

    public WaterTile(String name, int xOffset, int yOffset) {
        super(name, xOffset, yOffset);
        id = addTile(this);
    }
}
