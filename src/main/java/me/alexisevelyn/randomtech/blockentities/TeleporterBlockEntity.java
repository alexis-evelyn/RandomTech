package me.alexisevelyn.randomtech.blockentities;

import me.alexisevelyn.randomtech.BlockEntities;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import reborncore.api.IToolDrop;
import reborncore.api.blockentity.IUpgrade;
import reborncore.client.screen.BuiltScreenHandlerProvider;
import reborncore.client.screen.builder.BuiltScreenHandler;
import reborncore.client.screen.builder.ScreenHandlerBuilder;
import reborncore.common.blocks.BlockMachineBase;
import reborncore.common.powerSystem.PowerAcceptorBlockEntity;

import java.util.Set;

public class TeleporterBlockEntity extends PowerAcceptorBlockEntity implements IToolDrop, BuiltScreenHandlerProvider {
    int state = 0;

    public TeleporterBlockEntity() {
        super(BlockEntities.TELEPORTER);
    }

    public TeleporterBlockEntity(BlockEntityType<?> blockEntityType) {
        super(blockEntityType);
    }

    @Override
    public double getBaseMaxPower() {
        return 10000;
    }

    @Override
    public double getBaseMaxOutput() {
        return 100;
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
        return new ScreenHandlerBuilder("teleporter_gui").player(playerEntity.inventory).inventory().hotbar().addInventory()
                .blockEntity(this).syncEnergyValue()
                .sync(this::getState, this::setState)
                .addInventory().create(this, i);
    }

    @Override
    public void tick() {
        super.tick();

        if (world == null){
            return;
        }

        if (world.isClient) {
            return;
        }

//        if (world.getTime() % 20 == 0) {
//            world.setBlockState(pos, world.getBlockState(pos).with(BlockMachineBase.ACTIVE, false));
//        }

        addEnergy(1.0);
        syncWithAll(); // Syncs Energy Changes With Client
    }

    @Override
    public boolean canAcceptEnergy(final Direction direction) {
        return false;
    }

    @Override
    public boolean canProvideEnergy(final Direction direction) {
        return true;
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }
}
