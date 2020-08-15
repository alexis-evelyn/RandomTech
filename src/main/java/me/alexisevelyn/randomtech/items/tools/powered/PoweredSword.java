package me.alexisevelyn.randomtech.items.tools.powered;

import me.alexisevelyn.randomtech.api.items.tools.generic.GenericPoweredSword;
import me.alexisevelyn.randomtech.toolmaterials.poweredtools.PoweredToolMaterial;
import team.reborn.energy.EnergyTier;

/**
 * The type Powered sword.
 */
public class PoweredSword extends GenericPoweredSword {
    private static final String dischargedTranslationKey = "item.randomtech.unpowered_sword";

    /**
     * Instantiates a new Powered sword.
     *
     * @param settings the settings
     */
    public PoweredSword(Settings settings) {
        super(new PoweredToolMaterial(), 2561, EnergyTier.HIGH, 1, 20, -2.4F, settings, dischargedTranslationKey);
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