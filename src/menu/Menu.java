package menu;

import game.graphics.ImageManager;
import game.graphics.SpriteBatch;
import game.graphics.TextureAtlas;

import java.awt.image.BufferedImage;

public class Menu {

    public static TextureAtlas menuAtlas;

    private BufferedImage cornerImage, sideImage, middleImage;

    // The amount of tiles wide and high the menu is
    private int width, height;

    // The size of the menu tiles
    private final int tileSize = 16;

    // The first array is the cursors current selection, the second array is the cursors x, y position on the screen.
    private int[][] cursorPosition;

    // The current index in the cursorPosition array
    private int currentSelection;

    // The location of the three images in the texture atlas used to create the menu
    private int[] corner, side, middle;

    /**
     * The menu is created out of 16 X 16 tiles.
     *
     * @param width       The numbers of tiles wide
     * @param height      The number of tiles tall
     * @param cornerImage The file location for the corner image
     * @param sideImage   The file location for the top, bottom and side image
     * @param middleImage The file location for the middle image
     */
    public Menu(int width, int height, String cornerImage, String sideImage, String middleImage) {
        this.width = width;
        this.height = height;

        if (menuAtlas == null) {
            menuAtlas = new TextureAtlas(TextureAtlas.SMALL);
        }

        this.cornerImage = ImageManager.getImage("/menus/" + cornerImage);
        this.sideImage = ImageManager.getImage("/menus/" + sideImage);
        this.middleImage = ImageManager.getImage("/menus/" + middleImage);

        corner = menuAtlas.addTexture(this.cornerImage);
        side = menuAtlas.addTexture(this.sideImage);
        middle = menuAtlas.addTexture(this.middleImage);
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

    public void render(SpriteBatch batch, int x, int y) {
        // Drawing the corners
        batch.draw(tileSize, tileSize, x, y, corner[0], corner[1]);
        batch.draw(tileSize, tileSize, x + width * tileSize, y, corner[0], corner[1], SpriteBatch.ROTATE_90);
        batch.draw(tileSize, tileSize, x, y + height * tileSize, corner[0], corner[1], SpriteBatch.ROTATE_270);
        batch.draw(tileSize, tileSize, x + width * tileSize, y + height * tileSize, corner[0], corner[1], SpriteBatch.ROTATE_180);

        // DrawSides
        for (int i = 1; i < width; i++) {
            batch.draw(tileSize, tileSize, x, y + i * tileSize, side[0], side[1]);
            batch.draw(tileSize, tileSize, x + i * tileSize, y, side[0], side[1], SpriteBatch.ROTATE_90);
            batch.draw(tileSize, tileSize, x + width * tileSize, y + i * tileSize, side[0], side[1], SpriteBatch.ROTATE_180);
            batch.draw(tileSize, tileSize, x + i * tileSize, y + height * tileSize, side[0], side[1], SpriteBatch.ROTATE_270);

        }

        //Draw middle
        for (int yp = 1; yp < height; yp++) {
            for (int xp = 1; xp < width; xp++) {
                batch.draw(tileSize, tileSize, x + xp * tileSize, y + yp * tileSize, middle[0], middle[1]);
            }
        }
    }
}
