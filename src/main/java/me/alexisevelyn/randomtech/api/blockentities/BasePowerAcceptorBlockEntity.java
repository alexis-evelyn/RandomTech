package me.alexisevelyn.randomtech.api.blockentities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import reborncore.api.IToolDrop;
import reborncore.api.blockentity.IUpgrade;
import reborncore.api.blockentity.InventoryProvider;
import reborncore.client.screen.BuiltScreenHandlerProvider;
import reborncore.client.screen.builder.BuiltScreenHandler;
import reborncore.common.powerSystem.PowerAcceptorBlockEntity;
import reborncore.common.util.RebornInventory;

import java.util.Set;

public abstract class BasePowerAcceptorBlockEntity extends PowerAcceptorBlockEntity implements IToolDrop, InventoryProvider, BuiltScreenHandlerProvider {
    // Energy Values
    @SuppressWarnings("CanBeFinal") double energyAddend = -1000.0;
    @SuppressWarnings("CanBeFinal") double maxPower = 10000;
    @SuppressWarnings("CanBeFinal") double maxInput = 10000;

    @SuppressWarnings("CanBeFinal") double maxOutput = 0;

    @SuppressWarnings("CanBeFinal") boolean canAcceptEnergy = true;
    @SuppressWarnings("CanBeFinal") boolean canProvideEnergy = false;

    public RebornInventory<?> inventory;

    public BasePowerAcceptorBlockEntity(BlockEntityType<?> blockEntityType) {
        super(blockEntityType);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public double getBaseMaxPower() {
        return maxPower;
    }

    @Override
    public double getBaseMaxOutput() {
        return maxOutput;
    }

    @Override
    public double getBaseMaxInput() {
        return maxInput;
    }

    /**
     * Returns the maximum number of items a stack can contain when placed inside this inventory.
     * No slots may have more than this number of items. It is effectively the
     * stacking limit for this inventory's slots.
     *
     * @return the max {@link ItemStack#getCount() count} of item stacks in this inventory
     */
    @Override
    public int getMaxCountPerStack() {
        return inventory.getMaxCountPerStack();
    }

    /**
     * Returns the number of times the specified item occurs in this inventory across all stored stacks.
     *
     * @param item Item to check for
     */
    @Override
    public int count(Item item) {
        return inventory.count(item);
    }

    /**
     * Determines whether this inventory contains any of the given candidate items.
     *
     * @param items Items to check for
     */
    @Override
    public boolean containsAny(Set<Item> items) {
        return inventory.containsAny(items);
    }

    @Override
    public void onOpen(PlayerEntity player) {

    }

    @Override
    public void onClose(PlayerEntity player) {

    }

    // Used for TR's Wrench
    @Nullable
    public ItemStack getToolDrop(PlayerEntity playerEntity) {
        return null;
    }

    @Override
    public boolean canBeUpgraded() {
        return false;
    }

    @Override
    public boolean isUpgradeValid(IUpgrade upgrade, ItemStack stack) {
        return false;
    }

    public boolean hasEnoughEnergy() {
        return getEnergy() >= (-1 * energyAddend);
    }

    // Future Proofing
    @SuppressWarnings("SameReturnValue")
    public abstract int getMinPower();

    @Override
    public boolean canAcceptEnergy(final Direction direction) {
        return canAcceptEnergy;
    }

    @Override
    public boolean canProvideEnergy(final Direction direction) {
        return canProvideEnergy;
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Nullable
    @Override
    public BuiltScreenHandler createScreenHandler(int syncID, PlayerEntity player) {
        return null;
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

    public double getEnergyAddend() {
        return this.energyAddend;
    }
}
