package me.alexisevelyn.randomtech.rei.plugin.recipecategories;

import com.google.common.collect.Lists;
import me.alexisevelyn.randomtech.rei.plugin.REIPlugin;
import me.alexisevelyn.randomtech.rei.plugin.recipedisplays.FuserRecipeDisplay;
import me.alexisevelyn.randomtech.utility.RegistryHelper;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeCategory;
import me.shedaniel.rei.api.widgets.Widgets;
import me.shedaniel.rei.gui.widget.Widget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.util.Identifier;

import java.util.List;

public class FuserRecipeCategory implements RecipeCategory<FuserRecipeDisplay> {

    @Override
    public Identifier getIdentifier() {
        return REIPlugin.FUSER;
    }

    @Override
    public EntryStack getLogo() {
        return EntryStack.create(RegistryHelper.FUSER);
    }

    @Override
    public String getCategoryName() {
        return I18n.translate("text.rei.category.fuser");
    }

    @Override
    public List<Widget> setupDisplay(FuserRecipeDisplay recipeDisplay, Rectangle bounds) {
        // TODO: Create custom display setup for my fuser.

        Point startPoint = new Point(bounds.getCenterX() - 41, bounds.getCenterY() - 13);
        List<Widget> widgets = Lists.newArrayList();

        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 27, startPoint.y + 4)));
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 61, startPoint.y + 5)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 4, startPoint.y + 5)).entry(recipeDisplay.getIngredient()).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 61, startPoint.y + 5)).entry(recipeDisplay.getFluid()).disableBackground().markOutput());
        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 36;
    }
}