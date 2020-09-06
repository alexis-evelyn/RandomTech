package me.alexisevelyn.randomtech.api.blocks.cables;

import com.google.common.collect.Maps;
import me.alexisevelyn.randomtech.api.Main;
import me.alexisevelyn.randomtech.api.utilities.CalculationHelper;
import me.alexisevelyn.randomtech.api.utilities.pathfinding.dijkstra.*;
import net.minecraft.block.*;
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
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
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

import java.util.*;
import java.util.function.Consumer;

/**
 * The type Generic cable.
 */
public abstract class GenericCable extends ConnectingBlock implements Waterloggable {
    private static final EnumProperty<CableConnection> CABLE_CONNECTION_NORTH = EnumProperty.of("north", CableConnection.class, CableConnection.NONE, CableConnection.CABLE, CableConnection.INTERFACEABLE);
    private static final EnumProperty<CableConnection> CABLE_CONNECTION_SOUTH = EnumProperty.of("south", CableConnection.class, CableConnection.NONE, CableConnection.CABLE, CableConnection.INTERFACEABLE);
    private static final EnumProperty<CableConnection> CABLE_CONNECTION_EAST = EnumProperty.of("east", CableConnection.class, CableConnection.NONE, CableConnection.CABLE, CableConnection.INTERFACEABLE);
    private static final EnumProperty<CableConnection> CABLE_CONNECTION_WEST = EnumProperty.of("west", CableConnection.class, CableConnection.NONE, CableConnection.CABLE, CableConnection.INTERFACEABLE);
    private static final EnumProperty<CableConnection> CABLE_CONNECTION_UP = EnumProperty.of("up", CableConnection.class, CableConnection.NONE, CableConnection.CABLE, CableConnection.INTERFACEABLE);
    private static final EnumProperty<CableConnection> CABLE_CONNECTION_DOWN = EnumProperty.of("down", CableConnection.class, CableConnection.NONE, CableConnection.CABLE, CableConnection.INTERFACEABLE);

    // Allows to be Waterlogged
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    private static final Vec3i northVector = Direction.NORTH.getVector();
    private static final Vec3i southVector = Direction.SOUTH.getVector();
    private static final Vec3i eastVector = Direction.EAST.getVector();
    private static final Vec3i westVector = Direction.WEST.getVector();
    private static final Vec3i upVector = Direction.UP.getVector();
    private static final Vec3i downVector = Direction.DOWN.getVector();

    private static final Map<Direction, EnumProperty<CableConnection>> FACING_PROPERTIES = Util.make(Maps.newEnumMap(Direction.class), (enumMap) -> {
        enumMap.put(Direction.NORTH, CABLE_CONNECTION_NORTH);
        enumMap.put(Direction.EAST, CABLE_CONNECTION_EAST);
        enumMap.put(Direction.SOUTH, CABLE_CONNECTION_SOUTH);
        enumMap.put(Direction.WEST, CABLE_CONNECTION_WEST);
        enumMap.put(Direction.UP, CABLE_CONNECTION_UP);
        enumMap.put(Direction.DOWN, CABLE_CONNECTION_DOWN);
    });

    private static final int maxCount = 5000; // Integer.MAX_VALUE;

    /**
     *
     * @param radius
     * @param settings
     */
    protected GenericCable(float radius, @NotNull Settings settings) {
        super(radius, settings);

        this.setDefaultState(this.stateManager.getDefaultState()
                .with(CABLE_CONNECTION_NORTH, CableConnection.NONE)
                .with(CABLE_CONNECTION_SOUTH, CableConnection.NONE)
                .with(CABLE_CONNECTION_EAST, CableConnection.NONE)
                .with(CABLE_CONNECTION_WEST, CableConnection.NONE)
                .with(CABLE_CONNECTION_UP, CableConnection.NONE)
                .with(CABLE_CONNECTION_DOWN, CableConnection.NONE)
                .with(WATERLOGGED, false)
        );
    }

