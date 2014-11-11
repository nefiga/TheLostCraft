package tile;

import collision.shapes.Rectangle;
import entity.Entity;
import entity.ItemEntity;
import gear.tool.PickAxe;
import gear.tool.Tool;
import item.resource.Resource;
import level.Level;
import math.Vector2;

public class StoneTile extends Tile {

    public StoneTile(String name) {
        super(name);
        setImage("stone_tile");
        setShape(new Rectangle(new Vector2(0, 0), 64, 64));
        id = addTile(this);
        setStartDurability(500);
    }

    public boolean solid(int x, int y) {
        return true;
    }

    @Override
    public void interact(Level level, Entity entity, Tool tool, int x, int y) {
        if (tool instanceof PickAxe) {
            level.damageTile(x, y, tool.getStrength());
        }
        if (level.getTileDurability(x, y, false) <= 0) breakTile(level, x, y);
    }

    @Override
    public void breakTile(Level level, int x, int y) {
        level.replaceTile(Tile.grass, x, y);
        level.addItemEntity(new ItemEntity(Resource.stone, x, y));
    }
}
