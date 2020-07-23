package me.alexisevelyn.randomtech.api.utilities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MiningManager {
    // TODO: Check if block requires silk touch and if tool is dead.
    public static boolean canMine(PlayerEntity playerEntity, BlockState blockState, World world, BlockPos blockPos) {
        boolean canMine = playerEntity.isUsingEffectiveTool(blockState);

        return !(blockState.getHardness(world, blockPos) < 0) && canMine;
    }
}
