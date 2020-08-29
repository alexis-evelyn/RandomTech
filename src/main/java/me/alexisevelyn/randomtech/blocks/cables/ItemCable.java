package me.alexisevelyn.randomtech.blocks.cables;

import me.alexisevelyn.randomtech.api.blocks.cables.GenericCable;
import me.alexisevelyn.randomtech.api.utilities.GenericBlockHelper;
import me.alexisevelyn.randomtech.blockentities.cables.ItemCableBlockEntity;
import me.alexisevelyn.randomtech.inventories.ItemCableInventory;
import me.alexisevelyn.randomtech.utility.Materials;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Random;

/**
 * The type Item cable.
 */
public class ItemCable extends GenericCable implements BlockEntityProvider, InventoryProvider {
    /**
     * Instantiates a new Item cable.
     *
     */
    public ItemCable() {
        this((float) (3.0/16), FabricBlockSettings
                .of(Materials.CABLE_MATERIAL)
                .sounds(BlockSoundGroup.GLASS)
                .nonOpaque() // Fixes xray issue. Also allows light pass through block
                .allowsSpawning(GenericBlockHelper::never) // Allows or denies spawning
                .solidBlock(GenericBlockHelper::never) // ??? - Seems to have no apparent effect
                .suffocates(GenericBlockHelper::never) // Suffocates player
                .blockVision(GenericBlockHelper::never) // Blocks Vision inside of block
                .strength(0.3F, 0.3F)
                .ticksRandomly());
    }

    /**
     *
     * @param radius
     * @param settings
     */
    public ItemCable(float radius, @NotNull Settings settings) {
        super(radius, settings);
    }

    /**
     * For Block Form
     *
     * @param state
     * @param world
     * @param pos
     * @param tintIndex
     * @return
     */
    public static int getEdgeColor(BlockState state, BlockRenderView world, BlockPos pos, int tintIndex) {
        return Color.RED.getRGB();
    }

    /**
     * For Item Form
     *
     * @param itemStack
     * @param tintIndex
     * @return
     */
    public static int getEdgeColor(ItemStack itemStack, int tintIndex) {
        return Color.RED.getRGB();
    }

    /**
     * On state replaced.
     *
     * @param state    the state
     * @param world    the world
     * @param pos      the pos
     * @param newState the new state
     * @param moved    the moved
     */
    // Used to make the cable drop its contents when broken
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        BlockEntity blockEntity = world.getBlockEntity(pos); // Retrieve and store block entity for further processing
        super.onStateReplaced(state, world, pos, newState, moved); // Mark Block Entity to Be Removed

        // If new block state is the same type of block as our own, return
        if (state.isOf(newState.getBlock()))
            return;

        // If block entity is not expected type, return
        if (!(blockEntity instanceof ItemCableBlockEntity))
            return;

