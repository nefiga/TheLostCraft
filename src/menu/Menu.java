package menu;

import game.Screen;
import game.fonts.Font;
import game.graphics.*;
import menu.component.MenuComponent;

public abstract class Menu {

    public TextureAtlas menuAtlas = new TextureAtlas(TextureAtlas.SMALL);
    protected SpriteBatch menuBatch;

    public static final int NORMAL_TILE_SET = 0;

    protected Font font = Font.generalFont;

    protected Screen screen;

    protected Result result;

    protected MenuComponent component;

    // The amount of tiles wide and high the menu is
    protected int width, height;

    protected int x, y;

    // The size of the menu tiles
    protected final int tileSize = 16;

    protected int drawSize;

    // The location of the three images in the texture atlas used to create the menu
    protected int[] cornerImage, sideImage, middleImage;

    /**
     * Creates a new menu. The size of the menu can be controlled in two ways.
     * You can control the width and height of the tiles as well as how many tiles used.
     *
     * @param width    The numbers of tiles wide
     * @param height   The number of tiles tall
     * @param drawSize The width and height the tiles will be drawn on screen
     */
    public Menu(int x, int y, int width, int height, int drawSize, int tileSet, MenuComponent component) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.drawSize = drawSize;
        this.component = component;
        this.component.setPositionInMenu(x, y);
        loadMenuImages(tileSet);

        menuBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(menuAtlas), width * height + 50);
    }

    public Menu(int tileSet) {
        loadMenuImages(tileSet);
        menuBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(menuAtlas), width * height + 50);
    }

    private void loadMenuImages(int tileSet) {
        switch (tileSet) {
            case NORMAL_TILE_SET:
                this.cornerImage = menuAtlas.addTexture("/menu/corner");
                this.sideImage = menuAtlas.addTexture("/menu/side");
                this.middleImage = menuAtlas.addTexture("/menu/middle");
                break;
        }
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

        menuBatch.end();
    }

    public abstract void onMouseButtonPressed(int button, int x, int y);

    public abstract void release(int button, int x, int y);

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
