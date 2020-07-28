package me.alexisevelyn.randomtech.api.utilities.recipemanagers;

import me.alexisevelyn.randomtech.api.blockentities.BasePowerAcceptorBlockEntity;
import me.alexisevelyn.randomtech.api.blockentities.FluidMachineBlockEntityBase;
import me.alexisevelyn.randomtech.blockentities.FuserBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.EmptyFluid;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;
import reborncore.common.crafting.RebornFluidRecipe;
import reborncore.common.crafting.RebornRecipe;
import reborncore.common.crafting.RebornRecipeType;
import reborncore.common.crafting.ingredient.RebornIngredient;
import reborncore.common.fluid.container.FluidInstance;
import reborncore.common.util.Tank;

public class GenericFluidRecipe extends RebornFluidRecipe {
    public GenericFluidRecipe(RebornRecipeType<?> type, Identifier name) {
        super(type, name);
    }

    @SuppressWarnings("unused")
    public GenericFluidRecipe(RebornRecipeType<?> type, Identifier name, DefaultedList<RebornIngredient> ingredients, DefaultedList<ItemStack> outputs, int power, int time) {
        super(type, name, ingredients, outputs, power, time);
    }

    @SuppressWarnings("unused")
    public GenericFluidRecipe(RebornRecipeType<?> type, Identifier name, DefaultedList<RebornIngredient> ingredients, DefaultedList<ItemStack> outputs, int power, int time, FluidInstance fluidInstance) {
        super(type, name, ingredients, outputs, power, time, fluidInstance);
    }

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

    public boolean hasEnoughEnergy(BasePowerAcceptorBlockEntity fuserBlockEntity) {
        return fuserBlockEntity.hasEnoughEnergy(getPower() * getTime());
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

    @SuppressWarnings("EmptyMethod")
    @Override
    public String getGroup() {
        return super.getGroup();
    }

    @SuppressWarnings("EmptyMethod")
    @Override
    public ItemStack getRecipeKindIcon() {
        return super.getRecipeKindIcon();
    }
}
