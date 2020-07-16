package me.alexisevelyn.randomtech.items.tools.powered;

import me.alexisevelyn.randomtech.items.tools.generic.GenericPoweredAxe;
import me.alexisevelyn.randomtech.toolmaterials.PoweredToolMaterial;
import net.minecraft.item.Items;
import team.reborn.energy.EnergyTier;

public class PoweredAxe extends GenericPoweredAxe {
    public PoweredAxe(Settings settings) {
        super(new PoweredToolMaterial(), 1337, EnergyTier.HIGH, 1, 20, 0, Items.IRON_AXE, settings);
    }
}
