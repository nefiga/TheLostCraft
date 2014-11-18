package menu;

import inventory.ItemStack;
import inventory.SizedInventory;
import menu.component.HorizontalListView;
import menu.component.InventoryComponent;
import menu.component.MenuComponent;
import menu.component.VerticalListView;

public class PlayerInventoryMenu extends SingleComponentMenu implements MenuComponent.OnClickListener {

    SizedInventory inventory;

    HorizontalListView topView, middleView, bottomView;

    ItemStack holding = null;

    public PlayerInventoryMenu(int x, int y, SizedInventory inventory) {
        super(x, y);
        this.inventory = inventory;
        loadInventoryComponents();
    }

    private void loadInventoryComponents() {
        topView = new HorizontalListView(-1, 301, 32);
        middleView = new HorizontalListView(-1, 301, 32);
        bottomView = new HorizontalListView(-1, 301, 32);
        this.component = new VerticalListView(-1, 301, 155, topView, middleView, bottomView);
        component.setTopPadding(5);
        component.setLeftPadding(5);
        ((VerticalListView) component).setCenter(false);
        ((VerticalListView) component).setSpacing(5);

        ItemStack[] itemSlots = inventory.getItemSlots();
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 8; x++) {
                if (x + y >= itemSlots.length) continue;
                if (y == 0)
                    topView.addComponent(new InventoryComponent(x + y * 8, 32, 32, itemSlots[x + y * 8]));
                    else if (y == 1)
                    middleView.addComponent(new InventoryComponent(x + y * 8, 32, 32, itemSlots[x + y * 8]));
                    else if (y == 2)
                    bottomView.addComponent(new InventoryComponent(x + y * 8, 32, 32, itemSlots[x + y * 8]));
            }
        }
    }

    @Override
    public void onLeftClick(MenuComponent c) {

    }

    @Override
    public void onRightClick(MenuComponent c) {

    }
}
