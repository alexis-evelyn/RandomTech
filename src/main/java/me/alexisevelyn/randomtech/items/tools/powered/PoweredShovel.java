package me.alexisevelyn.randomtech.items.tools.powered;

import me.alexisevelyn.randomtech.api.items.tools.generic.GenericPoweredShovel;
import me.alexisevelyn.randomtech.toolmaterials.poweredtools.PoweredToolMaterial;
import team.reborn.energy.EnergyTier;

/**
 * The type Powered shovel.
 */
public class PoweredShovel extends GenericPoweredShovel {
    private static final String dischargedTranslationKey = "item.randomtech.unpowered_shovel";

    /**
     * Instantiates a new Powered shovel.
     *
     * @param settings the settings
     */
    public PoweredShovel(Settings settings) {
        super(new PoweredToolMaterial(), 2561, EnergyTier.HIGH, 1, 20, -3.0F, settings, dischargedTranslationKey);
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
