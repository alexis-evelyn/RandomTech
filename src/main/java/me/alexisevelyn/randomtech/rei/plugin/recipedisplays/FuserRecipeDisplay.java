package me.alexisevelyn.randomtech.rei.plugin.recipedisplays;

import me.alexisevelyn.randomtech.api.utilities.recipemanagers.GenericFluidRecipe;
import me.alexisevelyn.randomtech.rei.plugin.REIPlugin;
import me.alexisevelyn.randomtech.utility.RegistryHelper;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeDisplay;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import reborncore.common.crafting.RecipeManager;
import reborncore.common.crafting.ingredient.RebornIngredient;
import reborncore.common.fluid.container.FluidInstance;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FuserRecipeDisplay implements RecipeDisplay {
    private EntryStack ingredient;
    private EntryStack fluid;
    private EntryStack byproduct;
    private int recipeCraftTime = 20;

    public FuserRecipeDisplay(GenericFluidRecipe genericFluidRecipe) {
        // You can't craft something without an input in the fuser
        if (genericFluidRecipe.getRebornIngredients().size() <= 0)
            return;

        RebornIngredient input = genericFluidRecipe.getRebornIngredients().get(0);
        FluidInstance fluidInstance = genericFluidRecipe.getFluidInstance();

        // I require fluid output for my Fuser recipe as that's the whole point of the fuser
        if (fluidInstance.equals(FluidInstance.EMPTY))
            return;

        ItemStack[] itemStacks = input.getPreview().getMatchingStacksClient();

        // We need at least one item stack. Only the first one will be processed though as their's only one input.
        if (itemStacks.length <= 0)
            return;

        this.ingredient = EntryStack.create(itemStacks[0]);
        this.fluid = EntryStack.create(fluidInstance.getFluid(), fluidInstance.getAmount().getRawValue());

        // Get recipe craft time
        this.recipeCraftTime = genericFluidRecipe.getTime();

        // Byproducts are not required for the recipe, so we just return at this point if it doesn't exist
        if (genericFluidRecipe.getOutputs().size() <= 0)
            return;

        ItemStack output = genericFluidRecipe.getOutputs().get(0);
        this.byproduct = EntryStack.create(output);
    }

    public @Nullable EntryStack getIngredient() {
        return this.ingredient;
    }

    public @Nullable EntryStack getFluid() {
        return this.fluid;
    }

    public @Nullable EntryStack getByproduct() {
        return this.byproduct;
    }

    public int getRecipeCraftTime() {
        return this.recipeCraftTime;
    }

    @Override
    public List<List<EntryStack>> getInputEntries() {
        return Collections.singletonList(Collections.singletonList(ingredient));
    }

    @Override
    public List<EntryStack> getOutputEntries() {
        // TODO: Output both fluid and byproduct
        ArrayList<EntryStack> outputEntries = new ArrayList<>();

        outputEntries.add(fluid);
        outputEntries.add(byproduct);

        return outputEntries;
    }

    @Override
    public Identifier getRecipeCategory() {
        return REIPlugin.FUSER;
    }

    @Override
    public List<List<EntryStack>> getRequiredEntries() {
        return getInputEntries();
    }
}
