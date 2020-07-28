package me.alexisevelyn.randomtech.rei.plugin.autocrafting;

import me.shedaniel.rei.api.AutoTransferHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.recipe.Ingredient;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;

public class FuserAutocraftingHandler implements AutoTransferHandler {
    /**
     * @return the priority of this handler, higher priorities will be called first.
     */
//    @Override
//    public double getPriority() {
//        return 0;
//    }

    // I'm disabling the handler for now.
    // It's only partially implemented.
    @Override
    public Result handle(Context context) {
        // TODO: Figure out how to determine if Fuser Gui is open and change success result to not applicable if not on fuser
        // Look at: https://discordapp.com/channels/432055962233470986/576851123345031177/737345567788761188
//        RecipeDisplay display = context.getRecipe();
//
//        if (!(display instanceof FuserRecipeDisplay))
//            return Result.createNotApplicable();
//        if (!context.isActuallyCrafting())
//            return Result.createSuccessful();
//
//        FuserRecipeDisplay fuserRecipeDisplay = (FuserRecipeDisplay) display;
//        ScreenHandler container = context.getContainer();
//
//        if (!fuserRecipeDisplay.getOptionalRecipe().isPresent())
//            return Result.createNotApplicable();
//
//        Recipe<Inventory> recipe = fuserRecipeDisplay.getOptionalRecipe().get();
//        ClientPlayerEntity playerEntity = context.getMinecraft().player;
//
//        if (playerEntity == null)
//            return Result.createNotApplicable();
//
//        context.getMinecraft().openScreen(context.getContainerScreen());
//
//        List<Ingredient> inputs = recipe.getPreviewInputs();
//
//        if (inputs.size() == 0)
//            return Result.createNotApplicable();
//
//        this.transferItemStack(container, inputs.get(0), playerEntity, playerEntity.isHoldingSneakKey());
//        ScreenHelper.getLastOverlay().init();

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
