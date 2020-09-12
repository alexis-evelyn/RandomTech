package me.alexisevelyn.randomtech.blockentities.cables;

import me.alexisevelyn.randomtech.utility.BlockEntitiesHelper;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;

import java.util.Set;

/**
 * The type Fluid cable block entity.
 */
public class FluidCableBlockEntity extends BlockEntity implements SidedInventory {
    /**
     * Instantiates a new Fluid cable block entity.
     */
    public FluidCableBlockEntity() {
        super(BlockEntitiesHelper.FLUID_CABLE);
    }

    /**
     * Gets the available slot positions that are reachable from a given side.
     *
     * @param side
     */
    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[0];
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
        return false;
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
        return false;
    }

    /**
     * Size int.
     *
     * @return the int
     */
    @Override
    public int size() {
        return 0;
    }

    /**
     * Is empty boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean isEmpty() {
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
        return null;
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
        return null;
    }

    /**
     * Removes the stack currently stored at the indicated slot.
     *
     * @param slot
     * @return the stack previously stored at the indicated slot.
     */
    @Override
    public ItemStack removeStack(int slot) {
        return null;
    }

    /**
     * Sets stack.
     *
     * @param slot  the slot
     * @param stack the stack
     */
    @Override
    public void setStack(int slot, ItemStack stack) {
        // Will Be Written Later
    }

    /**
     * Returns the maximum number of items a stack can contain when placed inside this inventory.
     * No slots may have more than this number of items. It is effectively the
     * stacking limit for this inventory's slots.
     *
     * @return the max {@link ItemStack#getCount() count} of item stacks in this inventory
     */
    @Override
    public int getMaxCountPerStack() {
        return 0;
    }

    /**
     * Mark dirty.
     */
    @Override
    public void markDirty() {
        // Will Be Written Later
    }

    /**
     * Can player use boolean.
     *
     * @param player the player
     * @return the boolean
     */
    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return false;
    }

    /**
     * On open.
     *
     * @param player the player
     */
    @Override
    public void onOpen(PlayerEntity player) {
        // Will Be Written Later
    }

    /**
     * On close.
     *
     * @param player the player
     */
    @Override
    public void onClose(PlayerEntity player) {
        // Intentionally Left Empty
    }

    /**
     * Returns whether the given stack is a valid for the indicated slot position.
     *
     * @param slot
     * @param stack
     */
    @Override
    public boolean isValid(int slot, ItemStack stack) {
        return false;
    }

    /**
     * Returns the number of times the specified item occurs in this inventory across all stored stacks.
     *
     * @param item
     */
    @Override
    public int count(Item item) {
        return 0;
    }

    /**
     * Determines whether this inventory contains any of the given candidate items.
     *
     * @param items
     */
    @Override
    public boolean containsAny(Set<Item> items) {
        return false;
    }

    /**
     * Clear.
     */
    @Override
    public void clear() {
        // Will Be Written Later
    }
}
