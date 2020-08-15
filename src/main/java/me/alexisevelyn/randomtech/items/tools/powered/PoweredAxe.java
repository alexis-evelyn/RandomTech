package me.alexisevelyn.randomtech.items.tools.powered;

import me.alexisevelyn.randomtech.api.items.tools.generic.GenericPoweredAxe;
import me.alexisevelyn.randomtech.toolmaterials.poweredtools.PoweredToolMaterial;
import team.reborn.energy.EnergyTier;

/**
 * The type Powered axe.
 */
public class PoweredAxe extends GenericPoweredAxe {
    private static final String dischargedTranslationKey = "item.randomtech.unpowered_axe";

    /**
     * Instantiates a new Powered axe.
     *
     * @param settings the settings
     */
    public PoweredAxe(Settings settings) {
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
