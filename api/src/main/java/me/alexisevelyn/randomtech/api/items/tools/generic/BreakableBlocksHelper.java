package me.alexisevelyn.randomtech.api.items.tools.generic;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public interface BreakableBlocksHelper {
    /**
     *
     * @param state
     * @param player
     * @param world
     * @param pos
     * @return
     */
    boolean canBreakUnbreakableBlock(BlockState state, PlayerEntity player, BlockView world, BlockPos pos);

    /**
     *
     * @param state
     * @param player
     * @param world
     * @param pos
     * @return
     */
    float getUnbreakableBlockDifficultyMultiplier(BlockState state, PlayerEntity player, BlockView world, BlockPos pos);
}
