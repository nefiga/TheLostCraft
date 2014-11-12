package menu;

import game.Game;
import game.Screen;
import game.fonts.Font;
import game.graphics.ImageManager;
import game.graphics.ShaderManager;
import game.graphics.SpriteBatch;
import game.graphics.Texture;
import inventory.Inventory;
import inventory.PlayerInventoryLower;
import item.Item;
import org.lwjgl.opengl.Display;

public class InventoryMenu extends Menu {

    SpriteBatch itemBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(Item.itemAtlas), 100);

    private Inventory upperInventory;
    private PlayerInventoryLower lowerInventory;

    private boolean dualInventories;

    private int[] upperImage, lowerImage;

    private int upperX, upperY, lowerX, lowerY;

    public InventoryMenu(int tileSet) {
        super(tileSet);
    }

    public void openInventory(PlayerInventoryLower lowerInventory) {
        lowerX = Display.getWidth() / 2 - 311;
        lowerY = Display.getHeight() / 2 - 316;
        this.lowerInventory = lowerInventory;
        lowerImage = menuAtlas.addTexture("/menu/inventory");
        menuBatch.updateTexture();

        Game.openMenu(this);
    }

    public void openInventory(Inventory upperInventory, PlayerInventoryLower lowerInventory) {
        upperX = Display.getWidth() / 2 - 311;
        upperY = Display.getHeight() / 2;
        lowerX = upperX;
        lowerY = upperY - 316;
        this.upperInventory = upperInventory;
        this.lowerInventory = lowerInventory;
        lowerImage = menuAtlas.addTexture("/menu/inventory");
        upperImage = menuAtlas.addTexture("/menu/inventory");
        menuBatch.updateTexture();
        dualInventories = true;
        Game.openMenu(this);
    }

    public void render() {
        menuBatch.begin();
        menuBatch.draw(lowerX, lowerY, lowerImage[0], lowerImage[1], lowerImage[2], lowerImage[3]);

        if (dualInventories) {
            menuBatch.draw(upperX, upperY, upperImage[0], upperImage[1], upperImage[2], upperImage[3]);
        }
        menuBatch.end();

        itemBatch.begin();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 8; x++) {
                Item item = lowerInventory.getItem(x + y * 8);
                if (item != null) {
                    item.render(itemBatch, lowerX + 5 + x * 37, lowerY + 5 + y * 37);
                }
            }
        }
        itemBatch.end();
        Font.generalFont.begin();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 8; x++) {
                int count = lowerInventory.getCount(x + y * 8);
                if (count != -1) {
                    String c = "";
                    c += count;
                    Font.generalFont.drawString(c, lowerX + 5 + x * 37, lowerY + 5 + y * 37);
                }
            }
        }
        Font.generalFont.end();
    }

    @Override
    public void click(int button, int x, int y) {

    }

    @Override
    public void release(int button, int x, int y) {

    }

    @Override
    public void moveCursorUp() {

    }

    @Override
    public void moveCursorRight() {

    }

    @Override
    public void moveCursorDown() {

    }

    @Override
    public void moveCursorLeft() {

    }

    @Override
    public void charPressed(char c) {

    }

    @Override
    public void charHolding(char c) {

    }

    @Override
    public void select() {

    }

    @Override
    public void back() {
        Game.closeMenu();
    }

    @Override
    public void screenResized(int width, int height) {

    }

    @Override
    public void openForResult(Result result, Screen screen, int x, int y) {

    }

    @Override
    public void open(int x, int y) {

    }

    @Override
    public void returnResult() {

    }
}
