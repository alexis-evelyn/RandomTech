package me.alexisevelyn.randomtech.api.utilities;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.apiguardian.api.API;

/**
 * Some helper methods when creating a block using {@link FabricBlockSettings}
 */
public class GenericBlockHelper {
    /**
     * Mark some value as always true.
     *
     * Used in {@link FabricBlockSettings#allowsSpawning(AbstractBlock.TypedContextPredicate)}
     *
     * Internally calls {@link #always(BlockState, BlockView, BlockPos)}
     *
     * @param blockState the block's BlockState
     * @param blockView  the world the block is currently in
     * @param blockPos   the block's position in the world
     * @param entityType the type of entity in question
     * @return true
     */
    @API(status = API.Status.STABLE)
    public static boolean always(BlockState blockState, BlockView blockView, BlockPos blockPos, EntityType<?> entityType) {
        return always(blockState, blockView, blockPos);
    }

    /**
     * Mark some value as always false.
     *
     * Used in {@link FabricBlockSettings#allowsSpawning(AbstractBlock.TypedContextPredicate)}
     *
     * Internally calls {@link #never(BlockState, BlockView, BlockPos)}
     *
     * @param blockState the block's BlockState
     * @param blockView  the world the block is currently in
     * @param blockPos   the block's position in the world
     * @param entityType the type of entity in question
     * @return false
     */
    @API(status = API.Status.STABLE)
    public static boolean never(BlockState blockState, BlockView blockView, BlockPos blockPos, EntityType<?> entityType) {
        return never(blockState, blockView, blockPos);
    }

    /**
     * Mark some value as always true.
     *
     * Used in {@link FabricBlockSettings#solidBlock(AbstractBlock.ContextPredicate)}, {@link FabricBlockSettings#suffocates(AbstractBlock.ContextPredicate)}, and {@link FabricBlockSettings#blockVision(AbstractBlock.ContextPredicate)}
     *
     * @param blockState the block's BlockState
     * @param blockView  the world the block is currently in
     * @param blockPos   the block's position in the world
     * @return true
     */
    @API(status = API.Status.STABLE)
    @SuppressWarnings("SameReturnValue")
    public static boolean always(BlockState blockState, BlockView blockView, BlockPos blockPos) {
        return true;
    }

    /**
     * Mark some value as always false.
     *
     * Used in {@link FabricBlockSettings#solidBlock(AbstractBlock.ContextPredicate)}, {@link FabricBlockSettings#suffocates(AbstractBlock.ContextPredicate)}, and {@link FabricBlockSettings#blockVision(AbstractBlock.ContextPredicate)}
     *
     * @param blockState the block's BlockState
     * @param blockView  the world the block is currently in
     * @param blockPos   the block's position in the world
     * @return false
     */
    @API(status = API.Status.STABLE)
    @SuppressWarnings("SameReturnValue")
    public static boolean never(BlockState blockState, BlockView blockView, BlockPos blockPos) {
        return false;
    }
}
