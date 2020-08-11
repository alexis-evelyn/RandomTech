package me.alexisevelyn.randomtech.blockentities.cables;

import me.alexisevelyn.randomtech.api.blocks.cables.GenericCable;
import me.alexisevelyn.randomtech.blocks.cables.ItemCable;
import me.alexisevelyn.randomtech.inventories.ItemCableInventory;
import me.alexisevelyn.randomtech.utility.BlockEntities;
import net.minecraft.block.Block;
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
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemCableBlockEntity extends BlockEntity implements InventoryProvider, Tickable {
    private final ItemCableInventory inventory;

    public ItemCableBlockEntity() {
        super(BlockEntities.ITEM_CABLE);
        inventory = new ItemCableInventory();

        // Jump start any cable networks when they are loaded in.
        moveItemInNetwork();
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
        // This Causes too much lag
        // I will need help getting this to work efficiently

        // Ensure World is Not Null (It always is for a little bit on world load)
        if (world == null)
            return;

        // Ensure our block is actually an instance of ItemCable
        Block ourBlock = world.getBlockState(pos).getBlock();
        if (!(ourBlock instanceof ItemCable))
            return;

        ItemCable ourCable = (ItemCable) ourBlock;
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
        BlockPos nextBlockPos = findNextBlockPos(currentItemStack, currentKnownCables, currentInterfaceableBlocks);

        // No Path Found, just Return
        if (nextBlockPos == null)
            return;

        // Retrieve First Neighbor on Path
        BlockEntity blockEntity = world.getBlockEntity(nextBlockPos);

        // Not Expected Block Entity, So Return
        if (!(blockEntity instanceof ItemCableBlockEntity))
            return;

        ItemCableBlockEntity itemCableBlockEntity = (ItemCableBlockEntity) blockEntity;
        int[] neighborSlots = itemCableBlockEntity.getSlots();

        // Neighbor doesn't have any slots
        if (neighborSlots == null)
            return;

        // Attempt to Add Items to Neighbor Slots
        for (int slot : neighborSlots)
            currentItemStack = transferItemStacks(itemCableBlockEntity, slot, currentItemStack);
    }

    @Override
    public void tick() {
        // Don't Use
    }

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
        // Test with negative slot numbers and one slot higher than the size of the inventory
        if (inventory.size() < slot)
            return;

        inventory.setStack(slot, itemStack);
    }

    @Nullable
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

        ItemStack chosenItemStack = inventory.getStack(slot);
        if (chosenItemStack.equals(ItemStack.EMPTY))
            return null;

        return chosenItemStack;
    }

    private BlockPos findNextBlockPos(@NotNull ItemStack chosenItemStack, @NotNull List<BlockPos> currentKnownCables, @NotNull List<BlockPos> currentInterfaceableBlocks) {
        if (currentKnownCables.size() == 0)
            return null;

        if (currentInterfaceableBlocks.size() == 0)
            return null;

        // TODO: Replace with filter search
        BlockPos nextBlockPos = currentInterfaceableBlocks.get(0);

        List<BlockPos> path = GenericCable.dijkstraAlgorithm(currentKnownCables, nextBlockPos);

        if (path.isEmpty())
            return null;

        return path.get(0);
    }

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        return inventory;
    }
}
