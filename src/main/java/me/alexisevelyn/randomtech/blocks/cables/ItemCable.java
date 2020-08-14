package me.alexisevelyn.randomtech.blocks.cables;

import me.alexisevelyn.randomtech.api.blocks.cables.GenericCable;
import me.alexisevelyn.randomtech.api.utilities.GenericBlockHelper;
import me.alexisevelyn.randomtech.blockentities.cables.ItemCableBlockEntity;
import me.alexisevelyn.randomtech.utility.Materials;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class ItemCable extends GenericCable implements BlockEntityProvider, InventoryProvider {
    // Generic Instantiation of ItemCable with Default Shape
    public ItemCable() {
        this(null);
    }

    // I may create more than one cable with this class, so I'm putting extra constructors here
    // Generic Instantiation of ItemCable with Custom Shape
    public ItemCable(@Nullable VoxelShape genericShape) {
        this(FabricBlockSettings
                .of(Materials.CABLE_MATERIAL)
                .sounds(BlockSoundGroup.GLASS)
                .nonOpaque() // Fixes xray issue. Also allows light pass through block
                .allowsSpawning(GenericBlockHelper::never) // Allows or denies spawning
                .solidBlock(GenericBlockHelper::never) // ??? - Seems to have no apparent effect
                .suffocates(GenericBlockHelper::never) // Suffocates player
                .blockVision(GenericBlockHelper::never) // Blocks Vision inside of block
                .strength(0.3F, 0.3F)
                .ticksRandomly(), genericShape);
    }

    // For customizing block settings while only supplying one shape
    public ItemCable(@NotNull Settings settings, @Nullable VoxelShape genericShape) {
        this(settings, genericShape, genericShape, genericShape, null);
    }

    // For full control over cable shapes
    public ItemCable(@NotNull Settings settings, @Nullable VoxelShape outlinedShape, @Nullable VoxelShape visualShape, @Nullable VoxelShape collisionShape, @Nullable VoxelShape[] cullingShapes) {
        super(settings, outlinedShape, visualShape, collisionShape, cullingShapes);
    }

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

    // For Backend Use (Can be used for visual/auditory stuff too) - Server Side and Client Side
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
    }

    // For Visual Use Only - Client Side Only
    @Override
    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
    }

    @Override
    public boolean isInstanceOfCable(Block block, WorldAccess world, BlockPos blockPos) {
        return block instanceof ItemCable;
    }

    @Override
    public boolean isInstanceOfInterfaceableBlock(Block block, WorldAccess world, BlockPos blockPos) {
        if (block instanceof InventoryProvider)
            return true;

        // Checking the instance of also inherently checks if the block entity is null
        return world.getBlockEntity(blockPos) instanceof Inventory;
    }

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

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new ItemCableBlockEntity();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation") // https://discordapp.com/channels/507304429255393322/523633816078647296/740720404800209041
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(getInventory(state, world, pos));
    }

    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof InventoryProvider)
            return ((InventoryProvider) blockEntity).getInventory(state, world, pos);

        return null;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        // Have Cable BlockEntity Only Move During Block Update - Bad Idea. Causes Headaches Later
        // tickCable(world, pos);

        // This sets up the cable blockstates for each cable
        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    public void tickCable(WorldAccess world, BlockPos blockPos) {
        BlockEntity blockEntity = world.getBlockEntity(blockPos);

        if (!(blockEntity instanceof ItemCableBlockEntity))
            return;

        ItemCableBlockEntity itemCableBlockEntity = (ItemCableBlockEntity) blockEntity;
        itemCableBlockEntity.moveItemInNetwork();
    }
}
