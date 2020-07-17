package me.alexisevelyn.randomtech.api.armor.energy;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;

public interface EnergyHelper {
    void addDamage(ItemStack stack, DamageSource damageSource, float damage);
}
