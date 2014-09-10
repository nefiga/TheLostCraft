package game.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextureAtlas {

    public static final int SMALL = 640, MEDIUM = 960, LARGE = 1024;

    private int[] atlas;

    // The number of rows and columns in the TextureAtlas
    private final int rows, columns;

    // The current row and column in the Atlas
    private int currentRow, currentColumn;

    // The size of the TextureAtlas
    private int width, height;

    // The size of a column and row
    private int textureSize;

    private int textureCapacity;

    /**
     * Creates a new TextureAtlas
     *
     * @param atlasSize   The size of the TextureAtlas. There are the standard sizes small, medium and large
     * @param textureSize The standard size of the textures to be used in this TextureAtlas
     */
    public TextureAtlas(int atlasSize, int textureSize) {
        this.textureSize = textureSize;
        rows = atlasSize / textureSize;
        columns = atlasSize / textureSize;
        width = atlasSize;
        height = atlasSize;
        atlas = new int[atlasSize * atlasSize];
        textureCapacity = rows * columns;
    }

    public int[] addTexture(BufferedImage image) {
        int[] position = new int[3];
            int w = image.getWidth();
            int h = image.getHeight();
            int[] imagePixels = new int[w * h];
            image.getRGB(0, 0, w, h, imagePixels, 0, image.getWidth());

            if (currentColumn + (w / textureSize) > columns) {
                increaseCurrentRow();
                currentColumn = 0;
            }

            position[0] = currentColumn;
            position[1] = currentRow;
            position[2] = textureSize;

            int pixelRow = currentRow * textureSize;
            int pixelCol = currentColumn * textureSize;
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    atlas[pixelCol + x + (y + pixelRow) * width] = imagePixels[x + y * w];
                }
            }

            currentColumn += (w / textureSize);
        return position;
    }

    public int[] addTexture(int[] imagePixels, int w, int h) {
        int[] position = new int[3];

        if (currentColumn + (w / textureSize) > columns) {
            increaseCurrentRow();
            currentColumn = 0;
        }

        int pixelRow = currentRow * textureSize;
        int pixelCol = currentColumn * textureSize;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                atlas[pixelCol + x + (y + pixelRow) * width] = imagePixels[x + y * w];
            }
        }
        currentColumn += (w / textureSize);
        return position;
    }

    public void increaseCurrentRow() {
        currentRow++;
        if (currentRow > rows) {
            System.err.println("To many textures!");
        }
    }

    /**
     * @return An array of all the pixels that have be loaded in this TextureAtlas
     */
    public int[] getAtlas() {
        return atlas;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * @return The size of the textures in this TextureAtlas
     */
    public int getTextureSize() {
        return textureSize;
    }

    public int getTextureCapacity() {
        return textureCapacity;
    }
}
