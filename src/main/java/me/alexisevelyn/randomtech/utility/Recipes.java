package me.alexisevelyn.randomtech.utility;

import me.alexisevelyn.randomtech.utility.recipemanagers.GenericFluidRecipe;
import net.minecraft.util.Identifier;
import reborncore.common.crafting.RebornFluidRecipe;
import reborncore.common.crafting.RebornRecipe;
import reborncore.common.crafting.RebornRecipeType;
import reborncore.common.crafting.RecipeManager;

public class Recipes {
    Recipes() {
        super();
    }

    public static final RebornRecipeType<GenericFluidRecipe> LIQUID_FUSER = RecipeManager.newRecipeType(GenericFluidRecipe::new, new Identifier("randomtech:fuser"));
}
