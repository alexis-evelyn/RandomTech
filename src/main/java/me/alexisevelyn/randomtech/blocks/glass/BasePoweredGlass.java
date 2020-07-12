package me.alexisevelyn.randomtech.blocks.glass;

import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.HashMap;

public class BasePoweredGlass extends AbstractGlassBlock {
    public static final BooleanProperty POWERED = BooleanProperty.of("powered");

    public static final BooleanProperty SOURCE_NORTH = BooleanProperty.of("source_north");
    public static final BooleanProperty SOURCE_SOUTH = BooleanProperty.of("source_south");
    public static final BooleanProperty SOURCE_EAST = BooleanProperty.of("source_east");
    public static final BooleanProperty SOURCE_WEST = BooleanProperty.of("source_west");
    public static final BooleanProperty SOURCE_UP = BooleanProperty.of("source_up");
    public static final BooleanProperty SOURCE_DOWN = BooleanProperty.of("source_down");

    public static final HashMap<Direction, BooleanProperty> directionBooleanFinder = new HashMap<>();

    public BasePoweredGlass(Settings settings) {
        super(settings);

        setDefaultState(getStateManager()
                .getDefaultState()
                .with(POWERED, false)
                .with(SOURCE_NORTH, false)
                .with(SOURCE_SOUTH, false)
                .with(SOURCE_EAST, false)
                .with(SOURCE_WEST, false)
                .with(SOURCE_UP, false)
                .with(SOURCE_DOWN, false)
        );

        directionBooleanFinder.put(Direction.NORTH, SOURCE_NORTH);
        directionBooleanFinder.put(Direction.SOUTH, SOURCE_SOUTH);
        directionBooleanFinder.put(Direction.EAST, SOURCE_EAST);
        directionBooleanFinder.put(Direction.WEST, SOURCE_WEST);
        directionBooleanFinder.put(Direction.UP, SOURCE_UP);
        directionBooleanFinder.put(Direction.DOWN, SOURCE_DOWN);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        // world.getReceivedStrongRedstonePower(pos);
        // world.getReceivedRedstonePower(pos);

        // The problem with this is, the glass powers other glass and therefor cannot unpower
//        if (world.isReceivingRedstonePower(pos) != state.get(POWERED))
//            world.setBlockState(pos, state.cycle(POWERED));

        if (!world.isReceivingRedstonePower(pos)) {
            world.setBlockState(pos, state.with(POWERED, false));
            setAllSources(state, world, pos);
            return;
        }

//        BlockPos directionVector = fromPos.subtract(pos);
//        Direction direction = Direction.getFacing(directionVector.getX(), directionVector.getY(), directionVector.getZ());

        System.out.println("Received Power!!!");
        setAllSources(state, world, pos);
    }

    protected void setSource(BlockState state, World world, BlockPos pos, Direction direction) {
        if (world.getEmittedRedstonePower(pos, direction) > 0) {
            world.setBlockState(pos, state.cycle(directionBooleanFinder.get(direction)));
            System.out.println("Success Set Source: " + direction);
        } else {
            System.out.println("Failed Set Source: " + direction);
        }
    }

    protected void setAllSources(BlockState state, World world, BlockPos pos) {
        setSource(state, world, pos, Direction.NORTH);
        setSource(state, world, pos, Direction.SOUTH);
        setSource(state, world, pos, Direction.EAST);
        setSource(state, world, pos, Direction.WEST);
        setSource(state, world, pos, Direction.UP);
        setSource(state, world, pos, Direction.DOWN);
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        if (state.get(POWERED))
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
        builder.add(POWERED, SOURCE_NORTH, SOURCE_SOUTH, SOURCE_EAST, SOURCE_WEST, SOURCE_UP, SOURCE_DOWN);
    }
}
