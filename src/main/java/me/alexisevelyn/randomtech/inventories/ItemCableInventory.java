package me.alexisevelyn.randomtech.inventories;

import com.google.common.primitives.Ints;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class ItemCableInventory implements SidedInventory {
    DefaultedList<ItemStack> inventory;

    public ItemCableInventory() {
        this.inventory = DefaultedList.ofSize(10, ItemStack.EMPTY);
    }

    public DefaultedList<ItemStack> getInventory() {
        return this.inventory;
    }

    public void setInventory(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    /**
     * Gets the available slot positions that are reachable from a given side.
     *
     * @param side
     */
    @Override
    public int[] getAvailableSlots(Direction side) {
        int[] filterSlots = getFilterSlots(side);
        int[] realSlots = getRealSlots(side);

        // Concatenate Both Arrays into One
        return Ints.concat(filterSlots, realSlots);
    }

    public int[] getFilterSlots(@Nullable Direction side) {
        return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
    }

    public int[] getRealSlots(@Nullable Direction side) {
        return new int[]{9};
    }

    /**
     * Determines whether the given stack can be inserted into this inventory at the specified slot position from the given direction.
     *
     * @param slot
     * @param stack
     * @param dir
     */
    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction dir) {
        return this.isValid(slot, stack);
    }

    /**
     * Determines whether the given stack can be removed from this inventory at the specified slot position from the given direction.
     *
     * @param slot
     * @param stack
     * @param dir
     */
    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        Iterator<ItemStack> itemStackIterator = this.inventory.iterator();

        ItemStack itemStack;
        do {
            if (!itemStackIterator.hasNext())
                return true;

            itemStack = itemStackIterator.next();
        } while(itemStack.isEmpty());

        return false;
    }

    /**
     * Fetches the stack currently stored at the given slot. If the slot is empty,
     * or is outside the bounds of this inventory, returns see {@link ItemStack#EMPTY}.
     *
     * @param slot
     */
    @Override
    public ItemStack getStack(int slot) {
        return this.inventory.get(slot);
    }

    /**
     * Removes a specific number of items from the given slot.
     *
     * @param slot
     * @param amount
     * @return the removed items as a stack
     */
    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.inventory, slot, amount);
    }

    /**
     * Removes the stack currently stored at the indicated slot.
     *
     * @param slot
     * @return the stack previously stored at the indicated slot.
     */
    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);

        if (stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }
    }

    @Override
    public void markDirty() {
        // Do Nothing For Now!!!
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }
}
