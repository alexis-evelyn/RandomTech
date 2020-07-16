package me.alexisevelyn.randomtech.items.tools.powered;

import me.alexisevelyn.randomtech.items.tools.generic.GenericPoweredPickaxe;
import me.alexisevelyn.randomtech.toolmaterials.PoweredToolMaterial;
import net.minecraft.item.Items;
import team.reborn.energy.EnergyTier;

public class PoweredPickaxe extends GenericPoweredPickaxe {
    public PoweredPickaxe(Settings settings) {
        super(new PoweredToolMaterial(), 1337, EnergyTier.HIGH, 1, 20, 0, Items.IRON_PICKAXE, settings);
    }
}
