package menu;

import game.fonts.Font;
import game.graphics.ShaderManager;
import game.graphics.SpriteBatch;
import game.graphics.Texture;
import inventory.ItemStack;
import inventory.SizedInventory;
import item.Item;
import menu.component.InventoryComponent;
import menu.component.MenuComponent;
import menu.component.MenuComponent.OnClickListener;

public class InventoryMenu extends SingleComponentMenu implements OnClickListener {

    SpriteBatch inventoryBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(Item.itemAtlas), 100);

    SizedInventory inventory;

    InventoryComponent[] inventoryComponents;

    ItemStack holding = null;

    public InventoryMenu(int x, int y, MenuComponent component, SizedInventory inventory) {
        super(x, y, component);
        this.inventory = inventory;
        createInventoryComponents();
    }

    public InventoryMenu(int x, int y, SizedInventory inventory) {
        super(x, y);
        this.inventory = inventory;
        createInventoryComponents();
    }

    private void createInventoryComponents() {
        ItemStack[] itemStacks = inventory.getItemSlots();
        inventoryComponents = new InventoryComponent[itemStacks.length];

        for (int i = 0; i < itemStacks.length; i++) {
            inventoryComponents[i] = new InventoryComponent(i, 32, 32);
            inventoryComponents[i].renderBackground(false);
        }
    }

    public void render() {
        super.render();

        inventoryBatch.begin();
        for (int i = 0; i < inventoryComponents.length; i++) {
            if (inventory.getItemStackAt(i) != null)
                inventoryComponents[i].render(inventoryBatch, inventory.getItemStackAt(i).getItem());
        }
        inventoryBatch.end();

        Font.generalFont.begin();
        for (int i = 0; i < inventoryComponents.length; i++) {
            if (inventory.getItemStackAt(i) != null)
                inventoryComponents[i].renderString(Font.generalFont, inventory.getItemStackAt(i).getAmount());
        }
        Font.generalFont.end();
    }

    @Override
    public void onLeftPressed(MenuComponent c) {

    }

    @Override
    public void onLeftReleased(MenuComponent c) {

    }

    @Override
    public void onLeftClick(MenuComponent c) {

    }

    @Override
    public void onRightPressed(MenuComponent c) {

    }

    @Override
    public void onRightReleased(MenuComponent c) {

    }

    @Override
    public void onRightClick(MenuComponent c) {

    }
}
