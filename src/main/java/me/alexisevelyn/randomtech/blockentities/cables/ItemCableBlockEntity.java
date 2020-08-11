package me.alexisevelyn.randomtech.blockentities.cables;

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
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ItemCableBlockEntity extends BlockEntity implements InventoryProvider, Tickable {
    public ItemCableInventory inventory;

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
        // TODO: Replace this with a decent search algorithm
        // Don't use this code in a production environment!!!
        // This "works", but is inefficient as hell and doesn't validate everything that could go wrong.
        if (getWorld() == null)
            return;

        // Tick Time Modded by 20!!!

        if (!(getWorld().getBlockState(getPos()).getBlock() instanceof ItemCable))
            return;

        if (inventory.isEmpty())
            return;

        ItemCable itemCable = (ItemCable) getWorld().getBlockState(getPos()).getBlock();
        List<BlockPos> interfacingCables = itemCable.getAllInterfacingCables(getWorld(), getPos());

        int size = inventory.getInventory().size();

        if (size <= 0)
            return;

        interfacingCables.forEach(cablePos -> {
            ItemStack currentStack = inventory.getInventory().get(0);

            BlockPos currentPos = new BlockPos(cablePos.getX(), cablePos.getY(), cablePos.getZ());
            Block currentBlock = getWorld().getBlockState(currentPos).getBlock();

            if (!(currentBlock instanceof ItemCable))
                return;

            List<BlockPos> neighborBlocks = getAllNeighborBlocksTemporary(currentPos);

            if (neighborBlocks.size() == 0)
                return;

            for (BlockPos currentNeighbor : neighborBlocks) {
                // Block neighborBlock = getWorld().getBlockState(currentNeighbor).getBlock();
                BlockEntity neighborBlockEntity = getWorld().getBlockEntity(currentNeighbor);

                if (!(getWorld().getBlockEntity(currentNeighbor) instanceof Inventory)) // !(neighborBlock instanceof InventoryProvider) &&
                    continue;

                Inventory neighborInventoryProvider = (Inventory) neighborBlockEntity;

                if (neighborInventoryProvider == null)
                    continue;

                // This does not validate anything, it can cause crashes. This whole method is just makeshift code, don't use it in a production environment.
                neighborInventoryProvider.setStack(0, currentStack);
                getWorld().updateComparators(currentPos, currentBlock); // This does not update comparators.
            }
        });
    }

    private List<BlockPos> getAllNeighborBlocksTemporary(BlockPos pos) {
        // This is not helping efficiency, will be replaced
        BlockPos north, south, east, west, up, down;

        // Neighbor Positions
        north = CalculationHelper.addVectors(pos, Direction.NORTH.getVector());
        south = CalculationHelper.addVectors(pos, Direction.SOUTH.getVector());
        east = CalculationHelper.addVectors(pos, Direction.EAST.getVector());
        west = CalculationHelper.addVectors(pos, Direction.WEST.getVector());
        up = CalculationHelper.addVectors(pos, Direction.UP.getVector());
        down = CalculationHelper.addVectors(pos, Direction.DOWN.getVector());

        List<BlockPos> cables = new ArrayList<>();
        cables.add(north);
        cables.add(south);
        cables.add(east);
        cables.add(west);
        cables.add(up);
        cables.add(down);

        return cables;
    }

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        return inventory;
    }
}
