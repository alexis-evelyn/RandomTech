package me.alexisevelyn.randomtech.items.tools.powered;

import me.alexisevelyn.randomtech.items.tools.generic.GenericPoweredShovel;
import me.alexisevelyn.randomtech.toolmaterials.PoweredToolMaterial;
import net.minecraft.item.Items;
import team.reborn.energy.EnergyTier;

public class PoweredShovel extends GenericPoweredShovel {
    public PoweredShovel(Settings settings) {
        super(new PoweredToolMaterial(), 1337, EnergyTier.HIGH, 1, 20, 0, Items.IRON_SHOVEL, settings);
    }
}
