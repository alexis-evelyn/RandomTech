package me.alexisevelyn.randomtech.api.utilities.recipemanagers;

import me.alexisevelyn.randomtech.api.blockentities.BasePowerAcceptorBlockEntity;
import me.alexisevelyn.randomtech.api.blockentities.FluidMachineBlockEntityBase;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.EmptyFluid;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;
import reborncore.common.crafting.RebornFluidRecipe;
import reborncore.common.crafting.RebornRecipeType;
import reborncore.common.crafting.ingredient.RebornIngredient;
import reborncore.common.fluid.container.FluidInstance;
import reborncore.common.util.Tank;

/**
 * The type Generic fluid recipe.
 */
public class GenericFluidRecipe extends RebornFluidRecipe {
    /**
     * Instantiates a new Generic fluid recipe.
     *
     * @param type the type
     * @param name the name
     */
    public GenericFluidRecipe(RebornRecipeType<?> type, Identifier name) {
        super(type, name);
    }

    /**
     * Instantiates a new Generic fluid recipe.
     *
     * @param type        the type
     * @param name        the name
     * @param ingredients the ingredients
     * @param outputs     the outputs
     * @param power       the power
     * @param time        the time
     */
    @SuppressWarnings("unused")
    public GenericFluidRecipe(RebornRecipeType<?> type, Identifier name, DefaultedList<RebornIngredient> ingredients, DefaultedList<ItemStack> outputs, int power, int time) {
        super(type, name, ingredients, outputs, power, time);
    }

    /**
     * Instantiates a new Generic fluid recipe.
     *
     * @param type          the type
     * @param name          the name
     * @param ingredients   the ingredients
     * @param outputs       the outputs
     * @param power         the power
     * @param time          the time
     * @param fluidInstance the fluid instance
     */
    @SuppressWarnings("unused")
    public GenericFluidRecipe(RebornRecipeType<?> type, Identifier name, DefaultedList<RebornIngredient> ingredients, DefaultedList<ItemStack> outputs, int power, int time, FluidInstance fluidInstance) {
        super(type, name, ingredients, outputs, power, time, fluidInstance);
    }

    /**
     * Can craft boolean.
     *
     * @param blockEntity the block entity
     * @return the boolean
     */
    @Override
    public boolean canCraft(BlockEntity blockEntity) {
        // Super checks if tank's current fluid level
        // is more than or equal to recipe fluid when I
        // need it to be checking the tank's free space instead

        if (!(blockEntity instanceof BasePowerAcceptorBlockEntity))
            return false;

        if (!hasEnoughEnergy((BasePowerAcceptorBlockEntity) blockEntity))
            return false;

        final Tank tank = getTank(blockEntity);

        if (tank == null)
            return false;

        // To Not Waste Energy on Last "Possible" Craft
        return tank.getFreeSpace().equalOrMoreThan(getFluidInstance().getAmount());
    }

    /**
     * Has enough energy boolean.
     *
     * @param fuserBlockEntity the fuser block entity
     * @return the boolean
     */
    public boolean hasEnoughEnergy(BasePowerAcceptorBlockEntity fuserBlockEntity) {
        return fuserBlockEntity.hasEnoughEnergy(getPower() * getTime());
    }

    /**
     * On craft boolean.
     *
     * @param blockEntity the block entity
     * @return the boolean
     */
    @Override
    public boolean onCraft(BlockEntity blockEntity) {
        // Super Is responsible for removing fluid.
        // So I'm not calling super

        final Tank tank = getTank(blockEntity);

        if (tank == null)
            return false;

        final FluidInstance recipeFluid = getFluidInstance();
        final FluidInstance tankFluid = tank.getFluidInstance();

        if (tankFluid.getFluid() instanceof EmptyFluid)
            tankFluid.setFluid(recipeFluid.getFluid());

        if (tankFluid.getFluid().equals(recipeFluid.getFluid())) {
            if (tank.getFreeSpace().equalOrMoreThan(recipeFluid.getAmount())) {
                tankFluid.addAmount(recipeFluid.getAmount());
                return true;
            }
        }
        return false;
    }

    /**
     * Gets tank.
     *
     * @param blockEntity the block entity
     * @return the tank
     */
    @Nullable
    @Override
    public Tank getTank(BlockEntity blockEntity) {
        if (blockEntity instanceof FluidMachineBlockEntityBase)
            return ((FluidMachineBlockEntityBase) blockEntity).getTank();

        return null;
    }

    /**
     * Gets group.
     *
     * @return the group
     */
    @SuppressWarnings("EmptyMethod")
    @Override
    public String getGroup() {
        return super.getGroup();
    }

    /**
     * Gets recipe kind icon.
     *
     * @return the recipe kind icon
     */
    @SuppressWarnings("EmptyMethod")
    @Override
    public ItemStack getRecipeKindIcon() {
        return super.getRecipeKindIcon();
    }
}
