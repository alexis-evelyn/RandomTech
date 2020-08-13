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
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemTransferHelper {
    public static List<BlockPos> getTransferrableNeighbors(@NotNull GenericCable ourCable, @NotNull World world, @NotNull BlockPos position, @NotNull ItemStack itemStack) {
        ArrayList<BlockPos> neighbors = new ArrayList<>();

        neighbors.add(CalculationHelper.addVectors(position, Direction.NORTH.getVector()));
        neighbors.add(CalculationHelper.addVectors(position, Direction.SOUTH.getVector()));
        neighbors.add(CalculationHelper.addVectors(position, Direction.EAST.getVector()));
        neighbors.add(CalculationHelper.addVectors(position, Direction.WEST.getVector()));
        neighbors.add(CalculationHelper.addVectors(position, Direction.UP.getVector()));
        neighbors.add(CalculationHelper.addVectors(position, Direction.DOWN.getVector()));

        // Remove if Not Interfaceable Neighbor
        neighbors.removeIf(neighbor -> !ourCable.isInterfacing(world.getBlockState(neighbor)));

        return neighbors;
    }

    @NotNull
    public static ItemStack transferItemStacks(@NotNull Inventory neighborInventory, int neighborSlot, @NotNull ItemStack ourItemStack) {
        ItemStack neighborStack = retrieveItemStack(neighborInventory, neighborSlot);

        if (neighborStack == null)
            return ourItemStack;

        if (neighborStack.getItem().equals(ourItemStack.getItem()))
            return addStacks(neighborStack, ourItemStack);

        if (neighborStack.isEmpty()) {
            neighborStack = ourItemStack.copy();
            ourItemStack.setCount(0);

            setStack(neighborInventory, neighborStack, neighborSlot);
            return ourItemStack;
        }

        return ourItemStack;
    }

    @NotNull
    public static ItemStack addStacks(@NotNull ItemStack neighborStack, @NotNull ItemStack ourItemStack) {
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

        inventory.setStack(slot, itemStack);
    }

    @Nullable
    public static ItemStack retrieveItemStack(@NotNull Inventory inventory, int slot) {
        if (inventory.size() <= slot)
            return null;

        return inventory.getStack(slot);
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
    public static ItemCableBlockEntity getCableBlockEntity(@NotNull World world, BlockPos blockPos) {
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

    public static void attemptTransferToContainer(@NotNull GenericCable ourCable, @NotNull World world, @NotNull BlockPos position, @NotNull ItemStack itemStack) {
        List<BlockPos> neighbors = ItemTransferHelper.getTransferrableNeighbors(ourCable, world, position, itemStack);

        for (BlockPos neighbor : neighbors) {
            BlockEntity blockEntity = world.getBlockEntity(neighbor);
            Block block = world.getBlockState(neighbor).getBlock();

            // TODO: Turn the inventory handler to a helper class
            if (blockEntity instanceof Inventory) {

            }

            if (block instanceof InventoryProvider) {

            }
        }
    }
}
