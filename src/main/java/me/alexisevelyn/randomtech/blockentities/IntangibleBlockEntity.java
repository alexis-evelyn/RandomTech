package me.alexisevelyn.randomtech.blockentities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public abstract class IntangibleBlockEntity extends BlockEntity {
    public IntangibleBlockEntity(BlockEntityType blockEntityType) {
        super(blockEntityType);
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView worldView, BlockPos pos) {
        if (world == null)
            return state.getOutlineShape(worldView, pos);

        // Close, but not exactly what I'm looking for, but it fakes the selective collision well
        PlayerEntity playerEntity = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 2, false);

        if (playerEntity == null)
            return state.getOutlineShape(worldView, pos);
        else if (playerEntity.isSneaking())
            return state.getOutlineShape(worldView, pos);

        return VoxelShapes.empty();
    }
}
