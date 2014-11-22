package menu.component;

import game.fonts.Font;
import game.graphics.SpriteBatch;
import inventory.ItemStack;
import item.Item;

public class InventoryComponent extends Button {

    public InventoryComponent(int id, int width, int height) {
        super(id, width, height);
    }

    public InventoryComponent(int id, int menuX, int menuY, int width, int height) {
        super(id, menuX, menuY, width, height);
    }

    public void render(SpriteBatch batch, Item item) {
        if (item != null)
            item.render(batch, screenX, screenY);
    }

    public void renderString(Font font, int count) {
        if (count > 0) {
            String string = "";
            string += count;

            font.drawString(string, 8, screenX + 20, screenY + 20);
        }
    }
}
