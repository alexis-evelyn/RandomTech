package me.alexisevelyn.randomtech.items.armor.powered;

import me.alexisevelyn.randomtech.api.items.armor.generic.GenericPoweredArmor;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import team.reborn.energy.EnergyTier;

public class PoweredHelmet extends GenericPoweredArmor {
    private static final int energyCapacity = 512;
    private static final EnergyTier energyTier = EnergyTier.HIGH;
    private static final int cost = 1;
    private static final String dischargedTranslationKey = "item.randomtech.unpowered_helmet";

    public PoweredHelmet(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, energyCapacity, energyTier, cost, settings, dischargedTranslationKey);
    }

    @Override
    public boolean isFireproof() {
        return true;
    }
}
