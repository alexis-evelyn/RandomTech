package me.alexisevelyn.randomtech.items.tools.powered;

import me.alexisevelyn.randomtech.items.tools.generic.GenericPoweredAxe;
import me.alexisevelyn.randomtech.toolmaterials.PoweredToolMaterial;

public class PoweredAxe extends GenericPoweredAxe {
    public PoweredAxe(Settings settings) {
        super(new PoweredToolMaterial(), -1, -2.2F, settings);
    }
}
