package inventory;

import item.Item;

public class PlayerInventoryLower implements Inventory{

    private ItemStack[] itemStacks = new ItemStack[32];

    public boolean addItem(Item item) {
        int firstOpenSlot = 100;
        for (int i = 0; i < itemStacks.length; i++) {
            ItemStack itemStack = itemStacks[i];
            if (itemStack != null) {
                if (itemStack.canAdd(item, 1)) return true;
            }
            else if (i < firstOpenSlot) firstOpenSlot = i;
        }
        if (firstOpenSlot < itemStacks.length) {
            itemStacks[firstOpenSlot] = new ItemStack(item, 1);
            return true;
        }
        return false;
    }

    public ItemStack addItemStack(ItemStack itemStack) {
        int firstOpenSlot = 100;
        for (int i =0; i < itemStacks.length; i++) {
            ItemStack stack = itemStacks[i];
            if (stack != null) {
                itemStack = stack.mergeStacks(itemStack);
                if (itemStack == null) return null;
            }
            if (i < firstOpenSlot) firstOpenSlot = i;
        }
        if (firstOpenSlot < itemStacks.length) {
            itemStacks[firstOpenSlot] = itemStack;
            return null;
        }
        return itemStack;
    }

    public Item getItem(int position) {
        if (position > itemStacks.length) return null;
        if (itemStacks[position] == null) return null;
        return itemStacks[position].getItem();
    }

    public int getCount(int position) {
        if (position > itemStacks.length) return -1;
        if (itemStacks[position] == null) return -1;
        return itemStacks[position].getSize();
    }
}
