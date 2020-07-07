package me.alexisevelyn.randomtech.blockentities;

import me.alexisevelyn.randomtech.BlockEntities;
import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.RegistryHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.Direction;
import reborncore.api.IToolDrop;
import reborncore.api.blockentity.IUpgrade;
import reborncore.api.blockentity.InventoryProvider;
import reborncore.client.screen.BuiltScreenHandlerProvider;
import reborncore.client.screen.builder.BuiltScreenHandler;
import reborncore.client.screen.builder.ScreenHandlerBuilder;
import reborncore.common.powerSystem.PowerAcceptorBlockEntity;
import reborncore.common.util.RebornInventory;

import java.util.Set;

public class FuserBlockEntity extends PowerAcceptorBlockEntity implements IToolDrop, InventoryProvider, BuiltScreenHandlerProvider {
    // Does Nothing
    int state = 0;

    // Energy Values
    double energyAddend = -1000.0;
    double maxPower = 10000;
    double maxInput = 10000;

    // Inventory Slot Markers
    RebornInventory<FuserBlockEntity> inventory;
    int inputSlot = 0;

    public FuserBlockEntity() {
        super(BlockEntities.FUSER);
        this.inventory = new RebornInventory<>(1, "FuserBlockEntity", 1, this);
    }

    @Override
    public double getBaseMaxPower() {
        return maxPower;
    }

    @Override
    public double getBaseMaxOutput() {
        return 0;
    }

    @Override
    public double getBaseMaxInput() {
        return maxInput;
    }

    // Used for TR's Wrench
    @Override
    public ItemStack getToolDrop(PlayerEntity playerEntity) {
        return new ItemStack(RegistryHelper.FUSER);
    }

    @Override
    public int getMaxCountPerStack() {
        return inventory.getMaxCountPerStack();
    }

    @Override
    public void onOpen(PlayerEntity player) {

    }

    @Override
    public void onClose(PlayerEntity player) {

    }

    @Override
    public int count(Item item) {
        return inventory.count(item);
    }

    @Override
    public boolean containsAny(Set<Item> items) {
        return inventory.containsAny(items);
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
    public BuiltScreenHandler createScreenHandler(int syncID, PlayerEntity playerEntity) {
        return new ScreenHandlerBuilder("fuser_gui")
                .player(playerEntity.inventory)
                .inventory()
                .hotbar()
                .addInventory()
                .blockEntity(this)
                .slot(inputSlot, 8, 72)
                .syncEnergyValue()
                .sync(this::getState, this::setState)
                .addInventory()
                .create(this, syncID);
    }

    @Override
    public void tick() {
        super.tick();

        if (world == null){
            return;
        }
    }

    public boolean hasEnoughEnergy() {
        return getEnergy() >= (-1 * energyAddend);
    }

    @Override
    public boolean canAcceptEnergy(final Direction direction) {
        return true;
    }

    @Override
    public boolean canProvideEnergy(final Direction direction) {
        return false;
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void fromTag(BlockState blockState, CompoundTag compoundTag) {
        super.fromTag(blockState, compoundTag);
    }

    @Override
    public CompoundTag toTag(CompoundTag compoundTag) {
        super.toTag(compoundTag);

        return compoundTag;
    }
}
