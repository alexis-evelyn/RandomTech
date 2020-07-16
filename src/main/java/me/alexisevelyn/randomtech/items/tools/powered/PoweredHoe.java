package me.alexisevelyn.randomtech.items.tools.powered;

import me.alexisevelyn.randomtech.items.tools.generic.GenericPoweredHoe;
import me.alexisevelyn.randomtech.toolmaterials.PoweredToolMaterial;
import net.minecraft.item.Items;
import team.reborn.energy.EnergyTier;

public class PoweredHoe extends GenericPoweredHoe {
    public PoweredHoe(Settings settings) {
        super(new PoweredToolMaterial(), 1337, EnergyTier.HIGH, 1, 20, 0, Items.IRON_HOE, settings);
    }
}
