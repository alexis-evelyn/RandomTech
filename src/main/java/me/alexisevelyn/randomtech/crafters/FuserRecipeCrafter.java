package me.alexisevelyn.randomtech.crafters;

import me.alexisevelyn.randomtech.blockentities.FuserBlockEntity;
import me.alexisevelyn.randomtech.utility.RecipesHelper;
import net.minecraft.block.entity.BlockEntity;
import reborncore.common.recipes.RecipeCrafter;
import reborncore.common.util.RebornInventory;

/**
 * The type Fuser recipe crafter.
 */
    // https://github.com/TechReborn/RebornCore/blob/1.16/src/main/java/reborncore/common/crafting/RebornFluidRecipe.java
public class FuserRecipeCrafter extends RecipeCrafter {
    /**
     * @param blockEntity Block Entity having this crafter
     * @param inventory   Inventory from parent blockEntity
     * @param inputSlots  Slot IDs for input
     */
    public FuserRecipeCrafter(BlockEntity blockEntity, RebornInventory<?> inventory, int[] inputSlots, int[] outputSlots) {
        super(RecipesHelper.LIQUID_FUSER, blockEntity, inputSlots.length, outputSlots.length, inventory, inputSlots, outputSlots);
    }


    /**
     * Update entity.
     */
    @SuppressWarnings("EmptyMethod")
    @Override
    public void updateEntity() {
        super.updateEntity();

        if (!(blockEntity instanceof FuserBlockEntity))
            return;

        // Update Fuser on Current Progress
        ((FuserBlockEntity) blockEntity).setMaxRecipeTime(currentNeededTicks);
        ((FuserBlockEntity) blockEntity).setRemainingRecipeTime(currentTickTime);

//        if (currentTickTime >= currentNeededTicks && canCraftAgain()) {
////            System.out.println("Can Fill Tank with: " + genericFluidRecipe.getFluidInstance().getAmount());
//            System.out.println("Attempt To Fill Tank: " + attemptAddFluidToTank(tank, genericFluidRecipe.getFluidInstance()));
//        }
    }

    /**
     * Update current recipe.
     */
    @SuppressWarnings("EmptyMethod")
    @Override
    public void updateCurrentRecipe() {
        super.updateCurrentRecipe();
    }

    /**
     * Can craft again boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean canCraftAgain() {
        return super.canCraftAgain();

//        if (!super.canCraftAgain())
//            return false;
//
//        // Current Recipe is always null?
//        if (!(blockEntity instanceof FuserBlockEntity))
//            return false;
//
//        tank = ((FuserBlockEntity) blockEntity).getTank();
//
//        if (tank == null)
//            return false;
//
//        return canFillTank(tank, (GenericFluidRecipe) currentRecipe);
    }

//    public boolean canFillTank(Tank tank, GenericFluidRecipe genericFluidRecipe) {
//        return tank.canInsertFluid(null, genericFluidRecipe.getFluidInstance().getFluid(), genericFluidRecipe.getFluidInstance().getAmount());
//    }
}
