package tile;

public class DirtTile extends Tile{

    public DirtTile(String name) {
        super(name);
        setImage("dirt_tile");
        this.id = addTile(this);
        mapColor = 0xff644909;
    }
}
