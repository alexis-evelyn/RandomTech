package me.alexisevelyn.randomtech.blockentities.cables;

import jdk.internal.jline.internal.Nullable;
import me.alexisevelyn.randomtech.api.utilities.CalculationHelper;
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
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ItemCableBlockEntity extends BlockEntity implements InventoryProvider, Tickable {
    private final ItemCableInventory inventory;

    public ItemCableBlockEntity() {
        super(BlockEntities.ITEM_CABLE);
        inventory = new ItemCableInventory();
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

    @Override
    public void tick() {
        // Ensure World is Not Null (It always is for a little bit on world load)
        if (world == null)
            return;

        // Don't execute every tick. Do it once a second.
        if (world.getTime() % 20 != 0)
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
        List<BlockPos> currentPath = searchForDestination(currentItemStack, currentKnownCables, currentInterfaceableBlocks);

        // No Path Found, just Return
        if (currentPath.size() == 0)
            return;

        // TODO: Implement Path Following Here
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

    @NotNull
    private List<BlockPos> searchForDestination(@NotNull ItemStack chosenItemStack, @NotNull List<BlockPos> currentKnownCables, @NotNull List<BlockPos> currentInterfaceableBlocks) {
        // TODO: Implement Search Algorithm Here
        // https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm

        List<BlockPos> path = new ArrayList<>();
        // chosenItemStack;

        return path;
    }

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        return inventory;
    }
}
