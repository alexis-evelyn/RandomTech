package me.alexisevelyn.randomtech.rei.plugin.recipecategories;

import com.google.common.collect.Lists;
import me.alexisevelyn.randomtech.rei.plugin.REIPlugin;
import me.alexisevelyn.randomtech.rei.plugin.recipedisplays.FuserRecipeDisplay;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeCategory;
import me.shedaniel.rei.api.widgets.Widgets;
import me.shedaniel.rei.gui.widget.Widget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * The type Fuser recipe category.
 */
public class FuserRecipeCategory implements RecipeCategory<FuserRecipeDisplay> {

    /**
     * Gets identifier.
     *
     * @return the identifier
     */
    @Override
    public @NotNull Identifier getIdentifier() {
        return REIPlugin.FUSER;
    }

    /**
     * Gets logo.
     *
     * @return the logo
     */
    @Override
    public @NotNull EntryStack getLogo() {
        return EntryStack.create(RegistryHelper.FUSER);
    }

    /**
     * Gets category name.
     *
     * @return the category name
     */
    @Override
    public @NotNull String getCategoryName() {
        return I18n.translate("text.rei.category.fuser");
    }

    /**
     * Sets display.
     *
     * @param recipeDisplay the recipe display
     * @param bounds        the bounds
     * @return the display
     */
    @Override
    public @NotNull List<Widget> setupDisplay(FuserRecipeDisplay recipeDisplay, Rectangle bounds) {
        EntryStack input = recipeDisplay.getIngredient();
        EntryStack fluid = recipeDisplay.getFluid();
        EntryStack byproduct = recipeDisplay.getByproduct();

        int recipeCraftTime = recipeDisplay.getRecipeCraftTime();

        List<Widget> widgets = Lists.newArrayList();

        // Both input and fluid needs to exist or the recipe is moot.
        if (input == null || fluid == null)
            return widgets;

        widgets.add(Widgets.createRecipeBase(bounds));

        Point startPoint = this.createStartPoint(bounds, 41, 13);

        if (byproduct != null) {
            // Change Start Point to Make Room for Byproduct - The start point is reversed as it calculates from the center. Add the number to go left/up
            startPoint = this.createStartPoint(bounds, 66, 13);

            // Input
            this.createInputSlot(startPoint, widgets, input, 4, 5);

            // Arrow
            this.createArrow(startPoint, widgets, 27, 4, recipeCraftTime);

            // Fluid Output
            this.createOutputSlot(startPoint, widgets, fluid, 61, 5);

            // Byproduct
            this.createOutputSlot(startPoint, widgets, byproduct, 90, 5); // 26 is the amount to perfectly line up output slots
        } else {
            // Input
            this.createInputSlot(startPoint, widgets, input, 4, 5);

            // Arrow
            this.createArrow(startPoint, widgets, 27, 4, recipeCraftTime);

            // Fluid Output
            this.createOutputSlot(startPoint, widgets, fluid, 61, 5);
        }

        return widgets;
    }

    /**
     * Create start point point.
     *
     * @param bounds the bounds
     * @param x      the x
     * @param y      the y
     * @return the point
     */
    public Point createStartPoint(Rectangle bounds, int x, int y) {
        return new Point(bounds.getCenterX() - x, bounds.getCenterY() - y);
    }

    /**
     * Create arrow.
     *
     * @param startPoint      the start point
     * @param widgets         the widgets
     * @param x               the x
     * @param y               the y
     * @param recipeCraftTime the recipe craft time
     */
    public void createArrow(Point startPoint, List<Widget> widgets, int x, int y, int recipeCraftTime) {
        widgets.add(Widgets.createArrow(new Point(startPoint.x + x, startPoint.y + y)).animationDurationTicks(recipeCraftTime));
    }

    /**
     * Create input slot.
     *
     * @param startPoint the start point
     * @param widgets    the widgets
     * @param entryStack the entry stack
     * @param x          the x
     * @param y          the y
     */
    public void createInputSlot(Point startPoint, List<Widget> widgets, EntryStack entryStack, int x, int y) {
        widgets.add(Widgets.createSlot(new Point(startPoint.x + x, startPoint.y + y)).entry(entryStack).markInput());
    }

    /**
     * Create output slot.
     *
     * @param startPoint the start point
     * @param widgets    the widgets
     * @param entryStack the entry stack
     * @param x          the x
     * @param y          the y
     */
    public void createOutputSlot(Point startPoint, List<Widget> widgets, EntryStack entryStack, int x, int y) {
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + x, startPoint.y + y)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + x, startPoint.y + y)).entry(entryStack).disableBackground().markOutput());
    }

    /**
     * Gets display height.
     *
     * @return the display height
     */
    @Override
    public int getDisplayHeight() {
        return 36;
    }
}