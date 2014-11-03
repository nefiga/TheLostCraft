package item.part;

import item.resource.Resource;

public class Blade extends Part{

    public Blade(String image, Resource resource) {
        super(image, resource);
        setTexture(resource.getImageString() + "_blade");
        createStats(2, 1.5f, 0.5f);
    }
}
