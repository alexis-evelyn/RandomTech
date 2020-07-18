package me.alexisevelyn.randomtech.items.tools.powered;

import me.alexisevelyn.randomtech.api.items.tools.generic.GenericPoweredSword;
import me.alexisevelyn.randomtech.toolmaterials.poweredtools.PoweredToolMaterial;
import team.reborn.energy.EnergyTier;

public class PoweredSword extends GenericPoweredSword {
    private static final String dischargedTranslationKey = "item.randomtech.unpowered_sword";

    public PoweredSword(Settings settings) {
        super(new PoweredToolMaterial(), 2561, EnergyTier.HIGH, 1, 20, -2.4F, settings, dischargedTranslationKey);
    }

    @Override
    public boolean isFireproof() {
        return true;
    }
}