package inventory;

import item.Item;

public interface Inventory {

    public Item addItem(Item item);

    public Item addItemInSlot(Item item, int slot);

    public ItemStack addItemStack(ItemStack stack);

    public ItemStack addItemStackInSlot(ItemStack stack, int slot);

    public ItemStack takeItem();

    public ItemStack splitStack();
}
