package me.alexisevelyn.randomtech.items.tools.powered;

import me.alexisevelyn.randomtech.api.items.tools.generic.GenericPoweredHoe;
import me.alexisevelyn.randomtech.toolmaterials.poweredtools.PoweredToolMaterial;
import team.reborn.energy.EnergyTier;

/**
 * The type Powered hoe.
 */
public class PoweredHoe extends GenericPoweredHoe {
    private static final String dischargedTranslationKey = "item.randomtech.unpowered_hoe";

    /**
     * Instantiates a new Powered hoe.
     *
     * @param settings the settings
     */
    public PoweredHoe(Settings settings) {
        super(new PoweredToolMaterial(), 2561, EnergyTier.HIGH, 1, 20, 0, settings, dischargedTranslationKey);
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
