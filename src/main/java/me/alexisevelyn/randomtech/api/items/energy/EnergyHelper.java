package me.alexisevelyn.randomtech.api.items.energy;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface EnergyHelper {
    void addDamage(ItemStack stack, PlayerEntity playerEntity, DamageSource damageSource, float damage);
}
