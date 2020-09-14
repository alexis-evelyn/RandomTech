package me.alexisevelyn.randomtech.api.items.energy;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import team.reborn.energy.EnergyHolder;

/**
 * The interface Energy helper.
 */
public interface EnergyHelper extends EnergyHolder {
    // This exists for both GenericPoweredTool and GenericPoweredArmor.
    // This makes it easier to target both classes in my mixins.

    /**
     * Is not full boolean.
     *
     * @param stack the stack
     * @return the boolean
     */
    boolean isNotFull(ItemStack stack);

    /**
     * Is usable boolean.
     *
     * @param stack the stack
     * @return the boolean
     */
    boolean isUsable(ItemStack stack);

    /**
     * Gets energy.
     *
     * @param stack the stack
     * @return the energy
     */
    double getEnergy(ItemStack stack);

    /**
     * Sets energy.
     *
     * @param stack  the stack
     * @param energy the energy
     */
    void setEnergy(ItemStack stack, double energy);

    /**
     * Gets max energy.
     *
     * @param stack the stack
     * @return the max energy
     */
    double getMaxEnergy(ItemStack stack);

    /**
     * On craft item stack.
     *
     * @param oldStack the old stack
     * @param newStack the new stack
     * @param tag      the tag
     * @return the item stack
     */
    ItemStack onCraft(ItemStack oldStack, ItemStack newStack, CompoundTag tag);
}
