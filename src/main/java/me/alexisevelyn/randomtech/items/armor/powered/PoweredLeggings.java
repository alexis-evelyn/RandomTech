package me.alexisevelyn.randomtech.items.armor.powered;

import me.alexisevelyn.randomtech.api.items.armor.generic.GenericPoweredArmor;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import team.reborn.energy.EnergyTier;

/**
 * The type Powered leggings.
 */
public class PoweredLeggings extends GenericPoweredArmor {
    private static final int energyCapacity = 687;
    private static final EnergyTier energyTier = EnergyTier.HIGH;
    private static final int cost = 1;
    private static final String dischargedTranslationKey = "item.randomtech.unpowered_leggings";

    /**
     * Instantiates a new Powered leggings.
     *
     * @param material the material
     * @param slot     the slot
     * @param settings the settings
     */
    public PoweredLeggings(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, energyCapacity, energyTier, cost, settings, dischargedTranslationKey);
    }

    /**
     * Is fireproof boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean isFireproof() {
        return true;
    }
}
