package item.part;

import game.graphics.ImageManager;
import item.Item;
import gear.Stats;
import item.resource.Resource;

public class Part extends Item {

    protected Resource resource;
    public Stats stats = new Stats();

    public Part(String image, Resource resource) {
        super(image);
        this.resource = resource;
    }

    public void setTexture(String image) {
        this.image = ImageManager.getImage("/items/part/" + image);
        this.imageString = image;
        imagePosition = itemAtlas.addTexture("/items/part/" + image);
    }

    /**
     * Sets the stat of this part to the stat of this Parts Resource times the multiplier
     *
     * @param position   Position of the stat to be set
     * @param multiplier The amount to multiply the stat by
     */
    protected void setStat(int position, float multiplier) {
        int stat = resource.getStats().getStat(position);
        System.out.println("Before " + stat);
        stats.setStat(position, (int) (stat * multiplier));
        System.out.println("After " + stats.getStat(position));
    }

    public Stats getStats() {
        return stats;
    }
}
