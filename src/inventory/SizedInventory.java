package inventory;

import item.Item;

public class SizedInventory implements Inventory{

    protected ItemStack[] itemSlots = new ItemStack[24];

    public ItemStack[] getItemSlots() {
        return itemSlots;
    }

    @Override
    public boolean canAddItem(Item item) {
        for (int i = 0; i < itemSlots.length; i++) {
            if (itemSlots[i].canAddItem(item))
                return true;
        }
        for (int i = 0; i < itemSlots.length; i++) {
            if (itemSlots[i] == null) {
                itemSlots[i] = new ItemStack(item, 1);
                return true;
            }
        }
        return false;
    }

    @Override
    public Item addItemInSlot(Item item, int slot) {
        if (itemSlots[slot].canAddItem(item))
            return null;

        return item;
    }

    @Override
    public ItemStack addItemStack(ItemStack stack) {
        for (int i = 0; i < itemSlots.length; i++) {
            if (stack.getAmount() <= 0) return null;
            if (itemSlots[i].getItem() == stack.getItem())
                stack = itemSlots[i].mergeStacks(stack);
        }
        for (int i = 0; i < itemSlots.length; i++) {
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
    public ItemStack takeItem(int slot) {
        return itemSlots[slot];
    }

    @Override
    public ItemStack splitStack(int slot) {
        return itemSlots[slot].splitStack();
    }
}
