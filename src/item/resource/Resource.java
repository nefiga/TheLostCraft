package item.resource;

import game.graphics.ImageManager;
import gear.Stats;
import item.Item;

public class Resource extends Item{

    public static final Resource stone = new StoneResource("Stone");

    protected Stats stats = new Stats();

    public Resource(String name) {
        super(name);
    }

    public void setTexture(String image) {
        this.image = ImageManager.getImage("/items/resource/" + image);
        this.imageString = image;
        imagePosition = itemAtlas.addTexture("/items/resource/" + image);
    }

    protected void setStat(int position, int stat) {
        this.stats.setStat(position, stat);
    }

    public Stats getStats() {
        return stats;
    }
}
