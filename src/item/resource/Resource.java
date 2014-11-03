package item.resource;

import game.graphics.ImageManager;
import gear.Stats;
import item.Item;

public class Resource extends Item{

    public static final Resource stone = new StoneResource("Stone");

    private Stats stats;

    public Resource(String name) {
        super(name);
        setStats(new Stats(1, 1, 1));
    }

    public void setTexture(String image) {
        this.image = ImageManager.getImage("/items/resource/" + image);
        this.imageString = image;
        imagePosition = itemAtlas.addTexture(this.image);
    }

    protected void setStats(Stats stats) {
        this.stats = stats;
    }

    public Stats getStats() {
        return stats;
    }
}
