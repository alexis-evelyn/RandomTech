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

/**
 * The type Base power acceptor block entity.
 */
public abstract class BasePowerAcceptorBlockEntity extends PowerAcceptorBlockEntity implements IToolDrop, InventoryProvider, BuiltScreenHandlerProvider {
    // Energy Values
    @SuppressWarnings("CanBeFinal") protected double minPower = 0;
    @SuppressWarnings("CanBeFinal") protected double maxPower = 10000;
    @SuppressWarnings("CanBeFinal") protected double maxInput = 10000;

    @SuppressWarnings("CanBeFinal") protected double maxOutput = 0;

    @SuppressWarnings("CanBeFinal") protected boolean canAcceptEnergy = true;
    @SuppressWarnings("CanBeFinal") protected boolean canProvideEnergy = false;

    public RebornInventory<?> inventory;

    /**
     * Instantiates a new Base power acceptor block entity.
     *
     * @param blockEntityType the block entity type
     */
    public BasePowerAcceptorBlockEntity(BlockEntityType<?> blockEntityType) {
        super(blockEntityType);
    }

    /**
     * Tick.
     */
    @SuppressWarnings("EmptyMethod")
    @Override
    public void tick() {
        super.tick();
    }

    /**
     * Gets base max power.
     *
     * @return the base max power
     */
    @Override
    public double getBaseMaxPower() {
        return maxPower;
    }

    /**
     * Gets base max output.
     *
     * @return the base max output
     */
    @Override
    public double getBaseMaxOutput() {
        return maxOutput;
    }

    /**
     * Gets base max input.
     *
     * @return the base max input
     */
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

    /**
     * On open.
     *
     * @param player the player
     */
    @Override
    public void onOpen(PlayerEntity player) {
        // Intentionally Left Empty
    }

    /**
     * On close.
     *
     * @param player the player
     */
    @Override
    public void onClose(PlayerEntity player) {
        // Intentionally Left Empty
    }

    /**
     * Gets tool drop.
     *
     * @param playerEntity the player entity
     * @return the tool drop
     */
    // Used for TR's Wrench
    @Nullable
    public ItemStack getToolDrop(PlayerEntity playerEntity) {
        return null;
    }

    /**
     * Can be upgraded boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean canBeUpgraded() {
        return false;
    }

    /**
     * Is upgrade valid boolean.
     *
     * @param upgrade the upgrade
     * @param stack   the stack
     * @return the boolean
     */
    @Override
    public boolean isUpgradeValid(IUpgrade upgrade, ItemStack stack) {
        return false;
    }

    /**
     * Has enough energy boolean.
     *
     * @param energyAddend the energy addend
     * @return the boolean
     */
    public boolean hasEnoughEnergy(int energyAddend) {
        return getEnergy() >= energyAddend;
    }

    /**
     * Gets min power.
     *
     * @return the min power
     */
    // Future Proofing
    @SuppressWarnings({"SameReturnValue"})
    public double getMinPower() {
        return this.minPower;
    }

    /**
     * Can accept energy boolean.
     *
     * @param direction the direction
     * @return the boolean
     */
    @Override
    public boolean canAcceptEnergy(final Direction direction) {
        return canAcceptEnergy;
    }

    /**
     * Can provide energy boolean.
     *
     * @param direction the direction
     * @return the boolean
     */
    @Override
    public boolean canProvideEnergy(final Direction direction) {
        return canProvideEnergy;
    }

    /**
     * On load.
     */
    @SuppressWarnings("EmptyMethod")
    @Override
    public void onLoad() {
        super.onLoad();
    }

    /**
     * Gets inventory.
     *
     * @return the inventory
     */
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Create screen handler built screen handler.
     *
     * @param syncID the sync id
     * @param player the player
     * @return the built screen handler
     */
    @Nullable
    @Override
    public BuiltScreenHandler createScreenHandler(int syncID, PlayerEntity player) {
        return null;
    }

    /**
     * From tag.
     *
     * @param blockState  the block state
     * @param compoundTag the compound tag
     */
    @Override
    public void fromTag(BlockState blockState, CompoundTag compoundTag) {
        super.fromTag(blockState, compoundTag);
    }

    /**
     * To tag compound tag.
     *
     * @param compoundTag the compound tag
     * @return the compound tag
     */
    @Override
    public CompoundTag toTag(CompoundTag compoundTag) {
        super.toTag(compoundTag);

        return compoundTag;
    }
}
