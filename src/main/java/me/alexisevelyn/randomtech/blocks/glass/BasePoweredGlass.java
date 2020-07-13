package me.alexisevelyn.randomtech.blocks.glass;

import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.HashMap;

public class BasePoweredGlass extends AbstractGlassBlock {
    public static final IntProperty POWER = IntProperty.of("power", 0, 15);

    public static final HashMap<Direction, BooleanProperty> directionBooleanFinder = new HashMap<>();

    public BasePoweredGlass(Settings settings) {
        super(settings);

        setDefaultState(getStateManager()
                .getDefaultState()
                .with(POWER, 0)
        );
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        // world.getReceivedStrongRedstonePower(pos);
        // world.getReceivedRedstonePower(pos);

        // The problem with this is, the glass powers other glass and therefor cannot unpower
//        if (world.isReceivingRedstonePower(pos) != state.get(POWERED))
//            world.setBlockState(pos, state.cycle(POWERED));

        world.setBlockState(pos, state.with(POWER, world.getReceivedRedstonePower(pos)));
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        if (state.get(POWER) > 0)
            return state.get(POWER) - 1;

        return 0;
    }

    @Override
    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return super.getStrongRedstonePower(state, world, pos, direction);
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        if (state.get(POWER) > 0)
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
        builder.add(POWER);
    }

    public int getRedstoneStrength(BlockState blockState) {
        return blockState.get(POWER);
    }
}
