package item.part;

import item.resource.Resource;

public class Guard extends Part{
    public Guard(String image, Resource resource) {
        super(image, resource);
        setTexture(resource.getImageString() + "_guard");
    }
}
