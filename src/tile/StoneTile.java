package tile;

public class StoneTile extends Tile{

    public StoneTile(String name) {
        super(name);
        setImage("stone_tile");
        setMapImage("map_stone");
        id = addTile(this);
        setDurability(1000);
        mapColor = 0xff707070;
    }

    public boolean solid() {
        return true;
    }
}
