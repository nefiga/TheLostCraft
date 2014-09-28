package tile;

public class DirtTile extends Tile{

    public DirtTile(String name) {
        super(name);
        setImage("dirt_tile");
        setMapImage("map_dirt");
        this.id = addTile(this);
    }
}
