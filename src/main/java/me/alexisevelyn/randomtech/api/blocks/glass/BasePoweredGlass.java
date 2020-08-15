package me.alexisevelyn.randomtech.api.blocks.glass;

import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

/**
 * The type Base powered glass.
 */
public abstract class BasePoweredGlass extends AbstractGlassBlock {
    public static final IntProperty POWER = IntProperty.of("power", 0, 15);

    /**
     * Instantiates a new Base powered glass.
     *
     * @param settings the settings
     */
    public BasePoweredGlass(Settings settings) {
        super(settings);

        setDefaultState(getStateManager()
                .getDefaultState()
                .with(POWER, 0)
        );
    }

    /**
     * Neighbor update.
     *
     * @param state   the state
     * @param world   the world
     * @param pos     the pos
     * @param block   the block
     * @param fromPos the from pos
     * @param notify  the notify
     */
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        // world.getReceivedStrongRedstonePower(pos);
        // world.getReceivedRedstonePower(pos);

        // The problem with this is, the glass powers other glass and therefor cannot unpower
//        if (world.isReceivingRedstonePower(pos) != state.get(POWERED))
//            world.setBlockState(pos, state.cycle(POWERED));

        world.setBlockState(pos, state.with(POWER, world.getReceivedRedstonePower(pos)));
    }

    /**
     * Gets weak redstone power.
     *
     * @param state     the state
     * @param world     the world
     * @param pos       the pos
     * @param direction the direction
     * @return the weak redstone power
     */
    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        if (state.get(POWER) > 0)
            return state.get(POWER) - 1;

        return 0;
    }

    /**
     * Gets strong redstone power.
     *
     * @param state     the state
     * @param world     the world
     * @param pos       the pos
     * @param direction the direction
     * @return the strong redstone power
     */
    @Override
    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return super.getStrongRedstonePower(state, world, pos, direction);
    }

    /**
     * Emits redstone power boolean.
     *
     * @param state the state
     * @return the boolean
     */
    @Override
    public boolean emitsRedstonePower(BlockState state) {
        if (state.get(POWER) > 0)
            return true;

        return super.emitsRedstonePower(state);
    }

    /**
     * Has comparator output boolean.
     *
     * @param state the state
     * @return the boolean
     */
    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return super.hasComparatorOutput(state);
    }

    /**
     * Gets comparator output.
     *
     * @param state the state
     * @param world the world
     * @param pos   the pos
     * @return the comparator output
     */
    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return super.getComparatorOutput(state, world, pos);
    }

    /**
     * Append properties.
     *
     * @param builder the builder
     */
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWER);
    }

    /**
     * Gets redstone strength.
     *
     * @param blockState the block state
     * @return the redstone strength
     */
    public int getRedstoneStrength(BlockState blockState) {
        return blockState.get(POWER);
    }
}
