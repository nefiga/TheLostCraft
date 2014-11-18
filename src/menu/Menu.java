package menu;

import game.Screen;
import game.fonts.Font;
import game.graphics.*;
import menu.component.MenuComponent;

public abstract class Menu {

    public TextureAtlas menuAtlas = new TextureAtlas(TextureAtlas.SMALL);

    public static final int NORMAL_TILE_SET = 0;

    protected Font font = Font.generalFont;

    protected Screen screen;

    protected Result result;

    protected MenuComponent component;

    protected int x, y;
    // The location of the three images in the texture atlas used to create the menu
    protected int[] cornerImage, sideImage, middleImage;

    /**
     * Creates a new menu. The size of the menu can be controlled in two ways.
     * You can control the width and height of the tiles as well as how many tiles used.
     */
    public Menu(int x, int y, MenuComponent component) {
        this.x = x;
        this.y = y;
        this.component = component;
        this.component.setPositionInMenu(x, y);
    }

    public Menu(int x, int y) {
    }

    public void update(long delta) {

    }

    public void render() {

        // Not sure I want menus to have a background.
        // I mostly want menus to be a holder for MenuComponents.

        /*menuBatch.begin();
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

        menuBatch.end();*/
    }

    public abstract void onMouseButtonPressed(int button, int x, int y);

    public abstract void onMouseButtonReleased(int button, int x, int y);

    public abstract void moveCursorUp();

    public abstract void moveCursorRight();

    public abstract void moveCursorDown();

    public abstract void moveCursorLeft();

    public abstract void charPressed(char c);

    public abstract void charHolding(char c);

    public abstract void select();

    public abstract void back();

    public abstract void screenResized(int width, int height);

    public abstract void openForResult(Result result, Screen screen);

    public abstract void open();

    public abstract void returnResult();

    public void setFont(Font font) {
        this.font = font;
    }
}
