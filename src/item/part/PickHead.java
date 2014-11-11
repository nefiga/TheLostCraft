package item.part;

import gear.Stats;
import item.resource.Resource;

public class PickHead extends Part{

    public PickHead(String image, Resource resource) {
        super(image, resource);
        setTexture(resource.getImageString() + "_pick_head");
        setStat(Stats.STR, 2.5f);
        setStat(Stats.AGI, 1);
        setStat(Stats.INT, 0.5f);
    }
}
