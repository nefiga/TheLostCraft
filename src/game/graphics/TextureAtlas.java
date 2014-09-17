package game.graphics;

import java.awt.image.BufferedImage;

public class TextureAtlas {

    public static final int SMALL = 640, MEDIUM = 960, LARGE = 1024;

    private int[] atlas;

    // The tiles of the atlas. This is determined by rows * columns
    // An empty space is indicated by 0 and a full space is indicated by 1
    private int[] tiles;

    // The number of rows and columns in the TextureAtlas
    private final int rows, columns;

    // The size of the TextureAtlas
    private int width, height;

    // The size of a column and row
    private int tileSize;

    private int textureCapacity;

    /**
     * Creates a new TextureAtlas
     *
     * @param atlasSize The size of the TextureAtlas. There are the standard sizes small, medium and large
     * @param tileSize  The size of the rows and columns
     */
    public TextureAtlas(int atlasSize, int tileSize) {
        this.tileSize = tileSize;
        rows = atlasSize / tileSize;
        columns = atlasSize / tileSize;
        width = atlasSize;
        height = atlasSize;
        atlas = new int[atlasSize * atlasSize];
        textureCapacity = rows * columns;
        tiles = new int[rows * columns];
    }

    /**
     * Adds the image to the texture atlas at the first open position
     *
     * @param image The image to be added
     * @return An array where position 0 and 1 are the starting x and y positions of the image in the atlas
     * and position 2 and 3 are the width and height of the image.
     */
    public int[] addTexture(BufferedImage image) {
        int[] position = new int[4];
        int w = image.getWidth();
        int h = image.getHeight();
        int textureColumns = w / tileSize;
        int textureRows = h / tileSize;
        int[] imagePixels = new int[w * h];
        image.getRGB(0, 0, w, h, imagePixels, 0, image.getWidth());

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (checkSpace(c, r, textureColumns, textureRows)) {
                    position[0] = c;
                    position[1] = r;
                    position[2] = image.getWidth();
                    position[3] = image.getHeight();

                    int pixelRow = r * tileSize;
                    int pixelCol = c * tileSize;
                    for (int y = 0; y < h; y++) {
                        for (int x = 0; x < w; x++) {
                            atlas[pixelCol + x + (y + pixelRow) * width] = imagePixels[x + y * w];
                        }
                    }
                    fillTile(c, r, textureColumns, textureRows);
                    return position;
                }
            }
        }

        System.err.print("Atlas out off space  " + this.toString());
        return null;
    }

    /**
     * Adds the pixels to the texture atlas at the first open position
     *
     * @param imagePixels An array of pixels to be added to the texture atlas
     * @return An array where position 0 and 1 are the starting x and y positions of the image in the atlas.
     */
    public int[] addTexture(int[] imagePixels, int w, int h) {
        int[] position = new int[2];
        int textureColumns = w / tileSize;
        int textureRows = h / tileSize;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (checkSpace(c, r, textureColumns, textureRows)) {
                    position[0] = c;
                    position[1] = r;

                    int pixelRow = r * tileSize;
                    int pixelCol = c * tileSize;
                    for (int y = 0; y < h; y++) {
                        for (int x = 0; x < w; x++) {
                            atlas[pixelCol + x + (y + pixelRow) * width] = imagePixels[x + y * w];
                        }
                    }
                    fillTile(c, r, textureColumns, textureRows);
                    return position;
                }
            }
        }

        System.err.print("Atlas out off space  " + this.toString());
        return null;
    }

    /**
     * Sets the location in the tiles array to 1 (full)
     * @param c The starting column
     * @param r The starting row
     * @param numColumns The number of columns to set
     * @param numRows The number of rows to set
     */
    public void fillTile(int c, int r, int numColumns, int numRows) {
        for (int y = 0; y < numRows; y++) {
            for (int x = 0; x < numColumns; x++) {
                tiles[(c + x) + (r + y) * rows] = 1;
            }
        }
    }

    /**
     * Checks if there is enough space for the image at the given location
     * @param c The starting column
     * @param r The starting row
     * @param numCol The number of columns the image will take up
     * @param numRow The number of rows the image will take up
     * @return true if there is enough space
     */
    public boolean checkSpace(int c, int r, int numCol, int numRow) {
        if (tiles[c + r * columns] == 0 && c + numCol < columns && r + numRow < rows) {
            for (int y = 0; y < numRow; y++) {
                for (int x = 0; x < numCol; x++) {
                    if (tiles[(c + x) + (r + y) * rows] == 1) return false;
                }
            }
            return true;
        }

        return false;
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
    public int getTileSize() {
        return tileSize;
    }

    public int getTextureCapacity() {
        return textureCapacity;
    }
}
