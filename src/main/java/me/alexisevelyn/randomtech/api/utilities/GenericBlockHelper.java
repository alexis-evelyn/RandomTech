package me.alexisevelyn.randomtech.api.utilities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

/**
 * The type Generic block helper.
 */
public class GenericBlockHelper {
    // Entity

    /**
     * Always boolean.
     *
     * @param blockState the block state
     * @param blockView  the block view
     * @param blockPos   the block pos
     * @param entityType the entity type
     * @return the boolean
     */
    public static boolean always(BlockState blockState, BlockView blockView, BlockPos blockPos, EntityType<?> entityType) {
        return always(blockState, blockView, blockPos);
    }

    /**
     * Never boolean.
     *
     * @param blockState the block state
     * @param blockView  the block view
     * @param blockPos   the block pos
     * @param entityType the entity type
     * @return the boolean
     */
    public static boolean never(BlockState blockState, BlockView blockView, BlockPos blockPos, EntityType<?> entityType) {
        return never(blockState, blockView, blockPos);
    }

    // No Entity

    /**
     * Always boolean.
     *
     * @param blockState the block state
     * @param blockView  the block view
     * @param blockPos   the block pos
     * @return the boolean
     */
    @SuppressWarnings("SameReturnValue")
    public static boolean always(BlockState blockState, BlockView blockView, BlockPos blockPos) {
        return true;
    }

    /**
     * Never boolean.
     *
     * @param blockState the block state
     * @param blockView  the block view
     * @param blockPos   the block pos
     * @return the boolean
     */
    @SuppressWarnings("SameReturnValue")
    public static boolean never(BlockState blockState, BlockView blockView, BlockPos blockPos) {
        return false;
    }
}
