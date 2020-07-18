package me.alexisevelyn.randomtech.crafters;

import me.alexisevelyn.randomtech.api.blockentities.FluidMachineBlockEntityBase;
import me.alexisevelyn.randomtech.utility.Recipes;
import me.alexisevelyn.randomtech.api.utilities.recipemanagers.GenericFluidRecipe;
import net.minecraft.block.entity.BlockEntity;
import reborncore.common.recipes.RecipeCrafter;
import reborncore.common.util.RebornInventory;
import reborncore.common.util.Tank;

// https://github.com/TechReborn/RebornCore/blob/1.16/src/main/java/reborncore/common/crafting/RebornFluidRecipe.java
public class FuserRecipeCrafter extends RecipeCrafter {
    Tank tank;
    GenericFluidRecipe genericFluidRecipe;

    /**
     * @param blockEntity Block Entity having this crafter
     * @param inventory   Inventory from parent blockEntity
     * @param inputSlots  Slot IDs for input
     */
    public FuserRecipeCrafter(BlockEntity blockEntity, RebornInventory<?> inventory, int[] inputSlots, int[] outputSlots) {
        super(Recipes.LIQUID_FUSER, blockEntity, inputSlots.length, outputSlots.length, inventory, inputSlots, outputSlots);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

//        if (currentTickTime >= currentNeededTicks && canCraftAgain()) {
////            System.out.println("Can Fill Tank with: " + genericFluidRecipe.getFluidInstance().getAmount());
//            System.out.println("Attempt To Fill Tank: " + attemptAddFluidToTank(tank, genericFluidRecipe.getFluidInstance()));
//        }
    }

    @Override
    public void updateCurrentRecipe() {
        super.updateCurrentRecipe();
    }

    @Override
    public boolean canCraftAgain() {
        if (!super.canCraftAgain())
            return false;

        if (!(blockEntity instanceof FluidMachineBlockEntityBase))
            return false;

        if (!(currentRecipe instanceof GenericFluidRecipe))
            return false;

        tank = ((FluidMachineBlockEntityBase) blockEntity).getTank();
        genericFluidRecipe = (GenericFluidRecipe) currentRecipe;

        if (tank == null)
            return false;

        return canFillTank(tank, genericFluidRecipe);
    }

    public boolean canFillTank(Tank tank, GenericFluidRecipe genericFluidRecipe) {
        return tank.canInsertFluid(null, genericFluidRecipe.getFluidInstance().getFluid(), genericFluidRecipe.getFluidInstance().getAmount());
    }
}
