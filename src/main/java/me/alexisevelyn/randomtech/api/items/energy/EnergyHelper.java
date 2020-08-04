package me.alexisevelyn.randomtech.api.items.energy;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

public interface EnergyHelper {
    // This exists for both GenericPoweredTool and GenericPoweredArmor.
    // This makes it easier to target both classes in my mixins.

    boolean isNotFull(ItemStack stack);
    boolean isUsable(ItemStack stack);
    double getEnergy(ItemStack stack);
    void setEnergy(ItemStack stack, double energy);
    double getMaxEnergy(ItemStack stack);

    ItemStack onCraft(ItemStack oldStack, ItemStack newStack, CompoundTag tag);
}
