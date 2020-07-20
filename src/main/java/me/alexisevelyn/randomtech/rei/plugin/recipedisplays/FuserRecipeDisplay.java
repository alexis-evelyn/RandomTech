package me.alexisevelyn.randomtech.rei.plugin.recipedisplays;

import me.alexisevelyn.randomtech.api.utilities.recipemanagers.GenericFluidRecipe;
import me.alexisevelyn.randomtech.rei.plugin.REIPlugin;
import me.alexisevelyn.randomtech.utility.RegistryHelper;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeDisplay;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Items;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;
import reborncore.common.crafting.RecipeManager;

import java.util.Collections;
import java.util.List;

public class FuserRecipeDisplay implements RecipeDisplay {
    private final EntryStack ingredient;
    private final EntryStack fluid;
    private final EntryStack byproduct;

    public FuserRecipeDisplay(GenericFluidRecipe genericFluidRecipe) {
        // TODO: Validate data and ensure all ingredients and outputs are accounted for.
        // TODO: Make sure to setup category manager correctly to display all outputs.

        this.ingredient = EntryStack.create(genericFluidRecipe.getRebornIngredients().get(0).getPreview().getMatchingStacksClient()[0]);
        this.fluid = EntryStack.create(genericFluidRecipe.getFluidInstance().getFluid());
        this.byproduct = EntryStack.create(genericFluidRecipe.getOutputs().get(0));
    }

    public EntryStack getIngredient() {
        return this.ingredient;
    }

    public EntryStack getFluid() {
        return this.fluid;
    }

    public EntryStack getByproduct() {
        return this.byproduct;
    }

    @Override
    public List<List<EntryStack>> getInputEntries() {
        return Collections.singletonList(Collections.singletonList(ingredient));
    }

    @Override
    public List<EntryStack> getOutputEntries() {
        // TODO: Output both fluid and byproduct
        return Collections.singletonList(fluid);
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
