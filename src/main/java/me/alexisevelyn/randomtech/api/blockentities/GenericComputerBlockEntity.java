package me.alexisevelyn.randomtech.api.blockentities;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import reborncore.api.IToolDrop;
import reborncore.api.blockentity.InventoryProvider;
import reborncore.client.screen.BuiltScreenHandlerProvider;

public abstract class GenericComputerBlockEntity extends BasePowerAcceptorBlockEntity implements IToolDrop, InventoryProvider, BuiltScreenHandlerProvider {
    // Inventory Slot Markers
    final int inputSlot = 0;
    final int energyAddend = 0;

    final Block block;

    public GenericComputerBlockEntity(Block block, BlockEntityType<?> blockEntityType) {
        super(blockEntityType);
        this.block = block;
    }

    // Used for TR's Wrench
    @Override
    public ItemStack getToolDrop(PlayerEntity playerEntity) {
        return new ItemStack(this.block);
    }

    @Override
    public void tick() {
        super.tick();
    }
}
