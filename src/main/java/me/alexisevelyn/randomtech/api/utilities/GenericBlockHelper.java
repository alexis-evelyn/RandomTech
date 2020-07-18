package me.alexisevelyn.randomtech.api.utilities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class GenericBlockHelper {
    // Entity

    public static boolean always(BlockState blockState, BlockView blockView, BlockPos blockPos, EntityType<?> entityType) {
        return always(blockState, blockView, blockPos);
    }

    public static boolean never(BlockState blockState, BlockView blockView, BlockPos blockPos, EntityType<?> entityType) {
        return never(blockState, blockView, blockPos);
    }

    // No Entity

    public static boolean always(BlockState blockState, BlockView blockView, BlockPos blockPos) {
        return true;
    }

    public static boolean never(BlockState blockState, BlockView blockView, BlockPos blockPos) {
        return false;
    }
}
