package me.alexisevelyn.randomtech.blockentities;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.alexisevelyn.randomtech.api.blockentities.FluidMachineBlockEntityBase;
import me.alexisevelyn.randomtech.api.utilities.CalculationHelper;
import me.alexisevelyn.randomtech.blocks.FuserBlock;
import me.alexisevelyn.randomtech.crafters.FuserRecipeCrafter;
import me.alexisevelyn.randomtech.guis.FuserGui;
import me.alexisevelyn.randomtech.utility.BlockEntitiesHelper;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.EmptyFluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import reborncore.api.IToolDrop;
import reborncore.api.blockentity.InventoryProvider;
import reborncore.api.recipe.IRecipeCrafterProvider;
import reborncore.client.screen.BuiltScreenHandlerProvider;
import reborncore.client.screen.builder.BuiltScreenHandler;
import reborncore.client.screen.builder.ScreenHandlerBuilder;
import reborncore.common.blocks.BlockMachineBase;
import reborncore.common.crafting.RebornRecipe;
import reborncore.common.fluid.FluidValue;
import reborncore.common.fluid.container.ItemFluidInfo;
import reborncore.common.recipes.RecipeCrafter;
import reborncore.common.util.RebornInventory;
import reborncore.common.util.Tank;

import java.util.Arrays;

/**
 * The type Fuser block entity.
 */
public class FuserBlockEntity extends FluidMachineBlockEntityBase implements IToolDrop, InventoryProvider, BuiltScreenHandlerProvider, IRecipeCrafterProvider {
    private final FuserRecipeCrafter crafter;

    // Slots
    private final static int[] inputSlots = { 0 };
    private final static int[] outputSlots = { 1 };

    private final static int inputSlot = inputSlots[0];
    private final static int outputSlot = outputSlots[0];

    private final static int fluidInputSlot = 2;
    private final static int fluidOutputSlot = 3;

    private int remainingRecipeTime = 0;
    private int maxRecipeTime = 0;

    /**
     * Instantiates a new Fuser block entity.
     */
    public FuserBlockEntity() {
        super(BlockEntitiesHelper.FUSER);
        this.inventory = new RebornInventory<>(4, "FuserBlockEntity", 64, this);

        // JsonElement buckets = new Gson().toJsonTree(5 * 1000);
        JsonObject buckets = new JsonParser().parse("{'buckets': 5}").getAsJsonObject();
        FluidValue maxFluidCapacity = FluidValue.parseFluidValue(buckets);

        this.tank = new Tank("TankStorage", maxFluidCapacity, this);

        crafter = new FuserRecipeCrafter(this, inventory, inputSlots, outputSlots);
    }

    /**
     * Gets tool drop.
     *
     * @param playerEntity the player entity
     * @return the tool drop
     */
    // Used for TR's Wrench
    @Override
    public ItemStack getToolDrop(PlayerEntity playerEntity) {
        return new ItemStack(RegistryHelper.FUSER);
    }

    /**
     * Create screen handler built screen handler.
     *
     * @param syncID       the sync id
     * @param playerEntity the player entity
     * @return the built screen handler
     */
    @Override
    public BuiltScreenHandler createScreenHandler(int syncID, PlayerEntity playerEntity) {
        return new ScreenHandlerBuilder("fuser_gui")
                .player(playerEntity.inventory)
                .inventory()
                .hotbar()
                .addInventory()
                .blockEntity(this)
                .slot(inputSlot, FuserGui.ingredientSlotX, FuserGui.ingredientSlotY) // Ingredient Input
                .outputSlot(outputSlot, FuserGui.byproductSlotX, FuserGui.byproductSlotY) // Byproduct Output
                .fluidSlot(fluidInputSlot, FuserGui.emptyFluidContainerSlotX, FuserGui.emptyFluidContainerSlotY) // Empty Fluid Container (Input)
                .fluidSlot(fluidOutputSlot, FuserGui.fullFluidContainerSlotX, FuserGui.fullFluidContainerSlotY) // Full Fluid Container (Output)
                .syncEnergyValue()
                .syncCrafterValue()
                .sync(getTank())
                .sync(this::getMaxRecipeTime, this::setMaxRecipeTime)
                .sync(this::getRemainingRecipeTime, this::setRemainingRecipeTime)
                .addInventory()
                .create(this, syncID);
    }

