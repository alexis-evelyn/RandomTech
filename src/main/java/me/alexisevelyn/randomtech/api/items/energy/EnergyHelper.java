package me.alexisevelyn.randomtech.api.items.energy;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

public interface EnergyHelper {
    boolean isNotFull(ItemStack stack);
    boolean isUsable(ItemStack stack);
    double getEnergy(ItemStack stack);
    void setEnergy(ItemStack stack, double energy);
    double getMaxStoredPower();

    ItemStack onCraft(ItemStack oldStack, ItemStack newStack, CompoundTag tag);
}
