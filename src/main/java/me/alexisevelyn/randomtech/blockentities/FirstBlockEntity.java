package me.alexisevelyn.randomtech.blockentities;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import reborncore.api.IToolDrop;
import reborncore.api.blockentity.IUpgrade;
import reborncore.client.screen.BuiltScreenHandlerProvider;
import reborncore.client.screen.builder.BuiltScreenHandler;
import reborncore.client.screen.builder.ScreenHandlerBuilder;
import reborncore.common.powerSystem.PowerAcceptorBlockEntity;

import java.util.Set;

public class FirstBlockEntity extends PowerAcceptorBlockEntity implements IToolDrop, BuiltScreenHandlerProvider {
    int state = 0;

    public FirstBlockEntity() {
        super(null);
    }

    public FirstBlockEntity(BlockEntityType<?> blockEntityType) {
        super(blockEntityType);
    }

    @Override
    public double getBaseMaxPower() {
        return 0;
    }

    @Override
    public double getBaseMaxOutput() {
        return 0;
    }

    @Override
    public double getBaseMaxInput() {
        return 0;
    }

    @Override
    public ItemStack getToolDrop(PlayerEntity playerEntity) {
        return null;
    }

    @Override
    public int getMaxCountPerStack() {
        return 0;
    }

    @Override
    public void onOpen(PlayerEntity player) {

    }

    @Override
    public void onClose(PlayerEntity player) {

    }

    @Override
    public int count(Item item) {
        return 0;
    }

    @Override
    public boolean containsAny(Set<Item> items) {
        return false;
    }

    @Override
    public boolean canBeUpgraded() {
        return false;
    }

    @Override
    public boolean isUpgradeValid(IUpgrade upgrade, ItemStack stack) {
        return false;
    }

    // Setters and getters for the GUI to sync
    private void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }

    @Override
    public BuiltScreenHandler createScreenHandler(int i, PlayerEntity playerEntity) {
        return new ScreenHandlerBuilder("first_gui").player(playerEntity.inventory).inventory().hotbar().addInventory()
                .blockEntity(this).syncEnergyValue()
                .sync(this::getState, this::setState)
                .addInventory().create(this, i);
    }
}
