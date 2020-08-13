package me.alexisevelyn.randomtech.blockentities.cables;

import me.alexisevelyn.randomtech.api.blocks.cables.GenericCable;
import me.alexisevelyn.randomtech.api.utilities.CalculationHelper;
import me.alexisevelyn.randomtech.api.utilities.pathfinding.dijkstra.Vertex;
import me.alexisevelyn.randomtech.api.utilities.pathfinding.dijkstra.VertexPath;
import me.alexisevelyn.randomtech.blocks.cables.ItemCable;
import me.alexisevelyn.randomtech.inventories.ItemCableInventory;
import me.alexisevelyn.randomtech.utility.BlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemCableBlockEntity extends BlockEntity implements InventoryProvider {
    private final ItemCableInventory inventory;
    private final ItemCable ourCable = new ItemCable();

    public ItemCableBlockEntity() {
        super(BlockEntities.ITEM_CABLE);
        inventory = new ItemCableInventory();

        // Jump start any cable networks when they are loaded in.
        // moveItemInNetwork();
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);

        // Save Inventory
        Inventories.toTag(tag, this.inventory.getInventory());

        return tag;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);

        // Setup Inventory
        this.inventory.setInventory(DefaultedList.ofSize(this.inventory.size(), ItemStack.EMPTY));
        Inventories.fromTag(tag, this.inventory.getInventory());
    }

    // Example Command For Testing: /data merge block ~ ~ ~-4 {Items: [{Slot: 9b, id: "minecraft:bedrock", Count: 1b}]}
    public void moveItemInNetwork() {
        // Ensure World is Not Null (It always is for a little bit on world load)
        if (world == null)
            return;

        List<BlockPos> currentKnownCables = ourCable.getAllCables(world, pos); // Will be used for the search algorithm later
        List<BlockPos> currentInterfaceableBlocks = ourCable.getAllInterfacingCables(world, currentKnownCables); // Our endpoints to choose from in the search algorithm

        // If nowhere to go, just return (we still need to validate filters and stuff)
        if (currentInterfaceableBlocks.size() == 0)
            return;

        // Look for first non-empty slot
        ItemStack currentItemStack = null;
        for (int slot : getSlots()) {
            currentItemStack = retrieveItemStack(slot);

            if (currentItemStack != null)
                break;
        }

        // If no itemstacks found, just return
        if (currentItemStack == null)
            return;

        // We choose a slot ahead of time so we can figure out what slot needs to be transfered
        VertexPath path = findPath(currentItemStack, currentKnownCables, currentInterfaceableBlocks);

        // No Path Found, just Return
        if (path.size() == 0)
            return;

        Vertex nextVertex = path.getNext();

        // We are at the end of the path (if any path) if this if statement equals true
        if (nextVertex == null || !(nextVertex.getPosition() instanceof BlockPos)) {
            attemptTransferToContainer(world, pos, currentItemStack);
            return;
        }

        // Get Destination's Block Entity
        ItemCableBlockEntity neighborCableEntity = (ItemCableBlockEntity) world.getBlockEntity((BlockPos) nextVertex.getPosition());
        int[] neighborSlots = neighborCableEntity.getSlots();

        // Neighbor doesn't have any slots
        if (neighborSlots == null)
            return;

        // Attempt to Add Items to Neighbor Slots
        for (int slot : neighborSlots) {
            currentItemStack = transferItemStacks(neighborCableEntity, slot, currentItemStack);
            world.updateComparators(neighborCableEntity.getPos(), world.getBlockState(neighborCableEntity.getPos()).getBlock());
        }

        world.updateComparators(pos, world.getBlockState(pos).getBlock());
    }

    private void attemptTransferToContainer(World world, BlockPos position, ItemStack itemStack) {
        List<BlockPos> neighbors = getTransferrableNeighbors(world, position, itemStack);

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

    // TODO: Turn to helper function
    private List<BlockPos> getTransferrableNeighbors(World world, BlockPos position, ItemStack itemStack) {
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

    // TODO: Helper Class
    @NotNull
    private ItemStack transferItemStacks(@NotNull ItemCableBlockEntity neighborBlockEntity, int neighborSlot, @NotNull ItemStack ourItemStack) {
        ItemStack neighborStack = neighborBlockEntity.retrieveItemStack(neighborSlot);

        if (neighborStack == null)
            return ourItemStack;

        if (neighborStack.getItem().equals(ourItemStack.getItem()))
            return addStacks(neighborStack, ourItemStack);

        if (neighborStack.isEmpty()) {
            neighborStack = ourItemStack.copy();
            ourItemStack.setCount(0);

            neighborBlockEntity.setStack(neighborStack, neighborSlot);
            return ourItemStack;
        }

        return ourItemStack;
    }

    private void setStack(@NotNull ItemStack itemStack, int slot) {
        if (slot < 0)
            return;

        if (inventory.size() < slot)
            return;

        inventory.setStack(slot, itemStack);
    }

    @NotNull
    private ItemStack addStacks(@NotNull ItemStack neighborStack, @NotNull ItemStack ourItemStack) {
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

    @Nullable
    private int[] getSlots() {
        if (inventory == null)
            return null;

        int[] slots = inventory.getRealSlots(null);

        if (slots.length == 0)
            return null;

        return slots;
    }

    @Nullable
    private ItemStack retrieveItemStack(int slot) {
        if (inventory.size() <= slot)
            return null;

        return inventory.getStack(slot);
    }

    @NotNull
    private VertexPath findPath(@NotNull ItemStack chosenItemStack, @NotNull List<BlockPos> currentKnownCables, @NotNull List<BlockPos> currentInterfaceableBlocks) {
        if (currentInterfaceableBlocks.size() == 0)
            return new VertexPath();

        // TODO: Replace with filter search
        BlockPos nextBlockPos = currentInterfaceableBlocks.get(0);

        return GenericCable.dijkstraAlgorithm(currentKnownCables, getPos(), nextBlockPos);
    }

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        return inventory;
    }
}
