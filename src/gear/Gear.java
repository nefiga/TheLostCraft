package gear;

import game.graphics.ImageManager;
import game.graphics.SpriteBatch;
import game.graphics.TextureAtlas;

import java.awt.image.BufferedImage;

public class Gear {

    public static TextureAtlas gearAtlas;

    protected int[] imagePosition;

    protected BufferedImage image;

    final int gearSize = 32;

    private String name;

    private Stats stats;

    public Gear(String name) {
        this.name = name;

        if (gearAtlas == null) {
            gearAtlas = new TextureAtlas(TextureAtlas.LARGE);
            // Create default texture
            image = ImageManager.getImage("/items/void_item");
            imagePosition = gearAtlas.addTexture(image);
        }
    }

    public void setTexture(BufferedImage image) {
        this.image = image;
        imagePosition = gearAtlas.addTexture(image);
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
