package inventory;

import item.Item;

public class SizedInventory implements Inventory {

    protected ItemStack[] itemSlots = new ItemStack[24];

    public ItemStack[] getItemSlots() {
        return itemSlots;
    }

    public ItemStack getItemStackAt(int position) {
        if (position < itemSlots.length && itemSlots[position] != null)
            return itemSlots[position];
        return null;
    }

    @Override
    public boolean canAddItem(Item item) {
        for (int i = 0; i < itemSlots.length; i++) {
            if (itemSlots[i] != null && itemSlots[i].canAddItem(item))
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
        if (slot < itemSlots.length && itemSlots[slot].canAddItem(item))
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
        if (slot < itemSlots.length && itemSlots[slot] != null)
            return this.itemSlots[slot].mergeStacks(stack);
        return stack;
    }

    @Override
    public ItemStack takeItem(int slot) {
        if (slot < itemSlots.length && itemSlots[slot] != null)
            return itemSlots[slot];
        return null;
    }

    @Override
    public ItemStack splitStack(int slot) {
        if (slot < itemSlots.length && itemSlots[slot] != null)
            return itemSlots[slot].splitStack();
        return null;
    }
}
