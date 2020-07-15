package me.alexisevelyn.randomtech.utility.recipemanagers;

import me.alexisevelyn.randomtech.blockentities.FluidMachineBlockEntityBase;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.EmptyFluid;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;
import reborncore.common.crafting.RebornFluidRecipe;
import reborncore.common.crafting.RebornRecipeType;
import reborncore.common.crafting.ingredient.RebornIngredient;
import reborncore.common.fluid.FluidValue;
import reborncore.common.fluid.container.FluidInstance;
import reborncore.common.util.Tank;

import java.util.Objects;

public class GenericFluidRecipe extends RebornFluidRecipe {
    public GenericFluidRecipe(RebornRecipeType<?> type, Identifier name) {
        super(type, name);
    }

    public GenericFluidRecipe(RebornRecipeType<?> type, Identifier name, DefaultedList<RebornIngredient> ingredients, DefaultedList<ItemStack> outputs, int power, int time) {
        super(type, name, ingredients, outputs, power, time);
    }

    public GenericFluidRecipe(RebornRecipeType<?> type, Identifier name, DefaultedList<RebornIngredient> ingredients, DefaultedList<ItemStack> outputs, int power, int time, FluidInstance fluidInstance) {
        super(type, name, ingredients, outputs, power, time, fluidInstance);
    }

    @Override
    public boolean canCraft(BlockEntity blockEntity) {
        // Super wrongly checks if tank's current fluid level
        // is more than or equal to recipe fluid when it
        // should be checking the tank's free space instead

        final Tank tank = getTank(blockEntity);

        if (tank == null)
            return false;

        final FluidInstance tankFluid = tank.getFluidInstance();

        if (tankFluid.isEmpty())
            return true;

        // To Not Waste Energy on Last "Possible" Craft
        return tank.getFreeSpace().equalOrMoreThan(getFluidInstance().getAmount());
    }

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

    @Nullable
    @Override
    public Tank getTank(BlockEntity blockEntity) {
        if (blockEntity instanceof FluidMachineBlockEntityBase)
            return ((FluidMachineBlockEntityBase) blockEntity).getTank();

        return null;
    }

    @Override
    public String getGroup() {
        return super.getGroup();
    }

    @Override
    public ItemStack getRecipeKindIcon() {
        return super.getRecipeKindIcon();
    }
}
