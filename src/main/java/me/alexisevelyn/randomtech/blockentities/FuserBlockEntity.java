package me.alexisevelyn.randomtech.blockentities;

import com.google.gson.*;
import me.alexisevelyn.randomtech.BlockEntities;
import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.RegistryHelper;
import me.alexisevelyn.randomtech.fluids.RedstoneFluid;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.EmptyFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
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
import reborncore.common.fluid.FluidValue;
import reborncore.common.fluid.container.FluidInstance;
import reborncore.common.powerSystem.PowerAcceptorBlockEntity;
import reborncore.common.util.RebornInventory;
import reborncore.common.util.Tank;

import java.util.Set;

public class FuserBlockEntity extends PowerAcceptorBlockEntity implements IToolDrop, InventoryProvider, BuiltScreenHandlerProvider {
    // Does Nothing
    int state = 0;

    // Energy Values
    double energyAddend = -1000.0;
    double maxPower = 10000;
    double maxInput = 10000;

    // Fluid Values
    // JsonElement buckets = new Gson().toJsonTree(5 * 1000);
    JsonObject buckets = new JsonParser().parse("{'buckets': 5}").getAsJsonObject();

    FluidValue maxFluidCapacity = FluidValue.parseFluidValue(buckets);

    // Inventory Slot Markers
    protected Tank tank;
    RebornInventory<FuserBlockEntity> inventory;

    // Slots
    int inputSlot = 0;
    int outputSlot = 1;

    public FuserBlockEntity() {
        super(BlockEntities.FUSER);
        this.inventory = new RebornInventory<>(2, "FuserBlockEntity", 64, this);
        this.tank = new Tank("TankStorage", maxFluidCapacity, this);

//        tank.setFluid(Fluids.WATER);
//        tank.setFluidAmount(FluidValue.INFINITE);
    }

    public FluidValue getMaxFluidLevel() {
        return tank.getCapacity();
    }

    public FluidValue getFluidLevel() {
        // tank.getFluidAmount();
        return tank.getFluidInstance().getAmount();
    }

    @Override
    public Tank getTank() {
        return tank;
    }

    public FluidInstance getFluid() {
        return tank.getFluidInstance();
    }

    public Fluid getFluidType() {
        return tank.getFluid();
    }

    public void setFluidAmount(FluidValue fluidAmount) {
        tank.getFluidInstance().setAmount(fluidAmount);
    }

    public void setFluid(Fluid fluid) {
        tank.setFluid(fluid);
    }

    public boolean isEmpty() {
        return tank.isEmpty();
    }

    public boolean hasFluid() {
        return !(tank.getFluid() instanceof EmptyFluid);
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
                .fluidSlot(inputSlot, 8, 72)
                .outputSlot(outputSlot, 26, 72)
                .syncEnergyValue()
                .sync(this::getState, this::setState)
                .sync(tank)
                .addInventory()
                .create(this, syncID);
    }

    @Override
    public void tick() {
        super.tick();

        if (world == null || world.isClient) {
            return;
        }

        if (!hasFluid()) {
            setFluid(RegistryHelper.MAGIC_FLUID);
        }

        if (isEmpty()) {
            setFluidAmount(FluidValue.BUCKET);
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

        tank.read(compoundTag);
    }

    @Override
    public CompoundTag toTag(CompoundTag compoundTag) {
        super.toTag(compoundTag);
        tank.write(compoundTag);

        return compoundTag;
    }
}
