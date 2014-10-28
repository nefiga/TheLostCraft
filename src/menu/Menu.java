package menu;

import game.Game;
import game.Screen;
import game.fonts.Font;
import game.graphics.*;
import menu.result.Result;

public abstract class Menu {

    public static TextureAtlas menuAtlas = new TextureAtlas(TextureAtlas.SMALL);
    protected SpriteBatch menuBatch;

    protected Font font = Font.generalFont;

    protected Screen screen;

    protected int verticalSpacing = 10;

    // The amount of tiles wide and high the menu is
    protected int width, height;

    protected int x, y;

    // Insets for items put in the menu so they do not touch the edges
    protected int[] insetY, insetX;

    // The size of the menu tiles
    protected final int tileSize;

    protected int drawSize;

    // The first array is the cursors current selection, the second array is the cursors x, y position on the screen.
    protected int[][] cursorPosition;

    // The current index in the cursorPosition array
    protected int currentSelection;

    // The location of the three images in the texture atlas used to create the menu
    protected int[] cornerImage, sideImage, middleImage, cursorImage;

    /**
     * Creates a new menu. The size of the menu can be controlled in two ways.
     * You can control the width and height of the tiles as well as how many tiles used.
     * @param width       The numbers of tiles wide
     * @param height      The number of tiles tall
     * @param tileSize    The width and height of the tiles for this menu
     * @param drawSize    The width and height the tiles will be drawn on screen
     * @param cornerImage The file location for the corner image
     * @param sideImage   The file location for the top, bottom and side image
     * @param middleImage The file location for the middle image
     */
    public Menu(int width, int height, int tileSize, int drawSize, String cornerImage, String sideImage, String middleImage) {
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.drawSize = drawSize;

        this.cornerImage = menuAtlas.addTexture(ImageManager.getImage("/menu/" + cornerImage));
        this.sideImage = menuAtlas.addTexture(ImageManager.getImage("/menu/" + sideImage));
        this.middleImage = menuAtlas.addTexture(ImageManager.getImage("/menu/" + middleImage));
        cursorImage = menuAtlas.addTexture(ImageManager.getImage("/menu/cursor"));

        menuBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(Menu.menuAtlas), 1000);
    }

    public void update(long delta) {

    }

    public void render() {
        menuBatch.begin();
        // Drawing the corners
        menuBatch.draw(x, y, drawSize, drawSize, cornerImage[0], cornerImage[1], tileSize, tileSize);
        menuBatch.draw(x + width * drawSize, y, drawSize, drawSize, cornerImage[0], cornerImage[1], tileSize, tileSize, SpriteBatch.ROTATE_90);
        menuBatch.draw(x, y + height * drawSize, drawSize, drawSize, cornerImage[0], cornerImage[1], tileSize, tileSize, SpriteBatch.ROTATE_270);
        menuBatch.draw(x + width * drawSize, y + height * drawSize, drawSize, drawSize, cornerImage[0], cornerImage[1], tileSize, tileSize, SpriteBatch.ROTATE_180);

        // Draw top and bottom
        for (int i = 1; i < width; i++) {
            menuBatch.draw(x + i * drawSize, y, drawSize, drawSize, sideImage[0], sideImage[1], tileSize, tileSize, SpriteBatch.ROTATE_90);
            menuBatch.draw(x + i * drawSize, y + height * drawSize, drawSize, drawSize, sideImage[0], sideImage[1], tileSize, tileSize, SpriteBatch.ROTATE_270);
        }

        // Draw sides
        for (int i = 1; i < height; i++) {
            menuBatch.draw(x, y + i * drawSize, drawSize, drawSize, sideImage[0], sideImage[1], tileSize, tileSize);
            menuBatch.draw(x + width * drawSize, y + i * drawSize, drawSize, drawSize, sideImage[0], sideImage[1], tileSize, tileSize, SpriteBatch.ROTATE_180);
        }

        //Draw middle
        for (int yp = 1; yp < height; yp++) {
            for (int xp = 1; xp < width; xp++) {
                menuBatch.draw(x + xp * drawSize, y + yp * drawSize, drawSize, drawSize, middleImage[0], middleImage[1], tileSize, tileSize);
            }
        }
        menuBatch.draw(insetX[currentSelection] - 30, insetY[currentSelection] + 5, cursorImage[0], cursorImage[1], cursorImage[2], cursorImage[3]);
        menuBatch.end();
    }

    public abstract void moveCursorUp();

    public abstract void moveCursorRight();

    public abstract void moveCursorDown();

    public abstract void moveCursorLeft();

    public abstract void select();

    public abstract void back();

    public abstract void openForResult(Result result, Screen screen, int x, int y);

    public abstract void returnResult();

    public abstract void returnResultAndClose();

    public void setVerticalSpacing(int spacing) {
        this.verticalSpacing = spacing;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}
