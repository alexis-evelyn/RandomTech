package me.alexisevelyn.randomtech.utility;

import net.minecraft.util.Identifier;
import reborncore.common.crafting.RebornRecipe;
import reborncore.common.crafting.RebornRecipeType;
import reborncore.common.crafting.RecipeManager;

public class Recipes {
    Recipes() {
        super();
    }

    public static final RebornRecipeType<RebornRecipe> LIQUID_FUSER = RecipeManager.newRecipeType(RebornRecipe::new, new Identifier("randomtech:fuser"));
}
