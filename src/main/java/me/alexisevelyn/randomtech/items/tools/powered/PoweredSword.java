package me.alexisevelyn.randomtech.items.tools.powered;

import me.alexisevelyn.randomtech.items.tools.generic.GenericPoweredSword;
import me.alexisevelyn.randomtech.toolmaterials.PoweredToolMaterial;

public class PoweredSword extends GenericPoweredSword {
    public PoweredSword(Settings settings) {
        super(new PoweredToolMaterial(), -1, -2.2F, settings);
    }
}