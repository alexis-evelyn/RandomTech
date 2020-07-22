package me.alexisevelyn.randomtech.utility;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;

public final class Materials {
    public static final Material MACHINE_MATERIAL = Material.METAL;
    public static final Material GLASS_MATERIAL = Material.GLASS;
    public static final Material TILE_MATERIAL = Material.STONE;
    public static final Material DARK_GLASS_MATERIAL = new FabricMaterialBuilder(MaterialColor.BLACK).build();

    public static final Material METAL_ORE_MATERIAL = Material.METAL;
}
