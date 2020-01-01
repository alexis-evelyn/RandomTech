package me.alexisevelyn.randomtech.crafters;

import me.alexisevelyn.randomtech.blockentities.FluidMachineBlockEntityBase;
import me.alexisevelyn.randomtech.utility.Recipes;
import me.alexisevelyn.randomtech.utility.recipemanagers.GenericFluidRecipe;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.EmptyFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import reborncore.common.crafting.RebornRecipe;
import reborncore.common.fluid.FluidValue;
import reborncore.common.fluid.container.FluidInstance;
import reborncore.common.recipes.RecipeCrafter;
import reborncore.common.util.RebornInventory;
import reborncore.common.util.Tank;

// https://github.com/TechReborn/RebornCore/blob/1.16/src/main/java/reborncore/common/crafting/RebornFluidRecipe.java
public class FuserRecipeCrafter extends RecipeCrafter {
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

        if (!(blockEntity instanceof FluidMachineBlockEntityBase))
            return;

        if (!(currentRecipe instanceof GenericFluidRecipe))
            return;

        Tank tank = ((FluidMachineBlockEntityBase) blockEntity).getTank();
        GenericFluidRecipe genericFluidRecipe = (GenericFluidRecipe) currentRecipe;

        if (tank == null)
            return;

        // TODO: Only add fluid once processing is finished
        // TODO: Add check if able to add fluid and adjust tank size accordingly
        if (currentTickTime >= currentNeededTicks && canFillTank(tank, genericFluidRecipe)) {
            System.out.println("Can Fill Tank with: " + genericFluidRecipe.getFluidInstance().getAmount());

            System.out.println("Attempt To Fill Tank: " + attemptAddFluidToTank(tank, genericFluidRecipe.getFluidInstance()));
        }
    }

    @Override
    public void updateCurrentRecipe() {
        super.updateCurrentRecipe();
    }

    @Override
    public boolean canCraftAgain() {
        return super.canCraftAgain();
    }

    public boolean canFillTank(Tank tank, GenericFluidRecipe genericFluidRecipe) {
        return tank.canInsertFluid(null, genericFluidRecipe.getFluidInstance().getFluid(), genericFluidRecipe.getFluidInstance().getAmount());
    }

    public boolean attemptAddFluidToTank(Tank tank, FluidInstance fluidInstance) {
        if (tank.getFluid() instanceof EmptyFluid)
            tank.setFluid(fluidInstance.getFluid());
        else if (!tank.getFluid().matchesType(fluidInstance.getFluid()))
            return false;

        System.out.println("Tank Amount: " + tank.getFluidAmount());
        FluidValue amount = tank.getFluidAmount().add(fluidInstance.getAmount());
        System.out.println("After Tank Amount: " + amount);

        if (amount.moreThan(tank.getFreeSpace()))
            return false;

        tank.setFluidAmount(amount);

        return true;
    }
}
