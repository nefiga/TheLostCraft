package tile;

public class GrassTile extends Tile{

    public GrassTile(String name) {
        super(name);
        setImage("grass_tile");
        setMapImage("map_grass");
        this.id = addTile(this);
        mapColor = 0xff0d8f00;
    }
}
