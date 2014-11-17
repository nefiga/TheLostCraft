package inventory;

import item.Item;

public class ItemStack {

    private Item item;
    private int amount;
    private int maxSize;
    private boolean canStack = true;

    public ItemStack(Item item, int amount) {
        this.item = item;
        this.amount = amount;
        this.maxSize = item.getMaxStackSize();
    }

    public ItemStack splitStack() {
        int halfStack = amount / 2;
        amount = halfStack + amount % 2;
        if (halfStack <= 0) return null;
        return new ItemStack(item, halfStack);
    }

    public ItemStack placeOne() {
        if (!canStack) return null;
        amount--;
        return new ItemStack(item, 1);
    }

    public ItemStack mergeStacks(ItemStack stack) {
        if (stack == null || !item.equals(stack.getItem())) return stack;

        int totalItems = amount + stack.getAmount();
        if (totalItems > maxSize) {
            amount = maxSize;
            return new ItemStack(item, totalItems - maxSize);
        }
        amount += stack.getAmount();
        return null;
    }

    public boolean canAdd(Item item, int amount) {
        if (this.amount + amount <= maxSize && item.equals(item)) {
            this.amount += amount;
            return true;
        }
        else return false;
    }

    public boolean isFull() {
        return amount == maxSize;
    }

    public int getAmount() {
        return amount;
    }

    public Item getItem() {
        return item;
    }
}
