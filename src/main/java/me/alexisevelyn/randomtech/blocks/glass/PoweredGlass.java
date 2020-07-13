package me.alexisevelyn.randomtech.blocks.glass;

import me.alexisevelyn.randomtech.utility.GenericBlockHelper;
import me.alexisevelyn.randomtech.utility.Materials;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class PoweredGlass extends BasePoweredGlass {
   public PoweredGlass() {
        super(FabricBlockSettings
                .of(Materials.GLASS_MATERIAL)
                .sounds(BlockSoundGroup.GLASS)
                .nonOpaque() // .noCollision() - Allows for walking through blocks
                .allowsSpawning(GenericBlockHelper::never)
                .solidBlock(GenericBlockHelper::never)
                .suffocates(GenericBlockHelper::never)
                .blockVision(GenericBlockHelper::never)
                .strength(0.3F, 0.3F));
    }

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        if (state.get(POWER) > 0)
            return 0;

        return 15;
    }
}
