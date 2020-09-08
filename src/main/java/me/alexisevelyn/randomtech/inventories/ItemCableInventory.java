package me.alexisevelyn.randomtech.inventories;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.stream.IntStream;

/**
 * The type Item cable inventory.
 */
public class ItemCableInventory implements SidedInventory {
    private DefaultedList<ItemStack> inventory;

    private boolean isDirty = false;
    private boolean needsProcessing = false;

    /**
     * Instantiates a new Item cable inventory.
     */
    public ItemCableInventory() {
        this.inventory = DefaultedList.ofSize(this.getFilterSlots(null).length + this.getRealSlots(null).length, ItemStack.EMPTY);
    }

    /**
     * Gets inventory.
     *
     * @return the inventory
     */
    public DefaultedList<ItemStack> getInventory() {
        return this.inventory;
    }

    /**
     * Sets inventory.
     *
     * @param inventory the inventory
     */
    public void setInventory(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    /**
     * Gets the available slot positions that are reachable from a given side.
     *
     * @param side
     */
    @Override
    @NotNull
    public int[] getAvailableSlots(Direction side) {
        // int[] filterSlots = getFilterSlots(side);
        // int[] realSlots = getRealSlots(side);

        // Concatenate Both Arrays into One
        // return Ints.concat(filterSlots, realSlots);

        return getRealSlots(side);
    }

    /**
     * Retrieve slot numbers used for filtering items
     *
     * @param side the side
     * @return int[] containing slot numbers
     */
    @NotNull
    public final int[] getFilterSlots(@Nullable Direction side) {
        return IntStream.rangeClosed(0, 8).toArray();
    }

    /**
     * Retrieve slot numbers used for item transfer
     *
     * @param side the side
     * @return int[] containing slot numbers
     */
    @NotNull
    public final int[] getRealSlots(@Nullable Direction side) {
        return IntStream.rangeClosed(9, 11).toArray();
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
        return this.isValid(slot, stack);
    }

    /**
     * Size int.
     *
     * @return the int
     */
    @Override
    public int size() {
        return this.inventory.size();
    }

    /**
     * Is empty boolean.
     *
     * @return the boolean
     */
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
     * Is empty boolean.
     *
     * @param side the side
     * @return the boolean
     */
    public boolean isEmpty(@Nullable Direction side) {
        for (int slot : getRealSlots(side)) {
            if (!getStack(slot).isEmpty())
                return false;
        }

        return true;
    }

    /**
     * Is filter empty boolean.
     *
     * @param side the side
     * @return the boolean
     */
    public boolean isFilterEmpty(@Nullable Direction side) {
        for (int slot : getFilterSlots(side)) {
            if (!getStack(slot).isEmpty())
                return false;
        }

        return true;
    }

    /**
     * Filter contains boolean.
     *
     * @param itemStack the item stack
     * @param side      the side
     * @return the boolean
     */
    public boolean filterContains(@NotNull ItemStack itemStack, @Nullable Direction side) {
        for (int slot : getFilterSlots(side)) {
            if (getStack(slot).getItem().equals(itemStack.getItem()))
                return true;
        }

        return false;
    }

    /**
     * Fetches the stack currently stored at the given slot. If the slot is empty,
     * or is outside the bounds of this inventory, returns see {@link ItemStack#EMPTY}.
     *
     * @param slot
     */
    @Override
    @NotNull
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
    @NotNull
    public ItemStack removeStack(int slot, int amount) {
        markDirty();
        setNeedsProcessing(true);

        return Inventories.splitStack(this.inventory, slot, amount);
    }

    /**
     * Removes the stack currently stored at the indicated slot.
     *
     * @param slot
     * @return the stack previously stored at the indicated slot.
     */
    @Override
    @NotNull
    public ItemStack removeStack(int slot) {
        markDirty();
        setNeedsProcessing(true);

        return Inventories.removeStack(this.inventory, slot);
    }

    /**
     * Sets stack.
     *
     * @param slot  the slot
     * @param stack the stack
     */
    @Override
    public void setStack(int slot, ItemStack stack) {
        markDirty();
        setNeedsProcessing(true);

//        if (stack.getCount() > this.getMaxCountPerStack()) {
//            stack.setCount(this.getMaxCountPerStack());
//        }

        this.inventory.set(slot, stack);
    }

    /**
     * Mark dirty.
     */
    @Override
    public void markDirty() {
        this.isDirty = true;
    }

    /**
     * Mark clean.
     */
    public void markClean() {
        this.isDirty = false;
    }

    /**
     * Is dirty boolean.
     *
     * @return the boolean
     */
    public boolean isDirty() {
        return this.isDirty;
    }

    /**
     * Sets needs processing.
     *
     * @param needsProcessing the needs processing
     */
    public void setNeedsProcessing(boolean needsProcessing) {
        this.needsProcessing = needsProcessing;
    }

    /**
     * Needs processing boolean.
     *
     * @return the boolean
     */
    public boolean needsProcessing() {
        return this.needsProcessing;
    }

    /**
     * Can player use boolean.
     *
     * @param player the player
     * @return the boolean
     */
    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    /**
     * Clear.
     */
    @Override
    public void clear() {
        markDirty();
        setNeedsProcessing(true);

        this.inventory.clear();
    }

    public int calculateComparatorOutput() {
        // Potential future idea of different comparator output depending on side?
        int[] slots = getRealSlots(null);

        int iterations = 0;
        float itemToStackRatio = 0.0F;

        for (int slot : slots) {
            ItemStack itemStack = getStack(slot);

            if (!itemStack.isEmpty()) {
                itemToStackRatio += itemStack.getCount() / (float) Math.min(getMaxCountPerStack(), itemStack.getMaxCount());
                ++iterations;
            }
        }

        itemToStackRatio /= slots.length;
        return MathHelper.floor(itemToStackRatio * 14.0F) + (iterations > 0 ? 1 : 0);
    }
}
