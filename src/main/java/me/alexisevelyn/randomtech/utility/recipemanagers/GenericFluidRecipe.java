package me.alexisevelyn.randomtech.utility.recipemanagers;

import me.alexisevelyn.randomtech.blockentities.FluidMachineBlockEntityBase;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;
import reborncore.common.crafting.RebornFluidRecipe;
import reborncore.common.crafting.RebornRecipeType;
import reborncore.common.crafting.ingredient.RebornIngredient;
import reborncore.common.fluid.container.FluidInstance;
import reborncore.common.util.Tank;

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
        return super.canCraft(blockEntity);
    }

    @Override
    public boolean onCraft(BlockEntity blockEntity) {
        return super.onCraft(blockEntity);
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
