package me.alexisevelyn.randomtech.utility.cablehelpers;

import me.alexisevelyn.randomtech.api.blocks.cables.GenericCable;
import me.alexisevelyn.randomtech.api.utilities.CalculationHelper;
import me.alexisevelyn.randomtech.blockentities.cables.ItemCableBlockEntity;
import me.alexisevelyn.randomtech.blocks.cables.ItemCable;
import me.alexisevelyn.randomtech.inventories.ItemCableInventory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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

/**
 * The type Item transfer helper.
 */
public class ItemTransferHelper {
    /**
     * Gets transferrable neighbors.
     *
     * @param ourCable    the our cable
     * @param world       the world
     * @param ourPosition the our position
     * @return the transferrable neighbors
     */
    public static List<BlockPos> getTransferrableNeighbors(@NotNull GenericCable ourCable, @NotNull World world, @NotNull BlockPos ourPosition) {
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
            boolean isValidSide = ourCable.isValidSide(world.getBlockState(neighborPos).getBlock(), world, neighborPos, CalculationHelper.getDirection(ourPosition, neighborPos));

            return !(isInterfaceable && isValidSide);
        });

        return neighbors;
    }

    /**
     * Transfer item stacks item stack.
     *
     * @param neighborInventory the neighbor inventory
     * @param neighborSlot      the neighbor slot
     * @param ourItemStack      the our item stack
     * @return the item stack
     */
    @NotNull
    public static ItemStack transferItemStacks(@NotNull Inventory neighborInventory, int neighborSlot, @NotNull ItemStack ourItemStack) {
        ItemStack neighborStack = retrieveItemStack(neighborInventory, neighborSlot);

        if (neighborStack == null || ourItemStack.isEmpty())
            return ourItemStack;

        if (neighborStack.isEmpty()) {
            setStack(neighborInventory, ourItemStack, neighborSlot);
            // ourItemStack.setCount(0); // Setting the count to zero allows clearing the itemstack from the original inventory

            return ourItemStack; // Return an Empty Stack to pass the isEmpty checks.
        }

        if (neighborStack.getItem().equals(ourItemStack.getItem()))
            return mergeStacks(neighborStack, ourItemStack);

        return ourItemStack;
    }

    /**
     * Merge stacks item stack.
     *
     * @param neighborStack the neighbor stack
     * @param ourItemStack  the our item stack
     * @return the item stack
     */
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
        return ItemStack.EMPTY;
    }

    /**
     * Sets stack.
     *
     * @param inventory the inventory
     * @param itemStack the item stack
     * @param slot      the slot
     */
    private static void setStack(@NotNull Inventory inventory, @NotNull ItemStack itemStack, int slot) {
        if (slot < 0)
            return;

        if (inventory.size() < slot)
            return;

        // This is written like this because Java likes to do funky things with instructions (apparently). - Video of Weird Behavior: https://youtu.be/dwmg8v3eE74
        ItemStack newItemStack = itemStack.copy();
        itemStack.setCount(0); // It would skip this instruction if I set the new instance of the itemstack in the composter's inventory and then set the count of the old stack to zero.

        // We copy the itemstack as it'll cause Quasi-Connected Stacks if We Share the Same Instance Across Inventories
        inventory.setStack(slot, newItemStack);
    }

    /**
     * Retrieve item stack item stack.
     *
     * @param inventory the inventory
     * @param slot      the slot
     * @return the item stack
     */
    @Nullable
    public static ItemStack retrieveItemStack(@NotNull Inventory inventory, int slot) {
        if (inventory.size() <= slot)
            return null;

        return inventory.getStack(slot);
    }

    /**
     * Has item stack boolean.
     *
     * @param inventory the inventory
     * @param slot      the slot
     * @return the boolean
     */
    public static boolean hasItemStack(@NotNull Inventory inventory, int slot) {
        ItemStack temporaryStack = retrieveItemStack(inventory, slot);

        if (temporaryStack == null)
            return false;

        return !temporaryStack.isEmpty();
    }

    /**
     * Get slots int [ ].
     *
     * @param inventory the inventory
     * @return the int [ ]
     */
    // I might move this somewhere else
    @NotNull
    public static int[] getSlots(ItemCableInventory inventory) {
        int[] slots = inventory.getRealSlots(null);

        if (slots.length == 0)
            return new int[]{};

        return slots;
    }

    /**
     * Gets cable block entity.
     *
     * @param world    the world
     * @param blockPos the block pos
     * @return the cable block entity
     */
    @Nullable
    public static ItemCableBlockEntity getCableBlockEntity(@NotNull WorldAccess world, BlockPos blockPos) {
        BlockEntity neighborBlockEntity = world.getBlockEntity(blockPos);

        if (!(neighborBlockEntity instanceof ItemCableBlockEntity))
            return null;

        return (ItemCableBlockEntity) neighborBlockEntity;
    }

    /**
     * Gets cable block.
     *
     * @param world    the world
     * @param blockPos the block pos
     * @return the cable block
     */
    @Nullable
    public static ItemCable getCableBlock(@NotNull World world, BlockPos blockPos) {
        Block neighborBlock = world.getBlockState(blockPos).getBlock();

        if (!(neighborBlock instanceof ItemCable))
            return null;

        return (ItemCable) neighborBlock;
    }

    /**
     * Try transfer to container.
     *
     * @param ourCable  the our cable
     * @param world     the world
     * @param position  the position
     * @param itemStack the item stack
     */
    public static void tryTransferToContainer(@NotNull GenericCable ourCable, @NotNull World world, @NotNull BlockPos position, @NotNull ItemStack itemStack) {
        List<BlockPos> neighbors = getTransferrableNeighbors(ourCable, world, position);

        for (BlockPos neighbor : neighbors) {
            BlockEntity blockEntity = world.getBlockEntity(neighbor);
            Block block = world.getBlockState(neighbor).getBlock();

            if (isInventoryProvider(block))
                tryTransferToInventoryProvider(world, position, neighbor, (InventoryProvider) block, itemStack);
            else if (isInventory(blockEntity))
                tryTransferToInventory(world, position, (Inventory) blockEntity, itemStack);

            // Update Neighbors
            world.updateComparators(neighbor, world.getBlockState(neighbor).getBlock());
        }

        // Update Ourselves
        world.updateComparators(position, world.getBlockState(position).getBlock());
    }

    /**
     * Try transfer to inventory provider.
     *
     * @param world             the world
     * @param ourPosition       the our position
     * @param neighborPosition  the neighbor position
     * @param inventoryProvider the inventory provider
     * @param itemStack         the item stack
     */
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

    /**
     * Try transfer to inventory.
     *
     * @param world     the world
     * @param position  the position
     * @param inventory the inventory
     * @param itemStack the item stack
     */
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

    /**
     * Is inventory provider boolean.
     *
     * @param object the object
     * @return the boolean
     */
    // Composter (Block) is an inventory provider
    // Only blocks are supposed to be inventory providers.
    public static boolean isInventoryProvider(Object object) {
        return object instanceof InventoryProvider;
    }

    /**
     * Is inventory boolean.
     *
     * @param object the object
     * @return the boolean
     */
    // ChestBlockEntity (BlockEntity) and HopperBlockEntity (BlockEntity) is an Inventory
    // Only block entities are supposed to be inventories.
    public static boolean isInventory(Object object) {
        return object instanceof Inventory;
    }

    @NotNull
    public static ItemStack findNonEmptyItemStack(ItemCableInventory inventory) {
        for (int slot : ItemTransferHelper.getSlots(inventory)) {
            ItemStack currentItemStack = retrieveItemStack(inventory, slot);

            if (currentItemStack != null && !currentItemStack.isEmpty()) {
                // Currently necessary to ensure all items get processed,
                // but may not be necessary in the future once proper cable traveling is implemented.
                inventory.setNeedsProcessing(true);

                return currentItemStack;
            }
        }

        return ItemStack.EMPTY;
    }

    public static boolean canReceiveStack(@NotNull ItemCable itemCable, @NotNull World world, @NotNull BlockPos blockPos, @NotNull ItemStack itemStack) {
        // Check filter here
        SidedInventory sidedInventory = itemCable.getInventory(world.getBlockState(blockPos), world, blockPos);

        if (!(sidedInventory instanceof ItemCableInventory))
            return false;

        ItemCableInventory itemCableInventory = (ItemCableInventory) sidedInventory;

        // If filter is not empty and does not contain the itemstack in question, then deny receiving
        if (filterDenied(itemStack, itemCableInventory, world, blockPos))
            return false;

        // For checking neighbors
        List<BlockPos> neighbors = getTransferrableNeighbors(itemCable, world, blockPos);

        return hasInsertableNeighbors(neighbors, world, blockPos, itemStack);
    }

    public static boolean filterDenied(@NotNull ItemStack itemStack, @NotNull ItemCableInventory itemCableInventory, @NotNull World world, @NotNull BlockPos blockPos) {
        // TODO: Add support for checking metadata such as nbt data.

        return !itemCableInventory.isFilterEmpty(null) && !itemCableInventory.filterContains(itemStack, null);
    }

    public static boolean hasInsertableNeighbors(@NotNull List<BlockPos> neighbors, @NotNull World world, @NotNull BlockPos ourPosition, @NotNull ItemStack itemStack) {
        for (BlockPos neighborPosition : neighbors) {
            BlockState blockState = world.getBlockState(neighborPosition);
            Block block = blockState.getBlock();
            BlockEntity blockEntity = world.getBlockEntity(neighborPosition);

            if (isInventoryProvider(block)) {
                Direction direction = CalculationHelper.getDirection(neighborPosition, ourPosition);

                if (canInsertIntoSidedInventory(((InventoryProvider) block).getInventory(blockState, world, neighborPosition), direction, itemStack))
                    return true;
            } else if (isInventory(blockEntity))
                return true;
        }

        return false;
    }

    public static boolean canInsertIntoSidedInventory(@NotNull SidedInventory sidedInventory, @Nullable Direction side, @NotNull ItemStack itemStack) {
        for (int slot : sidedInventory.getAvailableSlots(side))
            if (sidedInventory.canInsert(slot, itemStack, side))
                return true;

        return false;
    }
}
