package me.alexisevelyn.randomtech.api.blocks.cables;

import me.alexisevelyn.randomtech.api.utilities.CalculationHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public abstract class GenericCable extends Block {
    public static EnumProperty<CableConnection> CABLE_CONNECTION_NORTH = EnumProperty.of("north", CableConnection.class, CableConnection.NONE, CableConnection.CABLE, CableConnection.INTERFACEABLE);
    public static EnumProperty<CableConnection> CABLE_CONNECTION_SOUTH = EnumProperty.of("south", CableConnection.class, CableConnection.NONE, CableConnection.CABLE, CableConnection.INTERFACEABLE);
    public static EnumProperty<CableConnection> CABLE_CONNECTION_EAST = EnumProperty.of("east", CableConnection.class, CableConnection.NONE, CableConnection.CABLE, CableConnection.INTERFACEABLE);
    public static EnumProperty<CableConnection> CABLE_CONNECTION_WEST = EnumProperty.of("west", CableConnection.class, CableConnection.NONE, CableConnection.CABLE, CableConnection.INTERFACEABLE);
    public static EnumProperty<CableConnection> CABLE_CONNECTION_UP = EnumProperty.of("up", CableConnection.class, CableConnection.NONE, CableConnection.CABLE, CableConnection.INTERFACEABLE);
    public static EnumProperty<CableConnection> CABLE_CONNECTION_DOWN = EnumProperty.of("down", CableConnection.class, CableConnection.NONE, CableConnection.CABLE, CableConnection.INTERFACEABLE);

    private static final Vec3i northVector = Direction.NORTH.getVector();
    private static final Vec3i southVector = Direction.SOUTH.getVector();
    private static final Vec3i eastVector = Direction.EAST.getVector();
    private static final Vec3i westVector = Direction.WEST.getVector();
    private static final Vec3i upVector = Direction.UP.getVector();
    private static final Vec3i downVector = Direction.DOWN.getVector();

    public GenericCable(Settings settings) {
        super(settings);

        this.setDefaultState(this.stateManager.getDefaultState()
                .with(CABLE_CONNECTION_NORTH, CableConnection.NONE)
                .with(CABLE_CONNECTION_SOUTH, CableConnection.NONE)
                .with(CABLE_CONNECTION_EAST, CableConnection.NONE)
                .with(CABLE_CONNECTION_WEST, CableConnection.NONE)
                .with(CABLE_CONNECTION_UP, CableConnection.NONE)
                .with(CABLE_CONNECTION_DOWN, CableConnection.NONE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CABLE_CONNECTION_NORTH, CABLE_CONNECTION_SOUTH, CABLE_CONNECTION_EAST, CABLE_CONNECTION_WEST, CABLE_CONNECTION_UP, CABLE_CONNECTION_DOWN);
    }

    // This is to make sure that we only connect to the proper cable instance.
    // If you really want, you can connect to any cable by just checking if it's an instance of GenericCable.
    // Don't expect it to look right though if the other cable doesn't do the same.
    public abstract boolean isInstanceOfCable(Block block, WorldAccess world, BlockPos blockPos);

    // This is to make sure that we only connect to blocks that are supposed to connect to this cable.
    public abstract boolean isInstanceOfInterfaceableBlock(Block block, WorldAccess world, BlockPos blockPos);

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        // Responsible for Visually Connecting Cables Together
        setupCableStates(world, pos, state, false);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        super.onBroken(world, pos, state);

        // Responsible for Visually Disconnecting Cables on Block Breaking
        setupCableStates(world, pos, state, true);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        // No Way To Get A Block Update and Not Rely on a Ticker?
        setupCableStates(world, pos, state, false);
    }

    protected BlockState setCableState(BlockState ourBlockState, BlockState neighborBlockState, EnumProperty<CableConnection> ourProperty, EnumProperty<CableConnection> neighborProperty, WorldAccess world, BlockPos ourPos, BlockPos neighborPos, boolean broken) {
        if (isInstanceOfCable(ourBlockState.getBlock(), world, ourPos) && !broken) {
            if (isInstanceOfCable(neighborBlockState.getBlock(), world, neighborPos)) {
                // Connected to Cable
                world.setBlockState(neighborPos, neighborBlockState.with(neighborProperty, CableConnection.CABLE), 0x1); // Flag 0x1 = 0b0000001 which means Propagate Changes. More info in net.minecraft.world.ModifiableWorld
                world.setBlockState(ourPos, ourBlockState.with(ourProperty, CableConnection.CABLE), 0x1);
            } else if (isInstanceOfInterfaceableBlock(neighborBlockState.getBlock(), world, neighborPos)) {
                // Connected to Interfaceable Block
                world.setBlockState(ourPos, ourBlockState.with(ourProperty, CableConnection.INTERFACEABLE), 0x1);
            } else {
                // Removed Interfaceable Block
                world.setBlockState(ourPos, ourBlockState.with(ourProperty, CableConnection.NONE), 0x1);
            }
        } else if (isInstanceOfCable(neighborBlockState.getBlock(), world, neighborPos)) {
            // Broken Our Cable
            world.setBlockState(neighborPos, neighborBlockState.with(neighborProperty, CableConnection.NONE), 0x1);
        }

        // Return Our Latest Changes So Changes Can Stack
        return world.getBlockState(ourPos);
    }

    protected void setupCableStates(WorldAccess world, BlockPos pos, BlockState state, boolean broken) {
        // Neighbor Positions
        BlockPos north = CalculationHelper.addVectors(pos, northVector);
        BlockPos south = CalculationHelper.addVectors(pos, southVector);
        BlockPos east = CalculationHelper.addVectors(pos, eastVector);
        BlockPos west = CalculationHelper.addVectors(pos, westVector);
        BlockPos up = CalculationHelper.addVectors(pos, upVector);
        BlockPos down = CalculationHelper.addVectors(pos, downVector);

        // Neighbor BlockStates
        BlockState northBlockState = world.getBlockState(north);
        BlockState southBlockState = world.getBlockState(south);
        BlockState eastBlockState = world.getBlockState(east);
        BlockState westBlockState = world.getBlockState(west);
        BlockState upBlockState = world.getBlockState(up);
        BlockState downBlockState = world.getBlockState(down);

        // Set Cable States
        state = setCableState(state, northBlockState, CABLE_CONNECTION_NORTH, CABLE_CONNECTION_SOUTH, world, pos, north, broken);
        state = setCableState(state, southBlockState, CABLE_CONNECTION_SOUTH, CABLE_CONNECTION_NORTH, world, pos, south, broken);
        state = setCableState(state, eastBlockState, CABLE_CONNECTION_EAST, CABLE_CONNECTION_WEST, world, pos, east, broken);
        state = setCableState(state, westBlockState, CABLE_CONNECTION_WEST, CABLE_CONNECTION_EAST, world, pos, west, broken);
        state = setCableState(state, upBlockState, CABLE_CONNECTION_UP, CABLE_CONNECTION_DOWN, world, pos, up, broken);
        setCableState(state, downBlockState, CABLE_CONNECTION_DOWN, CABLE_CONNECTION_UP, world, pos, down, broken);
    }
}
