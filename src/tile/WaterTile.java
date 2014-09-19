package tile;

public class WaterTile extends Tile{

    public WaterTile(String name) {
        super(name);
        setImage("water_tile");
        setMapImage("map_water");
        id = addTile(this);
        mapColor = 0xff008a9f;
    }
}
