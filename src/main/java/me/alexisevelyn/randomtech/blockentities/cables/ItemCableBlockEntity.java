package me.alexisevelyn.randomtech.blockentities.cables;

import me.alexisevelyn.randomtech.api.blocks.cables.GenericCable;
import me.alexisevelyn.randomtech.api.utilities.cablehelpers.ItemTransferHelper;
import me.alexisevelyn.randomtech.api.utilities.pathfinding.dijkstra.Vertex;
import me.alexisevelyn.randomtech.api.utilities.pathfinding.dijkstra.VertexPath;
import me.alexisevelyn.randomtech.blocks.cables.ItemCable;
import me.alexisevelyn.randomtech.inventories.ItemCableInventory;
import me.alexisevelyn.randomtech.utility.BlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;

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
        for (int slot : ItemTransferHelper.getSlots(inventory)) {
            currentItemStack = ItemTransferHelper.retrieveItemStack(inventory, slot);

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
            ItemTransferHelper.attemptTransferToContainer(ourCable, world, pos, currentItemStack);
            return;
        }

        // Get Destination's Block Entity
        ItemCableBlockEntity neighborCableEntity = ItemTransferHelper.getCableBlockEntity(world, (BlockPos) nextVertex.getPosition());

        if (neighborCableEntity == null)
            return;

        int[] neighborSlots = ItemTransferHelper.getSlots(neighborCableEntity.inventory);

        // Neighbor doesn't have any slots
        if (neighborSlots == null)
            return;

        // Attempt to Add Items to Neighbor Slots
        for (int slot : neighborSlots) {
            currentItemStack = ItemTransferHelper.transferItemStacks(neighborCableEntity.inventory, slot, currentItemStack);
            world.updateComparators(neighborCableEntity.getPos(), world.getBlockState(neighborCableEntity.getPos()).getBlock());
        }

        world.updateComparators(pos, world.getBlockState(pos).getBlock());
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
