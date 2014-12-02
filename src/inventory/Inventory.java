package inventory;

import item.Item;

public interface Inventory {

    public boolean canAddItem(Item item);

    public ItemStack addItemInSlot(ItemStack stack, int slot);

    public ItemStack addItemStack(ItemStack stack);

    public ItemStack addItemStackInSlot(ItemStack stack, int slot);

    public ItemStack takeItem(int slot);

    public ItemStack splitStack(int slot);

    public boolean containsItem(int slot);

    public ItemStack itemStackAt(int slot);
}
