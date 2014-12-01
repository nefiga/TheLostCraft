package entity;

import collision.shapes.Rectangle;
import game.Game;
import gear.tool.PickAxe;
import inventory.Inventory;
import inventory.SizedInventory;
import item.Item;
import item.part.Bracket;
import item.part.LongHandle;
import item.part.PickHead;
import item.resource.Resource;
import math.Vector2;
import menu.PlayerInventoryMenu;
import org.lwjgl.opengl.Display;

public class Player extends LivingEntity {

    SizedInventory inventory;

    PlayerInventoryMenu menu;

    private long globalCD;

    private int harvestReach = 196;

    public Player(int x, int y) {
        super(x, y);
        setTexture("player");
        shape = new Rectangle(new Vector2(x, y), 64, 64);
        rect = new java.awt.Rectangle(64, 64);
        rect.setLocation(x, y);
        this.currentTool = new PickAxe("Pick Axe", new LongHandle("handle", Resource.stone), new PickHead("pick_head", Resource.stone), new Bracket("bracket", Resource.stone));
        inventory = new SizedInventory(SizedInventory.SLOT_24);
        menu = new PlayerInventoryMenu(Display.getWidth() / 2 - 150, Display.getHeight() / 2 - 75, inventory);
    }

    public void update(long delta) {
        level.collectItems();
        globalCD += delta;
    }

    /**
     * Tries to interact with an entity or tile at screenX, screenY position
     */
    public void interactWith(int x, int y) {
        if (globalCD >= 1000) {
            level.interact(this, currentTool, x + Game.getXOffset(), y + Game.getYOffset(), 10, 32);
            globalCD = 0;
        }
    }

    /**
     * Returns true if item can be added to inventory
     */
    public boolean canCollectItem(Item item) {
        return inventory.canAddItem(item);
    }

    public void onEscapedPressed() {
        if (menu.isOpen())
            menu.close();
        else
            level.onEscapePressed();
    }

    public void toggleInventory() {
        System.out.println("Menu State " + menu.isOpen());
        if (menu.isOpen())
            menu.close();
        else
            menu.open();
    }

    public void openInventory(Inventory upperInventory) {
    }
}
