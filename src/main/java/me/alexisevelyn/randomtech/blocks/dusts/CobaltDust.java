package me.alexisevelyn.randomtech.blocks.dusts;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.RedstoneWireBlock;

import java.util.function.ToIntFunction;

// This seems like a cool idea for a bluestone dust like what Mumbo joked about one time
public class CobaltDust extends RedstoneWireBlock {
    public CobaltDust() {
        super(FabricBlockSettings
                .of(Material.SUPPORTED)
                .nonOpaque()
                .noCollision()
                .breakInstantly()
                .lightLevel(getLightLevel()));
    }

    public static ToIntFunction<BlockState> getLightLevel() {
        return (state) -> 10; // 7?
    }
}