    @Override
    public int getConnectionMask(BlockState state) {
        int i = 0;

        for(int j = 0; j < Direction.values().length; ++j) {
            if (isConnected(state, Direction.values()[j])) {
                i |= 1 << j;
            }
        }

        return i;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.getOutlineShape(state, world, pos, context);
    }

    /**
     * Makes it where light levels aren't blocked
     *
     * @param state
     * @param world
     * @param pos
     * @return
     */
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return 0;
    }

    /**
     * Append properties.
     *
     * @param builder the builder
     */
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CABLE_CONNECTION_NORTH, CABLE_CONNECTION_SOUTH, CABLE_CONNECTION_EAST, CABLE_CONNECTION_WEST, CABLE_CONNECTION_UP, CABLE_CONNECTION_DOWN, WATERLOGGED);
    }

    /**
     * Is instance of cable boolean.
     *
     * @param block    the block
     * @param world    the world
     * @param blockPos the block pos
     * @return the boolean
     */
    // This is to make sure that we only connect to the proper cable instance.
    // If you really want, you can connect to any cable by just checking if it's an instance of GenericCable.
    // Don't expect it to look right though if the other cable doesn't do the same.
    public abstract boolean isInstanceOfCable(Block block, WorldAccess world, BlockPos blockPos);

    /**
     * Is instance of interfaceable block boolean.
     *
     * @param block    the block
     * @param world    the world
     * @param blockPos the block pos
     * @return the boolean
     */
    // This is to make sure that we only connect to blocks that are supposed to connect to this cable.
    public abstract boolean isInstanceOfInterfaceableBlock(Block block, WorldAccess world, BlockPos blockPos);

    /**
     * Is valid side boolean.
     *
     * @param block    the block
     * @param world    the world
     * @param blockPos the block pos
     * @param side     the side
     * @return the boolean
     */
    // This is to make sure that we only connect to blocks that are supposed to connect to this cable.
    public abstract boolean isValidSide(Block block, WorldAccess world, BlockPos blockPos, Direction side);

    /**
     * Allows for Opening Gui
     *
     * @param state
     * @param world
     * @param pos
     * @param player
     * @param hand
     * @param hit
     * @return
     */
    public abstract ActionResult openGui(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit);

    /**
     * On placed.
     *
     * @param world     the world
     * @param pos       the pos
     * @param state     the state
     * @param placer    the placer
     * @param itemStack the item stack
     */
    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        // Responsible for Visually Connecting Cables Together
        setupCableStates(world, pos, state);
    }

    /**
     * On broken.
     *
     * @param world the world
     * @param pos   the pos
     * @param state the state
     */
    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        super.onBroken(world, pos, state);

        // Responsible for Visually Disconnecting Cables on Block Breaking
        setupCableStates(world, pos, state, true);
    }

    /**
     * Gets state for neighbor update.
     *
     * @param state     the state
     * @param direction the direction
     * @param newState  the new state
     * @param world     the world
     * @param pos       the pos
     * @param posFrom   the pos from
     * @return the state for neighbor update
     */
    // This runs for every block update that occurs to the cables
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        // Try to support generic fluids if possible
        if (state.get(WATERLOGGED))
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));

        // This sets up the cable blockstates for each cable
        return setupCableStates(world, pos, state);
    }

    /**
     * Gets no interfaceable cables text.
     *
     * @return the no interfaceable cables text
     */
    // Override this to change the message text
    public Text getNoInterfaceableCablesText() {
        return new TranslatableText(Main.MODID + ".no_interfaceable_cables_found");
    }

    /**
     * Gets cable position header text.
     *
     * @return the cable position header text
     */
    // Override this to change the message text
    public Text getCablePositionHeaderText() {
        return new TranslatableText(Main.MODID + ".cable_position_header");
    }

    /**
     * Gets cable position text.
     *
     * @param cablePos the cable pos
     * @return the cable position text
     */
    // Override this to change the message text
    public Text getCablePositionText(BlockPos cablePos) {
        return new TranslatableText(Main.MODID + ".cable_position", cablePos.getX(), cablePos.getY(), cablePos.getZ());
    }

    /**
     * On use action result.
     *
     * @param state  the state
     * @param world  the world
     * @param pos    the pos
     * @param player the player
     * @param hand   the hand
     * @param hit    the hit
     * @return the action result
     */
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
            return openGui(state, world, pos, player, hand, hit);

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

    /**
     * Gets all interfacing cables.
     *
     * @param world the world
     * @param pos   the pos
     * @return the all interfacing cables
     */
    public List<BlockPos> getAllInterfacingCables(@NotNull WorldAccess world, @NotNull BlockPos pos) {
        List<BlockPos> knownCables = getAllCables(world, pos);

        // Fixes problem with single cable setups
        if (knownCables.size() == 0) {
            knownCables.add(pos);
        }

        return getAllInterfacingCables(world, knownCables);
    }

    /**
     * Gets all interfacing cables.
     *
     * @param world       the world
     * @param knownCables the known cables
     * @return the all interfacing cables
     */
    public List<BlockPos> getAllInterfacingCables(@NotNull WorldAccess world, @NotNull List<BlockPos> knownCables) {
        List<BlockPos> interfacingCables = new ArrayList<>();

        for (BlockPos cablePos : knownCables) {
            if (isInterfacing(world.getBlockState(cablePos)))
                interfacingCables.add(cablePos);
        }

        return interfacingCables;
    }

    /**
     * Gets all cables.
     *
     * @param world the world
     * @param pos   the pos
     * @return the all cables
     */
    public List<BlockPos> getAllCables(@NotNull WorldAccess world, @NotNull BlockPos pos) {
        Set<BlockPos> passedCables = new HashSet<>();
        List<BlockPos> knownCables = new ArrayList<>();

        visitNeighbors(world, pos, passedCables, knownCables::add, maxCount);
        return knownCables;
    }

    /**
     * Visit neighbors.
     *
     * @param world        the world
     * @param pos          the pos
     * @param passedCables the passed cables
     * @param visitor      the visitor
     * @param maxCount     the max count
     */
    private void visitNeighbors(@NotNull WorldAccess world, @NotNull BlockPos pos, @NotNull Set<BlockPos> passedCables, @NotNull Consumer<BlockPos> visitor, int maxCount) {
        visitNeighbors(world, pos, passedCables, visitor, 0, maxCount);
    }

    /**
     * Visit neighbors.
     *
     * @param world        the world
     * @param pos          the pos
     * @param passedCables the passed cables
     * @param visitor      the visitor
     * @param startCounter the counter
     * @param maxCount     the max count
     */
    private void visitNeighbors(@NotNull WorldAccess world, @NotNull BlockPos pos, @NotNull Set<BlockPos> passedCables, @NotNull Consumer<BlockPos> visitor, int startCounter, int maxCount) {
        int counter = startCounter;

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

                // TODO (Important): Figure out how to tail this recursion
                visitNeighbors(world, attachedNeighbor, passedCables, visitor, counter, maxCount);
            }
        }
    }

    /**
     * Is interfacing boolean.
     *
     * @param blockState the block state
     * @return the boolean
     */
    public boolean isInterfacing(@NotNull BlockState blockState) {
        return blockState.get(CABLE_CONNECTION_NORTH).equals(CableConnection.INTERFACEABLE) ||
                blockState.get(CABLE_CONNECTION_SOUTH).equals(CableConnection.INTERFACEABLE) ||
                blockState.get(CABLE_CONNECTION_EAST).equals(CableConnection.INTERFACEABLE) ||
                blockState.get(CABLE_CONNECTION_WEST).equals(CableConnection.INTERFACEABLE) ||
                blockState.get(CABLE_CONNECTION_UP).equals(CableConnection.INTERFACEABLE) ||
                blockState.get(CABLE_CONNECTION_DOWN).equals(CableConnection.INTERFACEABLE);
    }

    public boolean isConnected(@NotNull BlockState blockState, @NotNull Direction direction) {
        return isConnected(blockState, FACING_PROPERTIES.get(direction));
    }

    private boolean isConnected(@NotNull BlockState blockState, @NotNull Property<CableConnection> cableConnectionProperty) {
        return blockState.get(cableConnectionProperty).equals(CableConnection.INTERFACEABLE) || blockState.get(cableConnectionProperty).equals(CableConnection.CABLE);
    }

    /**
     * Gets valid neighbors.
     *
     * @param world the world
     * @param pos   the pos
     * @return the valid neighbors
     */
    private List<BlockPos> getValidNeighbors(@NotNull WorldAccess world, @NotNull BlockPos pos) {
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

        boolean isNorthValid = isNeighborValidForContinuance(world, northBlockState, north);
        boolean isSouthValid = isNeighborValidForContinuance(world, southBlockState, north);
        boolean isEastValid = isNeighborValidForContinuance(world, eastBlockState, north);
        boolean isWestValid = isNeighborValidForContinuance(world, westBlockState, north);
        boolean isUpValid = isNeighborValidForContinuance(world, upBlockState, north);
        boolean isDownValid = isNeighborValidForContinuance(world, downBlockState, north);

        List<BlockPos> cables = new ArrayList<>();
        if (isNorthValid)
            cables.add(north);

        if (isSouthValid)
            cables.add(south);

        if (isEastValid)
            cables.add(east);

        if (isWestValid)
            cables.add(west);

        if (isUpValid)
            cables.add(up);

        if (isDownValid)
            cables.add(down);

        return cables;
    }

    /**
     * Is neighbor valid for continuance boolean.
     *
     * @param world              the world
     * @param neighborBlockState the neighbor block state
     * @param neighborPos        the neighbor pos
     * @return the boolean
     */
    private boolean isNeighborValidForContinuance(@NotNull WorldAccess world, @NotNull BlockState neighborBlockState, @NotNull BlockPos neighborPos) {
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

    /**
     * Gets is viable.
     *
     * @param neighborConnection the neighbor connection
     * @param isViable           the is viable
     * @return the is viable
     */
    // This is so the IDE will shutup about duplicate code.
    private int getIsViable(@NotNull CableConnection neighborConnection, int isViable) {
        int viability = isViable;

        if (neighborConnection.equals(CableConnection.CABLE) || neighborConnection.equals(CableConnection.INTERFACEABLE)) {
            viability++;
        }

        return viability;
    }

    /**
     * Sets cable state.
     *
     * @param ourBlockState      the our block state
     * @param neighborBlockState the neighbor block state
     * @param ourProperty        the our property
     * @param neighborProperty   the neighbor property
     * @param world              the world
     * @param ourPos             the our pos
     * @param neighborPos        the neighbor pos
     * @param broken             the broken
     * @return the cable state
     */
    protected BlockState setCableState(BlockState ourBlockState, BlockState neighborBlockState, EnumProperty<CableConnection> ourProperty, EnumProperty<CableConnection> neighborProperty, WorldAccess world, BlockPos ourPos, BlockPos neighborPos, boolean broken) {
        if (isInstanceOfCable(ourBlockState.getBlock(), world, ourPos) && !broken) {
            if (isInstanceOfCable(neighborBlockState.getBlock(), world, neighborPos)) {
                // Connected to Cable
                world.setBlockState(neighborPos, neighborBlockState.with(neighborProperty, CableConnection.CABLE), 0x1); // Flag 0x1 = 0b0000001 which means Propagate Changes. More info in net.minecraft.world.ModifiableWorld
                world.setBlockState(ourPos, ourBlockState.with(ourProperty, CableConnection.CABLE), 0x1);
            } else if (isInstanceOfInterfaceableBlock(neighborBlockState.getBlock(), world, neighborPos) && isValidSide(neighborBlockState.getBlock(), world, neighborPos, CalculationHelper.getDirection(ourPos, neighborPos))) {
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

    /**
     * Sets cable states.
     *
     * @param world the world
     * @param pos   the pos
     * @param state the state
     * @return the cable states
     */
    protected BlockState setupCableStates(WorldAccess world, BlockPos pos, BlockState state) {
        return setupCableStates(world, pos, state, false);
    }

    /**
     * Sets cable states.
     *
     * @param world  the world
     * @param pos    the pos
     * @param state  the state
     * @param broken the broken
     * @return the cable states
     */
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
        BlockState modifiedState = setCableState(state, northBlockState, CABLE_CONNECTION_NORTH, CABLE_CONNECTION_SOUTH, world, pos, north, broken);
        modifiedState = setCableState(modifiedState, southBlockState, CABLE_CONNECTION_SOUTH, CABLE_CONNECTION_NORTH, world, pos, south, broken);
        modifiedState = setCableState(modifiedState, eastBlockState, CABLE_CONNECTION_EAST, CABLE_CONNECTION_WEST, world, pos, east, broken);
        modifiedState = setCableState(modifiedState, westBlockState, CABLE_CONNECTION_WEST, CABLE_CONNECTION_EAST, world, pos, west, broken);
        modifiedState = setCableState(modifiedState, upBlockState, CABLE_CONNECTION_UP, CABLE_CONNECTION_DOWN, world, pos, up, broken);
        modifiedState = setCableState(modifiedState, downBlockState, CABLE_CONNECTION_DOWN, CABLE_CONNECTION_UP, world, pos, down, broken);

        return modifiedState;
    }

    /**
     * Gets fluid state.
     *
     * @param state the state
     * @return the fluid state
     */
    // Used to visually indicate if waterlogged
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    /**
     * Dijkstra algorithm vertex path.
     *
     * @param currentKnownCables    the current known cables
     * @param startingBlockPosition the starting block position
     * @param endingBlockPosition   the ending block position
     * @return the vertex path
     */
    @NotNull
    public static VertexPath dijkstraAlgorithm(List<BlockPos> currentKnownCables, BlockPos startingBlockPosition, BlockPos endingBlockPosition) {
        List<Vertex> nodes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        Vertex startingPosition = null;
        Vertex endingPosition = null;

        for(BlockPos currentCable : currentKnownCables) {
            Vertex location = new Vertex(currentCable, getCableName(currentCable));
            nodes.add(location);

            // Add Starting Position to Variable To Track Later
            if (currentCable.equals(startingBlockPosition))
                startingPosition = location;

            // Add Ending Position to Variable To Track Later
            if (currentCable.equals(endingBlockPosition))
                endingPosition = location;
        }
        
        if (startingPosition == null || endingPosition == null)
            return new VertexPath();

        // TODO (Important): Add Edges
        addLane(nodes, edges, "Lane_0", nodes.indexOf(startingPosition), nodes.indexOf(endingPosition), 1);
        addLane(nodes, edges, "Lane_1", nodes.indexOf(endingPosition), nodes.indexOf(startingPosition), 1);

        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(graph);

        dijkstraAlgorithm.execute(startingPosition);

        return dijkstraAlgorithm.getPath(endingPosition);
    }

    /**
     * Add lane.
     *
     * @param nodes       the nodes
     * @param edges       the edges
     * @param laneId      the lane id
     * @param source      the source
     * @param destination the destination
     * @param weight      the weight
     */
    private static void addLane(List<Vertex> nodes, List<Edge> edges, String laneId, int source, int destination, int weight) {
        Edge lane = new Edge(laneId, nodes.get(source), nodes.get(destination), weight);
        edges.add(lane);
    }

    /**
     * Gets cable name.
     *
     * @param blockPos the block pos
     * @return the cable name
     */
    private static String getCableName(BlockPos blockPos) {
        return "(" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() + ")";
    }
}
