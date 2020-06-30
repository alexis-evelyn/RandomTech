package me.alexisevelyn.randomtech.rei.plugin;

import me.alexisevelyn.randomtech.rei.plugin.recipedisplays.FirstRecipeDisplay;
import me.shedaniel.rei.api.DisplayHelper;
import me.shedaniel.rei.api.EntryRegistry;
import me.shedaniel.rei.api.RecipeDisplay;
import me.shedaniel.rei.api.RecipeHelper;
import me.shedaniel.rei.api.plugins.REIPluginV0;
import net.minecraft.util.Identifier;

public class REIPlugin implements REIPluginV0 {
    @Override
    public int getPriority() {
        // Smaller Number, Higher Priority
        return 0;
    }

    @Override
    public Identifier getPluginIdentifier() {
        return new Identifier("random_tech", "rei_plugin");
    }

    @Override
    public void registerEntries(EntryRegistry entryRegistry) {

    }

    @Override
    public void registerPluginCategories(RecipeHelper recipeHelper) {

    }

    @Override
    public void registerRecipeDisplays(RecipeHelper recipeHelper) {
        RecipeDisplay recipeDisplay = new FirstRecipeDisplay();

        recipeHelper.registerDisplay(recipeDisplay);
    }

    @Override
    public void registerBounds(DisplayHelper displayHelper) {

    }

    @Override
    public void registerOthers(RecipeHelper recipeHelper) {

    }

    @Override
    public void preRegister() {

    }

    @Override
    public void postRegister() {

    }
}