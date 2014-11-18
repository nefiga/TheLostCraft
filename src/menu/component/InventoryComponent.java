package menu.component;

import game.fonts.Font;
import game.graphics.SpriteBatch;
import inventory.ItemStack;

public class InventoryComponent extends Button {

    private ItemStack stack = null;

    public InventoryComponent(int id, int width, int height, ItemStack itemStack) {
        super(id, width, height);
        this.stack = itemStack;
    }

    public InventoryComponent(int id, int menuX, int menuY, int width, int height, ItemStack itemStack) {
        super(id, menuX, menuY, width, height);
        this.stack = itemStack;
    }

    public void render(SpriteBatch batch) {
        stack.getItem().render(batch, menuX, menuY);
    }

    public void renderString(Font font) {
        String string = "";
        string += stack.getAmount();

        font.drawString(string, 8, menuX + 16, menuY + 16);
    }
}
