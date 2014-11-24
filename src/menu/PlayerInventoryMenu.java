package menu;

import inventory.SizedInventory;
import menu.component.*;

public class PlayerInventoryMenu extends InventoryMenu {

    HorizontalListView topView, middleView, bottomView;

    public PlayerInventoryMenu(int x, int y, SizedInventory inventory) {
        super(x, y, inventory);
        loadInventoryComponents();
    }

    private void loadInventoryComponents() {
        topView = new HorizontalListView(-1, 301, 32);
        topView.setLeftPadding(5);
        middleView = new HorizontalListView(-1, 301, 32);
        middleView.setLeftPadding(5);
        bottomView = new HorizontalListView(-1, 301, 32);
        bottomView.setLeftPadding(5);

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 8; x++) {
                if (x + y >= inventory.getSize()) continue;

                if (y == 0)
                    topView.addComponent(inventoryComponents[x + y * 8]);
                else if (y == 1)
                    middleView.addComponent(inventoryComponents[x + y * 8]);
                else if (y == 2)
                    bottomView.addComponent(inventoryComponents[x + y * 8]);
            }
        }

        this.component = new VerticalListView(-1, 301, 155, topView, middleView, bottomView);
        component.setPositionInMenu(this);
        component.setTopPadding(12);
        component.setSpacing(12);
        component.setLeftPadding(5);
        component.setCenter(false);
    }
}
