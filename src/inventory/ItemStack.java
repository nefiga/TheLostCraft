package inventory;

import item.Item;

public class ItemStack {

    private Item item;
    private int size;
    private int maxSize;
    private boolean canStack = true;

    public ItemStack(Item item, int size) {
        this.item = item;
        this.size = size;
        this.maxSize = item.getMaxStackSize();
    }

    public ItemStack splitStack() {
        int halfStack = size / 2;
        size = halfStack + size % 2;
        if (halfStack <= 0) return null;
        return new ItemStack(item, halfStack);
    }

    public ItemStack placeOne() {
        if (!canStack) return null;
        size--;
        return new ItemStack(item, 1);
    }

    public ItemStack mergeStacks(ItemStack stack) {
        if (stack == null || !item.equals(stack.getItem())) return stack;

        int totalItems = size + stack.getSize();
        if (totalItems > maxSize) {
            size = maxSize;
            return new ItemStack(item, totalItems - maxSize);
        }
        size += stack.getSize();
        return null;
    }

    public boolean canAdd(Item item, int amount) {
        if (size + amount <= maxSize && item.equals(item)) {
            size += amount;
            return true;
        }
        else return false;
    }

    public boolean isFull() {
        return size == maxSize;
    }

    public int getSize() {
        return size;
    }

    public Item getItem() {
        return item;
    }
}
