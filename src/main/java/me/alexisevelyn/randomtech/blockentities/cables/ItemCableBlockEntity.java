package me.alexisevelyn.randomtech.blockentities.cables;

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
import net.minecraft.world.WorldAccess;

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

    }

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        return inventory;
    }
}
