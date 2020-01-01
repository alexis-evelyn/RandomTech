package me.alexisevelyn.randomtech.items.tools.powered;

import me.alexisevelyn.randomtech.items.tools.generic.GenericPoweredPickaxe;
import me.alexisevelyn.randomtech.toolmaterials.PoweredToolMaterial;

public class PoweredPickaxe extends GenericPoweredPickaxe {
    public PoweredPickaxe(Settings settings) {
        super(new PoweredToolMaterial(), -1, -2.2F, settings);
    }
}
