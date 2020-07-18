package me.alexisevelyn.randomtech.items.armor.powered;

import me.alexisevelyn.randomtech.api.items.armor.generic.GenericPoweredArmor;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import team.reborn.energy.EnergyTier;

public class PoweredBoots extends GenericPoweredArmor {
    // TODO: Change tool and armor durability

    private static final int energyCapacity = 1337;
    private static final EnergyTier energyTier = EnergyTier.HIGH;
    private static final int cost = 1;
    private static final String dischargedTranslationKey = "item.randomtech.unpowered_boots";

    public PoweredBoots(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, energyCapacity, energyTier, cost, settings, dischargedTranslationKey);
    }

    @Override
    public boolean isFireproof() {
        return true;
    }
}
