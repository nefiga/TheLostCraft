package item.resource;

import collision.shapes.Rectangle;
import gear.Stats;
import math.Vector2;

public class StoneResource extends Resource{

    public StoneResource(String name) {
        super(name);
        setMaxStackSize(15);
        setTexture("stone");
        setShape(new Rectangle(new Vector2(0, 0), 32, 32));
        setStat(Stats.STR, 45);
        setStat(Stats.AGI, 10);
    }
}