        // Drop and Scatter Inventory
        ItemCableBlockEntity itemCableBlockEntity = (ItemCableBlockEntity) blockEntity;
        ItemScatterer.spawn(world, pos, itemCableBlockEntity.getInventory(state, world, pos));
    }

    /**
     * Random tick.
     *
     * @param state  the state
     * @param world  the world
     * @param pos    the pos
     * @param random the random
     */
    // For Backend Use (Can be used for visual/auditory stuff too) - Server Side and Client Side
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
    }

    /**
     * Random display tick.
     *
     * @param state  the state
     * @param world  the world
     * @param pos    the pos
     * @param random the random
     */
    // For Visual Use Only - Client Side Only
    @Override
    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
    }

    /**
     * Is instance of cable boolean.
     *
     * @param block    the block
     * @param world    the world
     * @param blockPos the block pos
     * @return the boolean
     */
    @Override
    public boolean isInstanceOfCable(Block block, WorldAccess world, BlockPos blockPos) {
        return block instanceof ItemCable;
    }

    /**
     * Is instance of interfaceable block boolean.
     *
     * @param block    the block
     * @param world    the world
     * @param blockPos the block pos
     * @return the boolean
     */
    @Override
    public boolean isInstanceOfInterfaceableBlock(Block block, WorldAccess world, BlockPos blockPos) {
        // Cables get checked beforehand and are not a part of this check.
        if (block instanceof GenericCable)
            return false;

        if (block instanceof InventoryProvider)
            return true;

        // Checking the instance of also inherently checks if the block entity is null
        return world.getBlockEntity(blockPos) instanceof Inventory;
    }

    /**
     * Is valid side boolean.
     *
     * @param block    the block
     * @param world    the world
     * @param blockPos the block pos
     * @param side     the side
     * @return the boolean
     */
    @Override
    public boolean isValidSide(Block block, WorldAccess world, BlockPos blockPos, Direction side) {
        if (block instanceof InventoryProvider) {
            SidedInventory inventory = ((InventoryProvider) block).getInventory(world.getBlockState(blockPos), world, blockPos);
            int[] slots = inventory.getAvailableSlots(side);

            if (slots.length == 0 && block instanceof ComposterBlock && (side.equals(Direction.UP) || side.equals(Direction.DOWN))) {
                // Cause the Composter is Special
                // When the composter has composted, it removes the bottom side from being valid.
                // Vanilla hoppers can still pull from it, but it breaks any SidedInventory aware code. That being said, the Composter's slot is 0.
                slots = new int[]{0};
            }

            // Is there no universal check if a slot is an input slot or not?
//            for (int slot : slots) {
//                if (inventory.canInsert(slot, null, side)) {
//                    return true;
//                }
//            }

            return slots.length > 0;
        }

        BlockEntity blockEntity = world.getBlockEntity(blockPos);

        // There's no generic way to check a hopper's side for insertion validity?
        if (blockEntity instanceof HopperBlockEntity)
            return !side.equals(Direction.UP);

        return blockEntity instanceof Inventory;
    }

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
    @Override
    public ActionResult openGui(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (!(blockEntity instanceof ItemCableBlockEntity))
            return ActionResult.FAIL;

        player.openHandledScreen((ItemCableBlockEntity) blockEntity);
        return ActionResult.CONSUME;
    }

    /**
     * Create block entity block entity.
     *
     * @param world the world
     * @return the block entity
     */
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        // I don't believe createBlockEntity is called while the world is null.
        if (world == null)
            return null;

        return new ItemCableBlockEntity();
    }

    /**
     * Has comparator output boolean.
     *
     * @param state the state
     * @return the boolean
     */
    @SuppressWarnings("deprecation")
    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
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
    @SuppressWarnings("deprecation") // https://discordapp.com/channels/507304429255393322/523633816078647296/740720404800209041
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        SidedInventory inventory = getInventory(state, world, pos);

        // Ignore Filter Slots
        if (inventory instanceof ItemCableInventory)
            return ((ItemCableInventory) inventory).calculateComparatorOutput();

        // Default Behavior
        return ScreenHandler.calculateComparatorOutput(inventory);
    }

    /**
     * Gets inventory.
     *
     * @param state the state
     * @param world the world
     * @param pos   the pos
     * @return the inventory
     */
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof InventoryProvider)
            return ((InventoryProvider) blockEntity).getInventory(state, world, pos);

        return null;
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
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        // Attaching a new cable or interfaceable block will block update the current cables and may expand the network.
        if (world instanceof World)
            tickCable((World) world, pos);

        // This sets up the cable blockstates for each cable
        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    /**
     * Tick cable.
     *
     * @param world    the world
     * @param blockPos the block pos
     */
    public void tickCable(World world, BlockPos blockPos) {
        BlockEntity blockEntity = world.getBlockEntity(blockPos);

        if (!(blockEntity instanceof ItemCableBlockEntity))
            return;

        ItemCableBlockEntity itemCableBlockEntity = (ItemCableBlockEntity) blockEntity;
        itemCableBlockEntity.moveItemInNetwork(world);
    }
}
