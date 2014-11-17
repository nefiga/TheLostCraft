package inventory;

import item.Item;

public class PlayerInventory implements Inventory {

    private ItemStack[] itemSlots = new ItemStack[30];

    @Override
    public Item addItem(Item item) {
        return null;
    }

    @Override
    public Item addItemInSlot(Item item, int slot) {
        return null;
    }

    @Override
    public ItemStack addItemStack(ItemStack stack) {
        for (int i = 0; i < this.itemSlots.length; i++) {
            if (stack.getAmount() <= 0) return null;
            if (this.itemSlots[i].getItem() == stack.getItem())
                stack = this.itemSlots[i].mergeStacks(stack);
        }
        for (int i = 0; i < this.itemSlots.length; i++) {
            if (itemSlots[i] == null) {
                itemSlots[i] = stack;
                return null;
            }
        }
        return stack;
    }

    @Override
    public ItemStack addItemStackInSlot(ItemStack stack, int slot) {
        return this.itemSlots[slot].mergeStacks(stack);
    }

    @Override
    public ItemStack takeItem() {
        return null;
    }

    @Override
    public ItemStack splitStack() {
        return null;
    }
}