    /**
     * Tick.
     */
    @Override
    public void tick() {
        super.tick();

        if (world == null || world.isClient)
            return;

        updateEnergyModelState(world);

        // Reset Stored Fluid Type if Tank is Empty
        if (tank.getFluidAmount().isEmpty())
            tank.setFluid(Fluids.EMPTY);

        attemptFillEmptyFluidContainer(fluidInputSlot, fluidOutputSlot);
    }

    public void updateEnergyModelState(@NotNull World world) {
        // This exists solely to have dynamic fuser textures based on power level
        // The boolean active is set by RebornCore's recipe handler in FuserBlock
        Direction facing = world.getBlockState(pos).get(BlockMachineBase.FACING);
        Boolean active = world.getBlockState(pos).get(BlockMachineBase.ACTIVE);

        BlockState state = world.getBlockState(pos)
                .with(FuserBlock.POWER, getEnergyState(this.getEnergy(), this.getMaxPower()))
                .with(BlockMachineBase.FACING, facing)
                .with(BlockMachineBase.ACTIVE, active);

        world.setBlockState(pos, state);
    }

    private int getEnergyState(double currentEnergy, double maxEnergy) {
        return (int) Math.floor(CalculationHelper.proportionCalculator(currentEnergy, 0, maxEnergy, 0, 15));
    }

    /**
     * Has empty fluid container boolean.
     *
     * @param inputSlot the input slot
     * @return the boolean
     */
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

    /**
     * Attempt fill empty fluid container.
     *
     * @param inputSlot  the input slot
     * @param outputSlot the output slot
     */
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

    /**
     * From tag.
     *
     * @param blockState  the block state
     * @param compoundTag the compound tag
     */
    @Override
    public void fromTag(BlockState blockState, CompoundTag compoundTag) {
        super.fromTag(blockState, compoundTag);

        tank.read(compoundTag);
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
        tank.write(compoundTag);

        return compoundTag;
    }

    /**
     * Gets recipe crafter.
     *
     * @return the recipe crafter
     */
    @Override
    public RecipeCrafter getRecipeCrafter() {
        return crafter;
    }

    /**
     * Can craft boolean.
     *
     * @param rebornRecipe the reborn recipe
     * @return the boolean
     */
    @Override
    public boolean canCraft(RebornRecipe rebornRecipe) {
        // Figure out when this gets called.
        return crafter.canCraftAgain();
    }

    /**
     * Is stack valid boolean.
     *
     * @param slotID the slot id
     * @param stack  the stack
     * @return the boolean
     */
    @Override
    public boolean isStackValid(int slotID, ItemStack stack) {
        return crafter.isStackValidInput(stack);
    }

    /**
     * Get input slots int [ ].
     *
     * @return the int [ ]
     */
    @Override
    public int[] getInputSlots() {
        return Arrays.copyOf(inputSlots, inputSlots.length);
    }

    /**
     * Sets remaining recipe time.
     *
     * @param remainingRecipeTime the remaining recipe time
     */
    public void setRemainingRecipeTime(int remainingRecipeTime) {
        this.remainingRecipeTime = remainingRecipeTime;
    }

    /**
     * Sets max recipe time.
     *
     * @param maxRecipeTime the max recipe time
     */
    public void setMaxRecipeTime(int maxRecipeTime) {
        this.maxRecipeTime = maxRecipeTime;
    }

    /**
     * Gets remaining recipe time.
     *
     * @return the remaining recipe time
     */
    public int getRemainingRecipeTime() {
        return this.remainingRecipeTime;
    }

    /**
     * Gets max recipe time.
     *
     * @return the max recipe time
     */
    public int getMaxRecipeTime() {
        return this.maxRecipeTime;
    }

    // Astromine is not stable and NetworkMember (interface) is being rewritten every update, so hold off on supporting Astromine cables.
//    @Override
//    public @NotNull Map<NetworkType, Collection<NetworkMemberType>> getMemberProperties() {
//        return ofTypes(AstromineNetworkTypes.FLUID, REQUESTER_PROVIDER, AstromineNetworkTypes.ENERGY, REQUESTER);
//    }
}
