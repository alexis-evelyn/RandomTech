package me.alexisevelyn.randomtech.rei.plugin;

import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.api.utilities.recipemanagers.GenericFluidRecipe;
import me.alexisevelyn.randomtech.rei.plugin.autocrafting.FuserAutocraftingHandler;
import me.alexisevelyn.randomtech.rei.plugin.recipecategories.FuserRecipeCategory;
import me.alexisevelyn.randomtech.rei.plugin.recipedisplays.FuserRecipeDisplay;
import me.alexisevelyn.randomtech.utility.RecipesHelper;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import me.shedaniel.rei.api.DisplayHelper;
import me.shedaniel.rei.api.EntryRegistry;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeHelper;
import me.shedaniel.rei.api.plugins.REIPluginV0;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import reborncore.common.crafting.RecipeManager;

/**
 * The type Rei plugin.
 */
@Environment(EnvType.CLIENT)
public class REIPlugin implements REIPluginV0 {
    public static final Identifier FUSER = new Identifier(Main.MODID, "fuser");

//    @Override
//    public int getPriority() {
//        // Smaller Number, Higher Priority
//        return 0;
//    }

    /**
     * Gets plugin identifier.
     *
     * @return the plugin identifier
     */
    @Override
    public Identifier getPluginIdentifier() {
        return new Identifier(Main.MODID, "rei_plugin");
    }

    /**
     * Register entries.
     *
     * @param entryRegistry the entry registry
     */
    @Override
    public void registerEntries(EntryRegistry entryRegistry) {
        // I believe this is used to register items that don't necessarily have recipes
    }

    /**
     * Register plugin categories.
     *
     * @param recipeHelper the recipe helper
     */
    @Override
    public void registerPluginCategories(RecipeHelper recipeHelper) {
        recipeHelper.registerCategory(new FuserRecipeCategory());
    }

    /**
     * Register recipe displays.
     *
     * @param recipeHelper the recipe helper
     */
    @Override
    public void registerRecipeDisplays(RecipeHelper recipeHelper) {
        RecipeManager.getRecipeTypes(Main.MODID).forEach(rebornRecipeType -> {
            if (rebornRecipeType == RecipesHelper.LIQUID_FUSER)
                registerFuser(recipeHelper);
        });
    }

    /**
     * Register bounds.
     *
     * @param displayHelper the display helper
     */
    @Override
    public void registerBounds(DisplayHelper displayHelper) {
        // I believe this is used to exclude certain recipes
    }

    /**
     * Register others.
     *
     * @param recipeHelper the recipe helper
     */
    @Override
    public void registerOthers(RecipeHelper recipeHelper) {
        // Register Working Station allows looking up recipes by that Workstation
        recipeHelper.registerWorkingStations(FUSER, EntryStack.create(RegistryHelper.FUSER));

        // TODO: Move button to somewhere other than the input slot as REI won't let you click items in if it's blocking the slot
        // recipeHelper.registerScreenClickArea(new Rectangle(FuserGui.ingredientSlotX, FuserGui.ingredientSlotY, FuserGui.inputSlotX, FuserGui.inputSlotY), FuserGui.class, FUSER); // Adds the show recipes for Fuser tooltip
        // recipeHelper.registerAutoCraftingHandler(new FuserAutocraftingHandler());
    }

    /**
     * Pre register.
     */
    @Override
    public void preRegister() {
        // Do stuff before registering
    }

    /**
     * Post register.
     */
    @Override
    public void postRegister() {
        // Do stuff after registering
    }

    /**
     * Register fuser.
     *
     * @param recipeHelper the recipe helper
     */
    private void registerFuser(RecipeHelper recipeHelper) {
        // Note to self, if your fluids are not showing up, check the identifier in the recipe json files.
        recipeHelper.registerRecipes(FUSER, GenericFluidRecipe.class, FuserRecipeDisplay::new);
    }
}