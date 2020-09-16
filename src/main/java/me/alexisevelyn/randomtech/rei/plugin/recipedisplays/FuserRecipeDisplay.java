package me.alexisevelyn.randomtech.rei.plugin.recipedisplays;

import me.alexisevelyn.randomtech.api.utilities.recipemanagers.GenericFluidRecipe;
import me.alexisevelyn.randomtech.rei.plugin.REIPlugin;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeDisplay;
import me.shedaniel.rei.utils.CollectionUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reborncore.common.crafting.ingredient.RebornIngredient;
import reborncore.common.fluid.container.FluidInstance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type Fuser recipe display.
 */
public class FuserRecipeDisplay implements RecipeDisplay {
    private EntryStack ingredient;
    private EntryStack fluid;
    private EntryStack byproduct;
    private int recipeCraftTime = 20;

    /**
     * Instantiates a new Fuser recipe display.
     *
     * @param genericFluidRecipe the generic fluid recipe
     */
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

    /**
     * Gets ingredient.
     *
     * @return the ingredient
     */
    public @Nullable EntryStack getIngredient() {
        return this.ingredient;
    }

    /**
     * Gets fluid.
     *
     * @return the fluid
     */
    public @Nullable EntryStack getFluid() {
        return this.fluid;
    }

    /**
     * Gets byproduct.
     *
     * @return the byproduct
     */
    public @Nullable EntryStack getByproduct() {
        return this.byproduct;
    }

    /**
     * Gets recipe craft time.
     *
     * @return the recipe craft time
     */
    public int getRecipeCraftTime() {
        return this.recipeCraftTime;
    }

    /**
     * Gets input entries.
     *
     * @return the input entries
     */
    @Override
    public @NotNull List<List<EntryStack>> getInputEntries() {
        return Collections.singletonList(Collections.singletonList(ingredient));
    }

    /**
     * Gets output entries.
     *
     * @return the output entries
     */
    @Override
    public @NotNull List<List<EntryStack>> getResultingEntries() {
        ArrayList<EntryStack> outputEntries = new ArrayList<>();

        if (fluid != null)
            outputEntries.add(fluid);

        if (byproduct != null)
            outputEntries.add(byproduct);

        return CollectionUtils.map(outputEntries, Collections::singletonList);
    }

    /**
     * Gets recipe category.
     *
     * @return the recipe category
     */
    @Override
    public @NotNull Identifier getRecipeCategory() {
        return REIPlugin.FUSER;
    }

    /**
     * Gets required entries.
     *
     * @return the required entries
     */
    @Override
    public @NotNull List<List<EntryStack>> getRequiredEntries() {
        return getInputEntries();
    }
}
