package tile;

public class VoidTile extends Tile{

    public VoidTile(String name) {
        super(name);
        id = addTile(this);
    }

    public boolean solid() {
        return true;
    }
}
