package gear;

import game.graphics.ImageManager;
import game.graphics.SpriteBatch;
import game.graphics.TextureAtlas;

import java.awt.image.BufferedImage;

public class Gear {

    public static TextureAtlas gearAtlas;

    int atlasS, atlasT, width, height;

    protected BufferedImage image;

    final int gearSize = 32;

    private String name;

    private Stats stats;

    public Gear(String name) {
        this.name = name;

        if (gearAtlas == null) {
            gearAtlas = new TextureAtlas(TextureAtlas.LARGE, gearSize);
            // Create default texture
            image = ImageManager.getImage("/items/blade");
            gearAtlas.addTexture(image);
        }
        atlasS = 0;
        atlasT = 0;
        width = height = gearSize;
    }

    public void setTexture(BufferedImage image) {
        this.image = image;
        int[] atlasPosition = gearAtlas.addTexture(image);
        atlasS = atlasPosition[0];
        atlasT = atlasPosition[1];
        width = height = atlasPosition[2];
    }

    public void addStats(Stats stats) {
        if (this.stats == null) this.stats = stats;
        else this.stats = Stats.combinedStats(this.stats, stats);
    }

    public void render(SpriteBatch batch, int x, int y) {
        batch.draw(width, height, x, y, atlasS, atlasT);
    }

    public String getName() {
        return name;
    }
}
