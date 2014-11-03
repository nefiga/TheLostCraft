package item.part;

import item.resource.Resource;

public class Bracket extends Part{

    public Bracket(String image, Resource resource) {
        super(image, resource);
        setTexture(resource.getImageString() + "_bracket");
    }
}
