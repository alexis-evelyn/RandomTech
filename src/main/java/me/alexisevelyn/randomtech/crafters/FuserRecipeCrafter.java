package me.alexisevelyn.randomtech.crafters;

import me.alexisevelyn.randomtech.utility.Recipes;
import me.alexisevelyn.randomtech.utility.recipemanagers.GenericFluidRecipe;
import net.minecraft.block.entity.BlockEntity;
import reborncore.common.crafting.RebornFluidRecipe;
import reborncore.common.crafting.RebornRecipe;
import reborncore.common.recipes.RecipeCrafter;
import reborncore.common.util.RebornInventory;

import java.util.List;

// https://github.com/TechReborn/RebornCore/blob/1.16/src/main/java/reborncore/common/crafting/RebornFluidRecipe.java
public class FuserRecipeCrafter extends RecipeCrafter {
    /**
     * @param blockEntity Block Entity having this crafter
     * @param inventory Inventory from parent blockEntity
     * @param inputSlots Slot IDs for input
     */
    public FuserRecipeCrafter(BlockEntity blockEntity, RebornInventory<?> inventory, int[] inputSlots, int[] outputSlots) {
        super(Recipes.LIQUID_FUSER, blockEntity, inputSlots.length, outputSlots.length, inventory, inputSlots, outputSlots);
    }

    @Override
    public void updateCurrentRecipe() {
        List<GenericFluidRecipe> recipeList = Recipes.LIQUID_FUSER.getRecipes(blockEntity.getWorld());

        if(recipeList.isEmpty() || blockEntity.getWorld() == null) {
            setCurrentRecipe(null);
            return;
        }

        int random = blockEntity.getWorld().random.nextInt(recipeList.size());

        // Sets the current recipe then syncs
        setCurrentRecipe(recipeList.get(random));

        this.currentNeededTicks = Math.max((int) (currentRecipe.getTime() * (1.0 - getSpeedMultiplier())), 1);
        this.currentTickTime = 0;

        setIsActive();
    }
}
