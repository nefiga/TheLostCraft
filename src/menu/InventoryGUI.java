package menu;

import game.fonts.Font;
import inventory.ItemStack;
import inventory.SizedInventory;
import menu.component.InventoryComponent;
import menu.component.MenuComponent;
import menu.component.MenuComponent.OnClickListener;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class InventoryGUI extends SingleComponentGUI implements OnClickListener {

    SizedInventory inventory;

    InventoryComponent[] inventoryComponents;

    ItemStack holding = null;

    public InventoryGUI(int x, int y, MenuComponent component, SizedInventory inventory) {
        super(x, y, component);
        this.inventory = inventory;
        createInventoryComponents();
    }

    public InventoryGUI(int x, int y, SizedInventory inventory) {
        super(x, y);
        this.inventory = inventory;
        createInventoryComponents();
    }

    private void createInventoryComponents() {
        inventoryComponents = new InventoryComponent[inventory.getSize()];

        for (int i = 0; i < inventory.getSize(); i++) {
            inventoryComponents[i] = new InventoryComponent(i, 64, 64);
            inventoryComponents[i].setOnClickListener(this);
        }
    }

    public void render() {

        MenuComponent.batch.begin();
        mainComponent.render(MenuComponent.batch);

        for (int i = 0; i < inventoryComponents.length; i++) {
            if (inventory.containsItem(i))
                inventoryComponents[i].render(MenuComponent.batch, inventory.itemStackAt(i).getItem());
        }

        if (holding != null)
            holding.getItem().render(MenuComponent.batch, Mouse.getX(), Math.abs(Mouse.getY() - Display.getHeight()));
        MenuComponent.batch.end();

        Font.generalFont.begin();
        mainComponent.renderString(Font.generalFont);

        for (int i = 0; i < inventoryComponents.length; i++) {
            if (inventory.containsItem(i))
                inventoryComponents[i].renderString(Font.generalFont, inventory.itemStackAt(i).getAmount());
        }
        Font.generalFont.end();
    }

    @Override
    public void onLeftPressed(MenuComponent c) {
        if (holding != null)
            holding = inventory.addItemStackInSlot(holding, c.getId());
        else
            holding = inventory.getItemStackAt(c.getId());
    }

    @Override
    public void onLeftReleased(MenuComponent c) {

    }

    @Override
    public void onLeftClick(MenuComponent c) {

    }

    @Override
    public void onRightPressed(MenuComponent c) {
        if (holding != null)
            holding = inventory.addItemInSlot(holding, c.getId());
        else
            holding = inventory.splitStack(c.getId());
    }

    @Override
    public void onRightReleased(MenuComponent c) {

    }

    @Override
    public void onRightClick(MenuComponent c) {

    }
}
