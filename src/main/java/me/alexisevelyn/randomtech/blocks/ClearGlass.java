package me.alexisevelyn.randomtech.blocks;

import me.alexisevelyn.randomtech.Materials;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

// AbstractGlassBlock and ConnectingBlock
public class ClearGlass extends AbstractGlassBlock {
    // TODO: Figure out why glass has wrong block texture and is opaque.
    // Oddly enough, Minecraft somehow detects non-transparent pixels that were only used as temporary fill-in for a transparent area.
    // Creating a brand new, only ever has been transparent area produces a pure black pixel where a transparent pixel is supposed to be.

    public ClearGlass() {
        super(FabricBlockSettings
                .of(Materials.GLASS_MATERIAL)
                .sounds(BlockSoundGroup.GLASS)
                .nonOpaque() // .noCollision() - Allows for walking through blocks
                .allowsSpawning(ClearGlass::never)
                .solidBlock(ClearGlass::never)
                .suffocates(ClearGlass::never)
                .blockVision(ClearGlass::never)
                .strength(0.3F, 0.3F));
    }

    private static boolean never(BlockState blockState, BlockView blockView, BlockPos blockPos, EntityType<?> entityType) {
        return never(blockState, blockView, blockPos);
    }

    private static boolean never(BlockState blockState, BlockView blockView, BlockPos blockPos) {
        return false;
    }
}
