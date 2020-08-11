package me.alexisevelyn.randomtech.items.armor.powered;

import me.alexisevelyn.randomtech.api.items.armor.generic.GenericPoweredArmor;
import me.alexisevelyn.randomtech.api.items.armor.generic.InvulnerabilityHandler;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import team.reborn.energy.EnergyTier;

public class PoweredChestplate extends GenericPoweredArmor {
    private static final int energyCapacity = 712;
    private static final EnergyTier energyTier = EnergyTier.HIGH;
    private static final int cost = 1;
    private static final String dischargedTranslationKey = "item.randomtech.unpowered_chestplate";

    public PoweredChestplate(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, energyCapacity, energyTier, cost, settings, dischargedTranslationKey);
    }

    @Override
    public boolean isFireproof() {
        return true;
    }

    @Override
    public boolean denyKillCommand(ItemStack itemStack, LivingEntity livingEntity) {
        return super.denyKillCommand(itemStack, livingEntity);
    }

    @Override
    public boolean denyGeneralDamage(ItemStack itemStack, DamageSource damageSource, float amount, LivingEntity livingEntity) {
        return super.denyGeneralDamage(itemStack, damageSource, amount, livingEntity);
    }
}
