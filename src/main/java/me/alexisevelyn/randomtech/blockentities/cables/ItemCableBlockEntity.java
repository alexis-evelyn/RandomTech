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
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemCableBlockEntity extends BlockEntity implements InventoryProvider, Tickable {
    private final ItemCableInventory inventory;
    private final ItemCable ourCable = new ItemCable();

    public ItemCableBlockEntity() {
        super(BlockEntities.ITEM_CABLE);
        inventory = new ItemCableInventory();
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);

        // Save Inventory
        Inventories.toTag(tag, this.inventory.getInventory());

        // Inventory is Saved Now, Mark Clean
        this.inventory.markClean();

        return tag;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);

        // To Compare Previous Inventory With Current Inventory
        DefaultedList<ItemStack> itemStacks = this.inventory.getInventory();

        // Setup Inventory
        this.inventory.setInventory(DefaultedList.ofSize(this.inventory.size(), ItemStack.EMPTY));
        Inventories.fromTag(tag, this.inventory.getInventory());

        if (world == null)
            return;

        // This allows command blocks to set the inventory of the cable and have items moved.
        // Command blocks do not update the block themselves (using /data merge), but they do modify the inventory and we can tick the cable here
        if (!itemStacks.equals(this.inventory.getInventory()))
            moveItemInNetwork(world);
    }

    // Example Command For Testing: /data merge block ~ ~ ~-4 {Items: [{Slot: 9b, id: "minecraft:bedrock", Count: 1b}]}
    public void moveItemInNetwork(@NotNull World world) {
        attemptCableTransfer(world);

        // TODO (Important): Use a boolean to determine if should extract or insert
        attemptInsertIntoInterfaceableBlocks(world);
        attemptExtractFromInterfaceableBlocks(world);
    }

    private void attemptInsertIntoInterfaceableBlocks(@NotNull World world) {
        ItemStack currentItemStack = null;
        for (int slot : ItemTransferHelper.getSlots(inventory)) {
            currentItemStack = ItemTransferHelper.retrieveItemStack(inventory, slot);

            if (currentItemStack != null)
                break;
        }

        // If no itemstacks found, just return
        if (currentItemStack == null)
            return;

        ItemTransferHelper.tryTransferToContainer(ourCable, world, pos, currentItemStack);
    }

    private void attemptExtractFromInterfaceableBlocks(@NotNull World world) {
        // TODO (Important): Implement Me
    }

    private void attemptCableTransfer(@NotNull World world) {
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
        Vertex nextVertex = path.getNext();

        // We are at the end of the path (if any path) if this if statement equals true
        if (nextVertex == null || !(nextVertex.getPosition() instanceof BlockPos))
            return;

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

        // TODO (IMPORTANT): Replace with filter search
        BlockPos nextBlockPos = currentInterfaceableBlocks.get(0);

        return GenericCable.dijkstraAlgorithm(currentKnownCables, getPos(), nextBlockPos);
    }

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        return inventory;
    }

    @Override
    public void tick() {
        // Only Run when World is Not Null
        // Helps Lower World Load Lag
        if (world == null)
            return;

        // Mark self as dirty to ensure the nbt data gets saved on next save
        if (inventory.isDirty())
            markDirty();

        // We only want the cable to move when the inventory is updated
        // Command blocks do not allow marking the inventory as dirty as they update the nbt data directly
        if (inventory.needsProcessing()) {
            inventory.setNeedsProcessing(false);
            moveItemInNetwork(world);
        }
    }
}
