package me.alexisevelyn.randomtech.items.tools.powered;

import me.alexisevelyn.randomtech.items.tools.generic.GenericPoweredShovel;
import me.alexisevelyn.randomtech.toolmaterials.PoweredToolMaterial;

public class PoweredShovel extends GenericPoweredShovel {
    public PoweredShovel(Settings settings) {
        super(new PoweredToolMaterial(), -1, -2.2F, settings);
    }
}
