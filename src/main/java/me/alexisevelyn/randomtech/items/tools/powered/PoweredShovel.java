package me.alexisevelyn.randomtech.items.tools.powered;

import me.alexisevelyn.randomtech.api.items.tools.generic.GenericPoweredShovel;
import me.alexisevelyn.randomtech.toolmaterials.poweredtools.PoweredToolMaterial;
import team.reborn.energy.EnergyTier;

public class PoweredShovel extends GenericPoweredShovel {
    private static final String dischargedTranslationKey = "item.randomtech.unpowered_shovel";

    public PoweredShovel(Settings settings) {
        super(new PoweredToolMaterial(), 2561, EnergyTier.HIGH, 1, 20, -3.0F, settings, dischargedTranslationKey);
    }

    @Override
    public boolean isFireproof() {
        return true;
    }
}
