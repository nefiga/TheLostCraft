package item.part;

import game.graphics.ImageManager;
import item.Item;
import gear.Stats;
import item.resource.Resource;

public class Part extends Item{

    protected Resource resource;
    public Stats stats;

    public Part(String image, Resource resource) {
        super(image);
        this.resource = resource;
        createStats(100, 1, 1);
    }

    public void setTexture(String image) {
        this.image = ImageManager.getImage("/items/part/" + image);
        this.imageString = image;
        imagePosition = itemAtlas.addTexture(this.image);
    }

    protected void createStats(float str, float agi, float intl) {
        Stats s = resource.getStats();
        stats = new Stats((int) (s.str * str), (int) (s.agi * agi), (int) (s.itl * intl));
    }

    public Stats getStats() {
        return stats;
    }
}
