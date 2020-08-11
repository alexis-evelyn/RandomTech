package me.alexisevelyn.randomtech.api.blocks.cables;

import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.api.utilities.CalculationHelper;
import me.alexisevelyn.randomtech.blockentities.cables.ItemCableBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public abstract class GenericCable extends Block implements Waterloggable {
    public static EnumProperty<CableConnection> CABLE_CONNECTION_NORTH = EnumProperty.of("north", CableConnection.class, CableConnection.NONE, CableConnection.CABLE, CableConnection.INTERFACEABLE);
    public static EnumProperty<CableConnection> CABLE_CONNECTION_SOUTH = EnumProperty.of("south", CableConnection.class, CableConnection.NONE, CableConnection.CABLE, CableConnection.INTERFACEABLE);
    public static EnumProperty<CableConnection> CABLE_CONNECTION_EAST = EnumProperty.of("east", CableConnection.class, CableConnection.NONE, CableConnection.CABLE, CableConnection.INTERFACEABLE);
    public static EnumProperty<CableConnection> CABLE_CONNECTION_WEST = EnumProperty.of("west", CableConnection.class, CableConnection.NONE, CableConnection.CABLE, CableConnection.INTERFACEABLE);
    public static EnumProperty<CableConnection> CABLE_CONNECTION_UP = EnumProperty.of("up", CableConnection.class, CableConnection.NONE, CableConnection.CABLE, CableConnection.INTERFACEABLE);
    public static EnumProperty<CableConnection> CABLE_CONNECTION_DOWN = EnumProperty.of("down", CableConnection.class, CableConnection.NONE, CableConnection.CABLE, CableConnection.INTERFACEABLE);

    // Allows to be Waterlogged
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    private static final Vec3i northVector = Direction.NORTH.getVector();
    private static final Vec3i southVector = Direction.SOUTH.getVector();
    private static final Vec3i eastVector = Direction.EAST.getVector();
    private static final Vec3i westVector = Direction.WEST.getVector();
    private static final Vec3i upVector = Direction.UP.getVector();
    private static final Vec3i downVector = Direction.DOWN.getVector();

    public final int maxCount = 5000; // Integer.MAX_VALUE;

    private final VoxelShape OUTLINED_SHAPE;
    private final VoxelShape VISUAL_SHAPE;
    private final VoxelShape COLLISION_SHAPE;
    private final VoxelShape[] CULLING_SHAPES;

    @SuppressWarnings("unused")
    public GenericCable(@NotNull Settings settings) {
        this(settings, null, null, null, null);
    }

    @SuppressWarnings("unused")
    public GenericCable(@NotNull Settings settings, @Nullable VoxelShape genericShape) {
        this(settings, genericShape, genericShape, genericShape, null);
    }

    @SuppressWarnings("unused")
    public GenericCable(@NotNull Settings settings, @Nullable VoxelShape genericShape, @Nullable VoxelShape[] cullingShapes) {
        this(settings, genericShape, genericShape, genericShape, cullingShapes);
    }

    public GenericCable(@NotNull Settings settings, @Nullable VoxelShape outlinedShape, @Nullable VoxelShape visualShape, @Nullable VoxelShape collisionShape, @Nullable VoxelShape[] cullingShapes) {
        super(settings);

        this.setDefaultState(this.stateManager.getDefaultState()
                .with(CABLE_CONNECTION_NORTH, CableConnection.NONE)
                .with(CABLE_CONNECTION_SOUTH, CableConnection.NONE)
                .with(CABLE_CONNECTION_EAST, CableConnection.NONE)
                .with(CABLE_CONNECTION_WEST, CableConnection.NONE)
                .with(CABLE_CONNECTION_UP, CableConnection.NONE)
                .with(CABLE_CONNECTION_DOWN, CableConnection.NONE)
                .with(WATERLOGGED, false)
        );

        this.OUTLINED_SHAPE = outlinedShape;
        this.VISUAL_SHAPE = visualShape;
        this.COLLISION_SHAPE = collisionShape;
        this.CULLING_SHAPES = cullingShapes;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CABLE_CONNECTION_NORTH, CABLE_CONNECTION_SOUTH, CABLE_CONNECTION_EAST, CABLE_CONNECTION_WEST, CABLE_CONNECTION_UP, CABLE_CONNECTION_DOWN, WATERLOGGED);
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
        setupCableStates(world, pos, state);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        super.onBroken(world, pos, state);

        // Responsible for Visually Disconnecting Cables on Block Breaking
        setupCableStates(world, pos, state, true);
    }

    // This runs for every block update that occurs to the cables
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (state.get(WATERLOGGED)) {
            // Try to support generic fluids if possible
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        // Have Cable BlockEntity Only Move During Block Update
        tickCable(world, pos);

        // This sets up the cable blockstates for each cable
        return setupCableStates(world, pos, state);
    }

    // Override this to change the message text
    public Text getNoInterfaceableCablesText() {
        return new TranslatableText(Main.MODID + ".no_interfaceable_cables_found");
    }

    // Override this to change the message text
    public Text getCablePositionHeaderText() {
        return new TranslatableText(Main.MODID + ".cable_position_header");
    }

    // Override this to change the message text
    public Text getCablePositionText(BlockPos cablePos) {
        return new TranslatableText(Main.MODID + ".cable_position", cablePos.getX(), cablePos.getY(), cablePos.getZ());
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        // So player has to click with an empty hand. This makes it easy to place blocks against the cable.
        if (!player.getStackInHand(hand).getItem().equals(Items.AIR)) // This causes weird behavior. Try again later. --> player.getStackInHand(hand).getItem() instanceof BlockItem
            return ActionResult.PASS;

        // Only the server needs to bother with the connection of the network.
        // Success takes away the block placement sound, but also makes it where onUse is only called once.
        //  That's why the check for isClient is after checking if the hand is empty
        if (world.isClient)
            return ActionResult.SUCCESS;

        // We want to allow the player to access the gui if they are not sneaking
        if (!player.isSneaking())
            return ActionResult.PASS;

        List<BlockPos> allCables = getAllInterfacingCables(world, pos);

        if (allCables.size() == 0) {
            player.sendMessage(getNoInterfaceableCablesText(), false);
            return ActionResult.CONSUME;
        }

        player.sendMessage(getCablePositionHeaderText(), false);
        for (BlockPos cablePos : allCables) {
            player.sendMessage(getCablePositionText(cablePos), false);
        }

        return ActionResult.CONSUME;
    }

    public List<BlockPos> getAllInterfacingCables(WorldAccess world, BlockPos pos) {
        List<BlockPos> knownCables = getAllCables(world, pos);
        return getAllInterfacingCables(world, knownCables);
    }

    public List<BlockPos> getAllInterfacingCables(WorldAccess world, List<BlockPos> knownCables) {
        List<BlockPos> interfacingCables = new ArrayList<>();

        for (BlockPos cablePos : knownCables) {
            if (isInterfacing(world.getBlockState(cablePos)))
                interfacingCables.add(cablePos);
        }

        return interfacingCables;
    }

    public List<BlockPos> getAllCables(WorldAccess world, BlockPos pos) {
        Set<BlockPos> passedCables = new HashSet<>();
        List<BlockPos> knownCables = new ArrayList<>();

        visitNeighbors(world, pos, passedCables, knownCables::add, this.maxCount);
        return knownCables;
    }

    private void visitNeighbors(WorldAccess world, BlockPos pos, Set<BlockPos> passedCables, Consumer<BlockPos> visitor, int maxCount) {
        visitNeighbors(world, pos, passedCables, visitor, 0, maxCount);
    }

    private void visitNeighbors(WorldAccess world, BlockPos pos, Set<BlockPos> passedCables, Consumer<BlockPos> visitor, int counter, int maxCount) {
        if (counter >= maxCount)
            return;

        counter++;

        // This check is so I can avoid duplicate results provided from outside this function.
        if (!(pos instanceof BlockPos.Mutable))
            visitor.accept(pos);

        List<BlockPos> validNeighbors = getValidNeighbors(world, pos);

        for(BlockPos attachedNeighbor : validNeighbors) {
            if (!passedCables.contains(attachedNeighbor)) {
                passedCables.add(attachedNeighbor);

                // TODO: Figure out how to tail this recursion
                visitNeighbors(world, attachedNeighbor, passedCables, visitor, counter, maxCount);
            }
        }
    }

    public boolean isInterfacing(BlockState blockState) {
        return blockState.get(CABLE_CONNECTION_NORTH).equals(CableConnection.INTERFACEABLE) ||
                blockState.get(CABLE_CONNECTION_SOUTH).equals(CableConnection.INTERFACEABLE) ||
                blockState.get(CABLE_CONNECTION_EAST).equals(CableConnection.INTERFACEABLE) ||
                blockState.get(CABLE_CONNECTION_WEST).equals(CableConnection.INTERFACEABLE) ||
                blockState.get(CABLE_CONNECTION_UP).equals(CableConnection.INTERFACEABLE) ||
                blockState.get(CABLE_CONNECTION_DOWN).equals(CableConnection.INTERFACEABLE);
    }

    private List<BlockPos> getValidNeighbors(WorldAccess world, BlockPos pos) {
        BlockPos north, south, east, west, up, down;
        BlockState northBlockState, southBlockState, eastBlockState, westBlockState, upBlockState, downBlockState;
        boolean isNorthValid, isSouthValid, isEastValid, isWestValid, isUpValid, isDownValid;

        // Neighbor Positions
        north = CalculationHelper.addVectors(pos, northVector);
        south = CalculationHelper.addVectors(pos, southVector);
        east = CalculationHelper.addVectors(pos, eastVector);
        west = CalculationHelper.addVectors(pos, westVector);
        up = CalculationHelper.addVectors(pos, upVector);
        down = CalculationHelper.addVectors(pos, downVector);

        // Neighbor BlockStates
        northBlockState = world.getBlockState(north);
        southBlockState = world.getBlockState(south);
        eastBlockState = world.getBlockState(east);
        westBlockState = world.getBlockState(west);
        upBlockState = world.getBlockState(up);
        downBlockState = world.getBlockState(down);

        isNorthValid = isNeighborValidForContinuance(world, northBlockState, north);
        isSouthValid = isNeighborValidForContinuance(world, southBlockState, north);
        isEastValid = isNeighborValidForContinuance(world, eastBlockState, north);
        isWestValid = isNeighborValidForContinuance(world, westBlockState, north);
        isUpValid = isNeighborValidForContinuance(world, upBlockState, north);
        isDownValid = isNeighborValidForContinuance(world, downBlockState, north);

        List<BlockPos> cables = new ArrayList<>();
        if (isNorthValid) {
            cables.add(north);
        }

        if (isSouthValid) {
            cables.add(south);
        }

        if (isEastValid) {
            cables.add(east);
        }

        if (isWestValid) {
            cables.add(west);
        }

        if (isUpValid) {
            cables.add(up);
        }

        if (isDownValid) {
            cables.add(down);
        }

        return cables;
    }

    private boolean isNeighborValidForContinuance(WorldAccess world, BlockState neighborBlockState, BlockPos neighborPos) {
        if (!isInstanceOfCable(neighborBlockState.getBlock(), world, neighborPos))
            return false;

        CableConnection northConnection = neighborBlockState.get(CABLE_CONNECTION_NORTH);
        CableConnection southConnection = neighborBlockState.get(CABLE_CONNECTION_SOUTH);
        CableConnection eastConnection = neighborBlockState.get(CABLE_CONNECTION_EAST);
        CableConnection westConnection = neighborBlockState.get(CABLE_CONNECTION_WEST);
        CableConnection upConnection = neighborBlockState.get(CABLE_CONNECTION_UP);
        CableConnection downConnection = neighborBlockState.get(CABLE_CONNECTION_DOWN);

        int isViable = 0;
        isViable = getIsViable(northConnection, isViable);
        isViable = getIsViable(southConnection, isViable);
        isViable = getIsViable(eastConnection, isViable);
        isViable = getIsViable(westConnection, isViable);
        isViable = getIsViable(upConnection, isViable);
        isViable = getIsViable(downConnection, isViable);

        // If only one, then we are seeing our own cable. We need more than one.
        return isViable >= 2;
    }

    // This is so the IDE will shutup about duplicate code.
    private int getIsViable(CableConnection neighborConnection, int isViable) {
        if (neighborConnection.equals(CableConnection.CABLE) || neighborConnection.equals(CableConnection.INTERFACEABLE)) {
            isViable++;
        }

        return isViable;
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

    protected BlockState setupCableStates(WorldAccess world, BlockPos pos, BlockState state) {
        return setupCableStates(world, pos, state, false);
    }

    protected BlockState setupCableStates(WorldAccess world, BlockPos pos, BlockState state, boolean broken) {
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
        state = setCableState(state, downBlockState, CABLE_CONNECTION_DOWN, CABLE_CONNECTION_UP, world, pos, down, broken);

        return state;
    }

    // Used to visually indicate if waterlogged
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (this.OUTLINED_SHAPE == null)
            return super.getOutlineShape(state, world, pos, context);

        return this.OUTLINED_SHAPE;
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        if (this.CULLING_SHAPES == null)
            return super.getOutlineShape(state, world, pos, ShapeContext.absent());

        return this.CULLING_SHAPES[this.getShapeIndex(state)];
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (this.VISUAL_SHAPE == null)
            return super.getVisualShape(state, world, pos, context);

        return this.VISUAL_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (this.COLLISION_SHAPE == null)
            return super.getCollisionShape(state, world, pos, context);

        return this.COLLISION_SHAPE;
    }

    public int getShapeIndex(BlockState state) {
        return 0;
    }

    @NotNull
    public static List<BlockPos> dijkstraAlgorithm(@NotNull List<BlockPos> currentKnownCables, @NotNull BlockPos destinationBlockPos) {
        // TODO: Implement Search Algorithm Here
        // https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm

        return new ArrayList<>();
    }

    public void tickCable(WorldAccess world, BlockPos blockPos) {
        BlockEntity blockEntity = world.getBlockEntity(blockPos);

        if (!(blockEntity instanceof ItemCableBlockEntity))
            return;

        ItemCableBlockEntity itemCableBlockEntity = (ItemCableBlockEntity) blockEntity;
        itemCableBlockEntity.moveItemInNetwork();
    }
}
