package inventory;

import item.Item;

public class SizedInventory implements Inventory {

    public static final int SLOT_2 = 2, SLOT_6 = 6, SLOT_12 = 12, SLOT_24 = 24;

    protected int size;

    protected ItemStack[] itemSlots;

    public SizedInventory(int size) {
        this.size = size;
        itemSlots = new ItemStack[size];
    }

    public int getSize() {
        return size;
    }

    public ItemStack[] getItemSlots() {
        return itemSlots;
    }

    public ItemStack getItemStackAt(int position) {
        if (position < itemSlots.length && itemSlots[position] != null) {
            ItemStack returnStack = itemSlots[position];
            itemSlots[position] = null;
            return returnStack;
        }

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
    public ItemStack addItemInSlot(ItemStack stack, int slot) {
        if (stack == null) return null;

        if (slot < itemSlots.length) {
            if (itemSlots[slot] == null) {
                itemSlots[slot] = new ItemStack(stack.getItem(), 1);

                if (stack.getAmount() > 1)
                    return new ItemStack(stack.getItem(), stack.getAmount() - 1);
                else
                    return null;
            }
            else if (itemSlots[slot].canAddItem(stack.getItem())) {
                if (stack.getAmount() > 1)
                    return new ItemStack(stack.getItem(), stack.getAmount() - 1);
                else
                    return null;
            }
        }

        return stack;
    }

    @Override
    public ItemStack addItemStack(ItemStack stack) {
        if (stack == null) return null;

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
        if (stack == null) return null;

        if (slot < itemSlots.length) {
            if (itemSlots[slot] != null)
                return this.itemSlots[slot].mergeStacks(stack);

            itemSlots[slot] = stack;
            return null;
        }

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

    @Override
    public ItemStack itemStackAt(int slot) {
        if (slot < itemSlots.length && itemSlots[slot] != null)
            return itemSlots[slot];

        return null;
    }

    @Override
    public boolean containsItem(int slot) {
        if (slot < itemSlots.length && itemSlots[slot] != null)
            return true;

        return false;
    }
}
