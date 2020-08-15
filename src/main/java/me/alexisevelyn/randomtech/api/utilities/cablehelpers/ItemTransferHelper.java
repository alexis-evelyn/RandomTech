package me.alexisevelyn.randomtech.api.utilities.cablehelpers;

import me.alexisevelyn.randomtech.api.blocks.cables.GenericCable;
import me.alexisevelyn.randomtech.api.utilities.CalculationHelper;
import me.alexisevelyn.randomtech.blockentities.cables.ItemCableBlockEntity;
import me.alexisevelyn.randomtech.blocks.cables.ItemCable;
import me.alexisevelyn.randomtech.inventories.ItemCableInventory;
import net.minecraft.block.Block;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemTransferHelper {
    public static List<BlockPos> getTransferrableNeighbors(@NotNull GenericCable ourCable, @NotNull World world, @NotNull BlockPos ourPosition, @NotNull ItemStack itemStack) {
        ArrayList<BlockPos> neighbors = new ArrayList<>();

        neighbors.add(CalculationHelper.addVectors(ourPosition, Direction.NORTH.getVector()));
        neighbors.add(CalculationHelper.addVectors(ourPosition, Direction.SOUTH.getVector()));
        neighbors.add(CalculationHelper.addVectors(ourPosition, Direction.EAST.getVector()));
        neighbors.add(CalculationHelper.addVectors(ourPosition, Direction.WEST.getVector()));
        neighbors.add(CalculationHelper.addVectors(ourPosition, Direction.UP.getVector()));
        neighbors.add(CalculationHelper.addVectors(ourPosition, Direction.DOWN.getVector()));

        // Remove if Not Interfaceable Neighbor
        neighbors.removeIf(neighborPos -> {
            boolean isInterfaceable = ourCable.isInstanceOfInterfaceableBlock(world.getBlockState(neighborPos).getBlock(), world, neighborPos);
            boolean isValidSide = ourCable.isValidSide(world.getBlockState(neighborPos).getBlock(), world, neighborPos, CalculationHelper.getDirection(neighborPos, ourPosition));

            return !(isInterfaceable && isValidSide);
        });

        return neighbors;
    }

    @NotNull
    public static ItemStack transferItemStacks(@NotNull Inventory neighborInventory, int neighborSlot, @NotNull ItemStack ourItemStack) {
        ItemStack neighborStack = retrieveItemStack(neighborInventory, neighborSlot);

        if (neighborStack == null || ourItemStack.isEmpty())
            return ourItemStack;

        if (neighborStack.isEmpty()) {
            neighborStack = ourItemStack.copy();
            ourItemStack.setCount(0);

            setStack(neighborInventory, neighborStack, neighborSlot);
            return ourItemStack;
        }

        if (neighborStack.getItem().equals(ourItemStack.getItem()))
            return mergeStacks(neighborStack, ourItemStack);

        return ourItemStack;
    }

    @NotNull
    public static ItemStack mergeStacks(@NotNull ItemStack neighborStack, @NotNull ItemStack ourItemStack) {
        int neighborStackMaxCount = neighborStack.getMaxCount();
        int neighborStackCount = neighborStack.getCount();

        int ourItemStackCount = ourItemStack.getCount();

        if ((ourItemStackCount + neighborStackCount) > neighborStackMaxCount) {
            neighborStack.setCount(neighborStackMaxCount);
            ourItemStack.decrement(neighborStackMaxCount - neighborStackCount);
            return ourItemStack;
        }

        neighborStack.increment(ourItemStackCount);
        ourItemStack.setCount(0);
        return ourItemStack;
    }

    private static void setStack(@NotNull Inventory inventory, @NotNull ItemStack itemStack, int slot) {
        if (slot < 0)
            return;

        if (inventory.size() < slot)
            return;

        inventory.setStack(slot, itemStack.copy());
        itemStack.setCount(0);
    }

    @Nullable
    public static ItemStack retrieveItemStack(@NotNull Inventory inventory, int slot) {
        if (inventory.size() <= slot)
            return null;

        return inventory.getStack(slot);
    }

    public static boolean hasItemStack(@NotNull Inventory inventory, int slot) {
        ItemStack temporaryStack = retrieveItemStack(inventory, slot);

        if (temporaryStack == null)
            return false;

        return !temporaryStack.isEmpty();
    }

    // I might move this somewhere else
    @NotNull
    public static int[] getSlots(ItemCableInventory inventory) {
        int[] slots = inventory.getRealSlots(null);

        if (slots.length == 0)
            return new int[]{0};

        return slots;
    }

    @Nullable
    public static ItemCableBlockEntity getCableBlockEntity(@NotNull WorldAccess world, BlockPos blockPos) {
        BlockEntity neighborBlockEntity = world.getBlockEntity(blockPos);

        if (!(neighborBlockEntity instanceof ItemCableBlockEntity))
            return null;

        return (ItemCableBlockEntity) neighborBlockEntity;
    }

    @Nullable
    public static ItemCable getCableBlock(@NotNull World world, BlockPos blockPos) {
        Block neighborBlock = world.getBlockState(blockPos).getBlock();

        if (!(neighborBlock instanceof ItemCable))
            return null;

        return (ItemCable) neighborBlock;
    }

    public static void tryTransferToContainer(@NotNull GenericCable ourCable, @NotNull World world, @NotNull BlockPos position, @NotNull ItemStack itemStack) {
        List<BlockPos> neighbors = getTransferrableNeighbors(ourCable, world, position, itemStack);

        for (BlockPos neighbor : neighbors) {
            BlockEntity blockEntity = world.getBlockEntity(neighbor);
            Block block = world.getBlockState(neighbor).getBlock();

            if (isInventoryProvider(block)) {
                tryTransferToInventoryProvider(world, position, neighbor, (InventoryProvider) block, itemStack);
            } else if (isInventory(blockEntity)) {
                tryTransferToInventory(world, position, (Inventory) blockEntity, itemStack);
            }

            // Update Neighbors
            world.updateComparators(neighbor, world.getBlockState(neighbor).getBlock());
        }

        // Update Ourselves
        world.updateComparators(position, world.getBlockState(position).getBlock());
    }

    @SuppressWarnings("DuplicatedCode")
    private static void tryTransferToInventoryProvider(@NotNull World world, @NotNull BlockPos ourPosition, @NotNull BlockPos neighborPosition, @NotNull InventoryProvider inventoryProvider, @NotNull ItemStack itemStack) {
        // Block - These are supposed to have proper SidedInventories, but are not guaranteed to (even if they say they do).
        SidedInventory inventory = inventoryProvider.getInventory(world.getBlockState(neighborPosition), world, neighborPosition);
        Direction direction = CalculationHelper.getDirection(neighborPosition, ourPosition);

        // Inventory has no slots, don't continue further
        if (inventory.size() == 0)
            return;

        if (itemStack.isEmpty())
            return;

        for (int slot : inventory.getAvailableSlots(direction)) {
            if (itemStack.isEmpty())
                break;

            ItemStack temporaryStack = retrieveItemStack(inventory, slot);

            if (temporaryStack == null)
                break;

            // Check if inventory will accept item (only exists in sidedinventories)
            if (!inventory.canInsert(slot, itemStack, direction))
                continue;

            if (temporaryStack.isEmpty()) {
                setStack(inventory, itemStack, slot);
                continue;
            }

            if (itemStack.getItem() == temporaryStack.getItem())
                mergeStacks(temporaryStack, itemStack);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private static void tryTransferToInventory(@NotNull World world, @NotNull BlockPos position, @NotNull Inventory inventory, @NotNull ItemStack itemStack) {
        // Block Entity

        // Inventory has no slots, don't continue further
        if (inventory.size() == 0)
            return;

        if (itemStack.isEmpty())
            return;

        // May remove
        if (inventory.isEmpty()) {
            setStack(inventory, itemStack, 0);
            inventory.markDirty();
            return;
        }

        for (int slot = 0; slot < inventory.size(); slot++) {
            if (itemStack.isEmpty())
                break;

            ItemStack temporaryStack = retrieveItemStack(inventory, slot);

            if (temporaryStack == null)
                break;

            if (temporaryStack.isEmpty()) {
                setStack(inventory, itemStack, slot);
                continue;
            }

            if (itemStack.getItem() == temporaryStack.getItem())
                mergeStacks(temporaryStack, itemStack);
        }

        inventory.markDirty();
    }

    // Composter (Block) is an inventory provider
    // Only blocks are supposed to be inventory providers.
    public static boolean isInventoryProvider(Object object) {
        return object instanceof InventoryProvider;
    }

    // ChestBlockEntity (BlockEntity) and HopperBlockEntity (BlockEntity) is an Inventory
    // Only block entities are supposed to be inventories.
    public static boolean isInventory(Object object) {
        return object instanceof Inventory;
    }
}
