package me.alexisevelyn.randomtech.items.tools.powered;

import me.alexisevelyn.randomtech.api.items.tools.generic.GenericPoweredAxe;
import me.alexisevelyn.randomtech.toolmaterials.poweredtools.PoweredToolMaterial;
import team.reborn.energy.EnergyTier;

public class PoweredAxe extends GenericPoweredAxe {
    private static final String dischargedTranslationKey = "item.randomtech.unpowered_axe";

    public PoweredAxe(Settings settings) {
        super(new PoweredToolMaterial(), 1337, EnergyTier.HIGH, 1, 20, -3.0F, settings, dischargedTranslationKey);
    }

    @Override
    public boolean isFireproof() {
        return true;
    }
}
