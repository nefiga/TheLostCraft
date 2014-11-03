package item.part;

import item.resource.Resource;

public class PickHead extends Part{

    public PickHead(String image, Resource resource) {
        super(image, resource);
        setTexture(resource.getImageString() + "_pick_head");
    }
}
