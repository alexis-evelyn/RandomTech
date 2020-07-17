package me.alexisevelyn.randomtech.items.tools.powered;

import me.alexisevelyn.randomtech.items.tools.generic.GenericPoweredHoe;
import me.alexisevelyn.randomtech.toolmaterials.poweredtools.PoweredToolMaterial;
import team.reborn.energy.EnergyTier;

public class PoweredHoe extends GenericPoweredHoe {
    private static final String dischargedTranslationKey = "item.randomtech.unpowered_hoe";

    public PoweredHoe(Settings settings) {
        super(new PoweredToolMaterial(), 1337, EnergyTier.HIGH, 1, 20, 0, settings, dischargedTranslationKey);
    }

    @Override
    public boolean isFireproof() {
        return true;
    }
}
