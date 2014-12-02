package menu;

import inventory.SizedInventory;
import menu.component.*;

public class PlayerInventoryGUI extends InventoryGUI {

    HorizontalListView topView, middleView, bottomView;

    public PlayerInventoryGUI(int x, int y, SizedInventory inventory) {
        super(x, y, inventory);
        loadInventoryComponents();
    }

    private void loadInventoryComponents() {
        topView = new HorizontalListView(-1, 560, 64);
        topView.setLeftPadding(5);
        topView.renderBackground(false);
        middleView = new HorizontalListView(-1, 560, 64);
        middleView.setLeftPadding(5);
        middleView.renderBackground(false);
        bottomView = new HorizontalListView(-1, 560, 64);
        bottomView.setLeftPadding(5);
        bottomView.renderBackground(false);

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

        mainComponent = new VerticalListView(-1, 301, 250, topView, middleView, bottomView);
        mainComponent.renderBackground(false);
        mainComponent.setPositionInMenu(this);
        mainComponent.setTopPadding(12);
        mainComponent.setSpacing(12);
        mainComponent.setLeftPadding(5);
        mainComponent.setCenter(false);
    }

    public void back() {
        close();
    }

    public void charPressed(char c) {
        if (c == 'v')
            close();
    }
}
