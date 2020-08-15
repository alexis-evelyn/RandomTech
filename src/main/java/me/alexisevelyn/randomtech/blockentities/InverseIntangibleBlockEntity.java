package me.alexisevelyn.randomtech.blockentities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

/**
 * The type Inverse intangible block entity.
 */
public abstract class InverseIntangibleBlockEntity extends BlockEntity {
    /**
     * Instantiates a new Inverse intangible block entity.
     *
     * @param blockEntityType the block entity type
     */
    public InverseIntangibleBlockEntity(BlockEntityType blockEntityType) {
        super(blockEntityType);
    }

    /**
     * Gets collision shape.
     *
     * @param state     the state
     * @param worldView the world view
     * @param pos       the pos
     * @return the collision shape
     */
    public VoxelShape getCollisionShape(BlockState state, BlockView worldView, BlockPos pos) {
        if (world == null)
            return state.getOutlineShape(worldView, pos);

        // Close, but not exactly what I'm looking for, but it fakes the selective collision well
        PlayerEntity playerEntity = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 2, false);

        if (playerEntity == null)
            return VoxelShapes.empty();
        else if (playerEntity.isSneaking())
            return VoxelShapes.empty();

        return state.getOutlineShape(worldView, pos);
    }
}
