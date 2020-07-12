package me.alexisevelyn.randomtech.blockentities;

import me.alexisevelyn.randomtech.utility.BlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class IntangibleDarkGlassBlockEntity extends BlockEntity {
    public IntangibleDarkGlassBlockEntity() {
        super(BlockEntities.INTANGIBLE_DARK_GLASS);
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView worldView, BlockPos pos, ShapeContext context) {
        if (world == null)
            return state.getOutlineShape(worldView, pos);

        // Close, but not exactly what I'm looking for
        PlayerEntity playerEntity = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 10, false);

        if (playerEntity != null)
            return state.getOutlineShape(worldView, pos);

        return VoxelShapes.empty();
    }
}
