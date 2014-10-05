package tile;

public class EmptyTile extends Tile{

    public EmptyTile(String name) {
        super(name);
        setImage("empty_tile");
        id = addTile(this);
    }
}
