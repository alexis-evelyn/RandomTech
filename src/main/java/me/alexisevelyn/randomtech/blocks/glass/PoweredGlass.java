package me.alexisevelyn.randomtech.blocks.glass;

import me.alexisevelyn.randomtech.api.blocks.glass.BasePoweredGlass;
import me.alexisevelyn.randomtech.api.utilities.GenericBlockHelper;
import me.alexisevelyn.randomtech.utility.Materials;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

/**
 * The type Powered glass.
 */
public class PoweredGlass extends BasePoweredGlass {
    /**
     * Instantiates a new Powered glass.
     */
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

    /**
     * Gets opacity.
     *
     * @param state the state
     * @param world the world
     * @param pos   the pos
     * @return the opacity
     */
    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        if (state.get(POWER) > 0)
            return 0;

        return 15;
    }
}
