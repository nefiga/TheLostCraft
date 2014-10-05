package tile;

public class GrassTile extends Tile{

    public GrassTile(String name) {
        super(name);
        setImage("grass_tile");
        this.id = addTile(this);
    }
}
