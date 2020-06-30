package me.alexisevelyn.randomtech.rei.plugin.recipedisplays;

import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeDisplay;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;

public class FirstRecipeDisplay implements RecipeDisplay {
    @Override
    public List<List<EntryStack>> getInputEntries() {
        return null;
    }

    @Override
    public List<EntryStack> getOutputEntries() {
        return null;
    }

    @Override
    public List<List<EntryStack>> getRequiredEntries() {
        return null;
    }

    @Override
    public Identifier getRecipeCategory() {
        return new Identifier("random_tech", "first_recipe_display");
    }

    @Override
    public Optional<Identifier> getRecipeLocation() {
        return Optional.empty();
    }
}
