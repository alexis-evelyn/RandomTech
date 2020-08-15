package me.alexisevelyn.randomtech.utility;

import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.api.utilities.recipemanagers.GenericFluidRecipe;
import net.minecraft.util.Identifier;
import reborncore.common.crafting.RebornRecipeType;
import reborncore.common.crafting.RecipeManager;

/**
 * The type Recipes.
 */
public class Recipes {
    public static final RebornRecipeType<GenericFluidRecipe> LIQUID_FUSER = RecipeManager.newRecipeType(GenericFluidRecipe::new, new Identifier(Main.MODID, "fuser"));
}
