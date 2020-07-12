package me.alexisevelyn.randomtech.blocks.glass;

import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.nio.file.DirectoryStream;

public class BasePoweredGlass extends AbstractGlassBlock {
    public static final BooleanProperty POWERED = BooleanProperty.of("powered");
    public static final DirectionProperty SOURCE = DirectionProperty.of("source", Direction.Type.HORIZONTAL);

    public BasePoweredGlass(Settings settings) {
        super(settings);

        setDefaultState(getStateManager()
                .getDefaultState()
                .with(POWERED, false)
                .with(SOURCE, Direction.NORTH)
        );
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        // world.getReceivedStrongRedstonePower(pos);
        // world.getReceivedRedstonePower(pos);

        if (world.isReceivingRedstonePower(pos) == state.get(POWERED))
            return;

        // The problem with this is, the glass powers other glass and therefor cannot unpower
        // world.setBlockState(pos, state.cycle(POWERED));

        // This below code does not solve the issue either

        BlockPos directionVector = fromPos.subtract(pos);
        Direction direction = Direction.getFacing(directionVector.getX(), directionVector.getY(), directionVector.getZ());

        if (world.isEmittingRedstonePower(fromPos, direction)) {
            System.out.println("Power Coming From Direction: " + direction);
            world.setBlockState(pos, state.with(SOURCE, direction)); // For some reason, this is not setting
            world.setBlockState(pos, state.with(POWERED, true));
            return;
        }

        world.setBlockState(pos, state.with(POWERED, false));
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        if (state.get(POWERED) && world.getBlockState(pos.offset(SOURCE.)))
            return 15;

        return super.getWeakRedstonePower(state, world, pos, direction);
    }

    @Override
    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
//        if (state.get(POWERED))
//            return 15;

        return super.getStrongRedstonePower(state, world, pos, direction);
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        if (state.get(POWERED))
            return true;

        return super.emitsRedstonePower(state);
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return super.hasComparatorOutput(state);
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return super.getComparatorOutput(state, world, pos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED, SOURCE);
    }
}
