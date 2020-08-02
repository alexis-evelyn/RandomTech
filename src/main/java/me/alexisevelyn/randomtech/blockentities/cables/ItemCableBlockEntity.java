package me.alexisevelyn.randomtech.blockentities.cables;

import me.alexisevelyn.randomtech.utility.BlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;

public class ItemCableBlockEntity extends ChestBlockEntity implements Tickable {
    public ItemCableBlockEntity() {
        super(BlockEntities.ITEM_CABLE);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
    }

    @Override
    public void tick() {

    }
}
