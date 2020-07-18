package me.alexisevelyn.randomtech.items.tools.powered;

import me.alexisevelyn.randomtech.api.items.tools.generic.GenericPoweredPickaxe;
import me.alexisevelyn.randomtech.toolmaterials.poweredtools.PoweredToolMaterial;
import team.reborn.energy.EnergyTier;

public class PoweredPickaxe extends GenericPoweredPickaxe {
    private static final String dischargedTranslationKey = "item.randomtech.unpowered_pickaxe";

    public PoweredPickaxe(Settings settings) {
        super(new PoweredToolMaterial(), 1337, EnergyTier.HIGH, 1, 20, -2.8F, settings, dischargedTranslationKey);
    }

    @Override
    public boolean isFireproof() {
        return true;
    }
}
