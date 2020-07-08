package me.alexisevelyn.randomtech.blockentities;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.alexisevelyn.randomtech.BlockEntities;
import me.alexisevelyn.randomtech.RegistryHelper;
import me.alexisevelyn.randomtech.crafters.FuserRecipeCrafter;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import reborncore.api.IToolDrop;
import reborncore.api.blockentity.InventoryProvider;
import reborncore.api.recipe.IRecipeCrafterProvider;
import reborncore.client.screen.BuiltScreenHandlerProvider;
import reborncore.client.screen.builder.BuiltScreenHandler;
import reborncore.client.screen.builder.ScreenHandlerBuilder;
import reborncore.common.crafting.RebornRecipe;
import reborncore.common.fluid.FluidValue;
import reborncore.common.recipes.RecipeCrafter;
import reborncore.common.util.RebornInventory;
import reborncore.common.util.Tank;

public class FuserBlockEntity extends FluidMachineBlockEntityBase implements IToolDrop, InventoryProvider, BuiltScreenHandlerProvider, IRecipeCrafterProvider {
    // Fluid Values
    // JsonElement buckets = new Gson().toJsonTree(5 * 1000);
    JsonObject buckets = new JsonParser().parse("{'buckets': 5}").getAsJsonObject();

    FluidValue maxFluidCapacity = FluidValue.parseFluidValue(buckets);

    FuserRecipeCrafter crafter;

    // Slots
    int[] inputSlots = { 0 };

    int inputSlot = inputSlots[0];
    int outputSlot = 1;

    public FuserBlockEntity() {
        super(BlockEntities.FUSER);
        this.inventory = new RebornInventory<>(2, "FuserBlockEntity", 64, this);
        this.tank = new Tank("TankStorage", maxFluidCapacity, this);

        crafter = new FuserRecipeCrafter(this, inventory, inputSlots);
    }

    // Used for TR's Wrench
    @Override
    public ItemStack getToolDrop(PlayerEntity playerEntity) {
        return new ItemStack(RegistryHelper.FUSER);
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
                .syncCrafterValue()
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

    @Override
    public RecipeCrafter getRecipeCrafter() {
        return crafter;
    }

    @Override
    public boolean canCraft(RebornRecipe rebornRecipe) {
        return true;
    }

    @Override
    public boolean isStackValid(int slotID, ItemStack stack) {
        return true;
    }

    @Override
    public int[] getInputSlots() {
        return inputSlots;
    }
}
