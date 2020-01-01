package me.alexisevelyn.randomtech.blockentities;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.alexisevelyn.randomtech.crafters.FuserRecipeCrafter;
import me.alexisevelyn.randomtech.utility.BlockEntities;
import me.alexisevelyn.randomtech.utility.RegistryHelper;
import me.alexisevelyn.randomtech.utility.recipemanagers.GenericFluidRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.EmptyFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.registry.Registry;
import reborncore.api.IToolDrop;
import reborncore.api.blockentity.InventoryProvider;
import reborncore.api.recipe.IRecipeCrafterProvider;
import reborncore.client.screen.BuiltScreenHandlerProvider;
import reborncore.client.screen.builder.BuiltScreenHandler;
import reborncore.client.screen.builder.ScreenHandlerBuilder;
import reborncore.common.crafting.RebornRecipe;
import reborncore.common.crafting.ingredient.FluidIngredient;
import reborncore.common.fluid.FluidValue;
import reborncore.common.fluid.container.ItemFluidInfo;
import reborncore.common.recipes.RecipeCrafter;
import reborncore.common.util.RebornInventory;
import reborncore.common.util.Tank;

import java.util.List;
import java.util.stream.Collectors;

public class FuserBlockEntity extends FluidMachineBlockEntityBase implements IToolDrop, InventoryProvider, BuiltScreenHandlerProvider, IRecipeCrafterProvider {
    // Fluid Values
    // JsonElement buckets = new Gson().toJsonTree(5 * 1000);
    JsonObject buckets = new JsonParser().parse("{'buckets': 500}").getAsJsonObject();

    FluidValue maxFluidCapacity = FluidValue.parseFluidValue(buckets);

    FuserRecipeCrafter crafter;

    // Slots
    int[] inputSlots = { 0 };
    int[] outputSlots = { 1 };

    int inputSlot = inputSlots[0];
    int outputSlot = outputSlots[0];

    int fluidInputSlot = 2;
    int fluidOutputSlot = 3;

    public FuserBlockEntity() {
        super(BlockEntities.FUSER);
        this.inventory = new RebornInventory<>(4, "FuserBlockEntity", 64, this);
        this.tank = new Tank("TankStorage", maxFluidCapacity, this);

        crafter = new FuserRecipeCrafter(this, inventory, inputSlots, outputSlots);
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
                .slot(inputSlot, 8, 72)
                .outputSlot(outputSlot, 26, 72)
                .fluidSlot(fluidInputSlot, 44, 72)
                .fluidSlot(fluidOutputSlot, 62, 72)
                .syncEnergyValue()
                .syncCrafterValue()
                .sync(getTank())
                .addInventory()
                .create(this, syncID);
    }

    @Override
    public void tick() {
        super.tick();

        if (world == null || world.isClient)
            return;

        // Reset Stored Fluid Type if Tank is Empty
        if (tank.getFluidAmount().isEmpty())
            tank.setFluid(Fluids.EMPTY);

        attemptFillEmptyFluidContainer(fluidInputSlot, fluidOutputSlot);
    }

    public boolean hasEmptyFluidContainer(int inputSlot) {
        Item item = inventory.getStack(inputSlot).getItem();

        // I'm not just trying to check for empty vanilla buckets
//        if (!(item instanceof BucketItem))
//            return false;

        if (!(item instanceof ItemFluidInfo))
            return false;

//        BucketItem bucketItem = (BucketItem) item;

        ItemFluidInfo itemFluidInfo = (ItemFluidInfo) item;

        return itemFluidInfo.getEmpty().getItem().equals(item);
    }

    public void attemptFillEmptyFluidContainer(int inputSlot, int outputSlot) {
        // Check if a bucket is in the fluid slot for outputting fluid contents
        if (!hasEmptyFluidContainer(inputSlot))
            return;

        ItemFluidInfo item = (ItemFluidInfo) inventory.getStack(inputSlot).getItem();
        Tank tank = getTank();

        if (tank == null)
            return;

        // There's no millibucket count for ItemFluidInfo, so we assume 1000 mb.
        // If that changes, we can update this variable here
        FluidValue millibucketCount = FluidValue.fromRaw(1000);

        // Skip processing if not enough fluid in tank
        if (tank.getFluid() instanceof EmptyFluid || tank.getFluidAmount().lessThan(millibucketCount))
            return;

        // Check if output stack is usable (e.g. same stack type and not above stack limit
        ItemStack filledContainer = item.getFull(tank.getFluid());

        // Check if stack same type of stack as output if output not empty
        if (inventory.getStack(outputSlot).getCount() != 0 && !inventory.getStack(outputSlot).getItem().equals(filledContainer.getItem()))
            return;

        // Check if stack is already max stack size
        if (inventory.getStack(outputSlot).getItem().getMaxCount() <= inventory.getStack(outputSlot).getCount())
            return;

        // Reduce fluid by millibucket count
        tank.getFluidInstance().subtractAmount(millibucketCount);

        // Reduce empty container by one
        inventory.getStack(inputSlot).decrement(1);

        // Set new output container or increment output slot by one
        if (inventory.getStack(outputSlot).getCount() == 0)
            inventory.setStack(outputSlot, filledContainer);
        else
            inventory.getStack(outputSlot).increment(1);
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
        if (rebornRecipe instanceof GenericFluidRecipe)
            rebornRecipe.canCraft(this);

        return false;
    }

    @Override
    public boolean isStackValid(int slotID, ItemStack stack) {
        return crafter.isStackValidInput(stack);
    }

    @Override
    public int[] getInputSlots() {
        return inputSlots;
    }
}
