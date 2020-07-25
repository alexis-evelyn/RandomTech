package me.alexisevelyn.randomtech.rei.plugin;

import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.api.utilities.recipemanagers.GenericFluidRecipe;
import me.alexisevelyn.randomtech.rei.plugin.recipecategories.FuserRecipeCategory;
import me.alexisevelyn.randomtech.rei.plugin.recipedisplays.FuserRecipeDisplay;
import me.alexisevelyn.randomtech.utility.Recipes;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import me.shedaniel.rei.api.*;
import me.shedaniel.rei.api.plugins.REIPluginV0;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import reborncore.common.crafting.RecipeManager;

@Environment(EnvType.CLIENT)
public class REIPlugin implements REIPluginV0 {
    public static final Identifier FUSER = new Identifier(Main.MODID, "fuser_category");

//    @Override
//    public int getPriority() {
//        // Smaller Number, Higher Priority
//        return 0;
//    }

    @Override
    public Identifier getPluginIdentifier() {
        return new Identifier(Main.MODID, "rei_plugin");
    }

    @Override
    public void registerEntries(EntryRegistry entryRegistry) {
        // I believe this is used to register items that don't necessarily have recipes
    }

    @Override
    public void registerPluginCategories(RecipeHelper recipeHelper) {
        recipeHelper.registerCategory(new FuserRecipeCategory());
    }

    @Override
    public void registerRecipeDisplays(RecipeHelper recipeHelper) {
        RecipeManager.getRecipeTypes(Main.MODID).forEach(rebornRecipeType -> {
            if (rebornRecipeType == Recipes.LIQUID_FUSER)
                registerFuser(recipeHelper);
        });
    }

    @Override
    public void registerBounds(DisplayHelper displayHelper) {
        // I believe this is used to exclude certain recipes
    }

    @Override
    public void registerOthers(RecipeHelper recipeHelper) {
        // Register Working Station allows looking up recipes by that Workstation
        recipeHelper.registerWorkingStations(FUSER, EntryStack.create(RegistryHelper.FUSER));
    }

    @Override
    public void preRegister() {
        // Do stuff before registering
    }

    @Override
    public void postRegister() {
        // Do stuff after registering
    }

    private void registerFuser(RecipeHelper recipeHelper) {
        // Note to self, if your fluids are not showing up, check the identifier in the recipe json files.

        recipeHelper.registerRecipes(FUSER, GenericFluidRecipe.class, FuserRecipeDisplay::new);
    }
}