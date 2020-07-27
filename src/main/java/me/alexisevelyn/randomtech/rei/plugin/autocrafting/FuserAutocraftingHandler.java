package me.alexisevelyn.randomtech.rei.plugin.autocrafting;

import me.alexisevelyn.randomtech.rei.plugin.recipedisplays.FuserRecipeDisplay;
import me.shedaniel.rei.api.AutoTransferHandler;
import me.shedaniel.rei.api.RecipeDisplay;
import me.shedaniel.rei.impl.ScreenHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;

import java.util.List;

public class FuserAutocraftingHandler implements AutoTransferHandler {
    /**
     * @return the priority of this handler, higher priorities will be called first.
     */
//    @Override
//    public double getPriority() {
//        return 0;
//    }

    @Override
    public Result handle(Context context) {
        // TODO: Figure out how to determine if Fuser Gui is open and change success result to not applicable if not on fuser
        RecipeDisplay display = context.getRecipe();

        // I'm disabling the handler for now.
        // It's only partially implemented.
        if (false)
            return Result.createNotApplicable();

        if (!(display instanceof FuserRecipeDisplay))
            return Result.createNotApplicable();
        if (!context.isActuallyCrafting())
            return Result.createSuccessful();

        FuserRecipeDisplay fuserRecipeDisplay = (FuserRecipeDisplay) display;
        ScreenHandler container = context.getContainer();

        if (!fuserRecipeDisplay.getOptionalRecipe().isPresent())
            return Result.createNotApplicable();

        Recipe<Inventory> recipe = fuserRecipeDisplay.getOptionalRecipe().get();
        ClientPlayerEntity playerEntity = context.getMinecraft().player;

        if (playerEntity == null)
            return Result.createNotApplicable();

        context.getMinecraft().openScreen(context.getContainerScreen());

        List<Ingredient> inputs = recipe.getPreviewInputs();

        if (inputs.size() == 0)
            return Result.createNotApplicable();

        this.transferItemStack(container, inputs.get(0), playerEntity, playerEntity.isHoldingSneakKey());
        ScreenHelper.getLastOverlay().init();

        return Result.createNotApplicable();
    }

    public void transferItemStack(ScreenHandler container, Ingredient input, ClientPlayerEntity playerEntity, boolean isShifting) {
        // TODO: Figure out how to finalize slot transfer
        // TODO: Copy player inventory and find applicable item to transfer
        // TODO: Figure out how to choose between one item and whole stack

        int inventorySlot = 0;
        final int fuserSlot = 0;

        if (container.slots.size() == 0)
            return;

        if (!container.canInsertIntoSlot(container.transferSlot(playerEntity, inventorySlot), container.slots.get(fuserSlot)))
            return;

        container.onSlotClick(inventorySlot, fuserSlot, SlotActionType.QUICK_MOVE, playerEntity);
    }
}
