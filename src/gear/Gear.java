package gear;

import game.graphics.SpriteBatch;
import item.Item;

import java.awt.image.BufferedImage;

public class Gear extends Item{
    protected int[] imagePosition;

    protected BufferedImage image;

    protected Stats stats;

    public Gear(String name) {
        super(name);

        // Gear pieces can not stack
        setMaxStackSize(1);
    }

    public void setTexture(String name) {
        // Doing nothing, because Gear has its own textureAtlas. Also images
        // must be loaded a different way
    }

    public void setTexture(String name, BufferedImage image) {
        this.image = image;
        imagePosition = itemAtlas.addTexture(name, image);
    }

    /**
     * Adds the stats to the gear. Will combined with other stats currently on the gear
     */
    public void addStats(Stats stats) {
        if (this.stats == null) this.stats = stats;
        else this.stats = Stats.combinedStats(this.stats, stats);
    }

    public void render(SpriteBatch batch, int x, int y) {
        batch.draw(x, y, imagePosition[0], imagePosition[1], imagePosition[2], imagePosition[3]);
    }

    public String getName() {
        return name;
    }
}
