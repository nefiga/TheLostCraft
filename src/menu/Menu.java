package menu;

import game.graphics.*;

import java.awt.image.BufferedImage;

public class Menu {

    public static TextureAtlas menuAtlas = new TextureAtlas(TextureAtlas.SMALL);
    private SpriteBatch menuBatch;

    private BufferedImage cornerImage, sideImage, middleImage;

    // The amount of tiles wide and high the menu is
    private int width, height;

    // The size of the menu tiles
    private final int tileSize;

    // The first array is the cursors current selection, the second array is the cursors x, y position on the screen.
    private int[][] cursorPosition;

    // The current index in the cursorPosition array
    private int currentSelection;

    // The location of the three images in the texture atlas used to create the menu
    private int[] corner, side, middle;

    /**
     * @param width       The numbers of tiles wide
     * @param height      The number of tiles tall
     * @param tileSize    The width and height of the tiles for this menu
     * @param cornerImage The file location for the corner image
     * @param sideImage   The file location for the top, bottom and side image
     * @param middleImage The file location for the middle image
     */
    public Menu(int width, int height, int tileSize, String cornerImage, String sideImage, String middleImage) {
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;

        this.cornerImage = ImageManager.getImage("/menus/" + cornerImage);
        this.sideImage = ImageManager.getImage("/menus/" + sideImage);
        this.middleImage = ImageManager.getImage("/menus/" + middleImage);

        corner = menuAtlas.addTexture(this.cornerImage);
        side = menuAtlas.addTexture(this.sideImage);
        middle = menuAtlas.addTexture(this.middleImage);

        menuBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(Menu.menuAtlas), 1000);
    }

    public void moveCursorUp() {

    }

    public void moveCursorRight() {

    }

    public void moveCursorDown() {

    }

    public void moveCursorLeft() {

    }

    public void select() {

    }

    public void back() {

    }

    public void render(int x, int y) {
        menuBatch.begin();
        // Drawing the corners
        menuBatch.draw(x, y, corner[0], corner[1], tileSize, tileSize);
        menuBatch.draw(x + width * tileSize, y, corner[0], corner[1], tileSize, tileSize, SpriteBatch.ROTATE_90);
        menuBatch.draw(x, y + height * tileSize, corner[0], corner[1], tileSize, tileSize, SpriteBatch.ROTATE_270);
        menuBatch.draw(x + width * tileSize, y + height * tileSize, corner[0], corner[1], tileSize, tileSize, SpriteBatch.ROTATE_180);

        // Draw top and bottom
        for (int i = 1; i < width; i++) {
            menuBatch.draw(x + i * tileSize, y, side[0], side[1], tileSize, tileSize, SpriteBatch.ROTATE_90);
            menuBatch.draw(x + i * tileSize, y + height * tileSize, side[0], side[1], tileSize, tileSize, SpriteBatch.ROTATE_270);
        }

        // Draw sides
        for (int i = 1; i < height; i++) {
            menuBatch.draw(x, y + i * tileSize, side[0], side[1], tileSize, tileSize);
            menuBatch.draw(x + width * tileSize, y + i * tileSize, side[0], side[1], tileSize, tileSize, SpriteBatch.ROTATE_180);
        }

        //Draw middle
        for (int yp = 1; yp < height; yp++) {
            for (int xp = 1; xp < width; xp++) {
                menuBatch.draw(x + xp * tileSize, y + yp * tileSize, middle[0], middle[1], tileSize, tileSize);
            }
        }
        menuBatch.end();
    }
}
