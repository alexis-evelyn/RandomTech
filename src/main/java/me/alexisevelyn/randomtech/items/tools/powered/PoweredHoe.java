package me.alexisevelyn.randomtech.items.tools.powered;

import me.alexisevelyn.randomtech.items.tools.generic.GenericPoweredHoe;
import me.alexisevelyn.randomtech.toolmaterials.PoweredToolMaterial;

public class PoweredHoe extends GenericPoweredHoe {
    public PoweredHoe(Settings settings) {
        super(new PoweredToolMaterial(), -1, -2.2F, settings);
    }
}
