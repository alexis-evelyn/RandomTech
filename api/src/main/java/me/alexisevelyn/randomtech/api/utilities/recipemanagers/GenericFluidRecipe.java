package me.alexisevelyn.randomtech.api.utilities.recipemanagers;

import me.alexisevelyn.randomtech.api.blockentities.BasePowerAcceptorBlockEntity;
import me.alexisevelyn.randomtech.api.blockentities.FluidMachineBlockEntityBase;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.EmptyFluid;
import net.minecraft.util.Identifier;
import org.apiguardian.api.API;
import org.jetbrains.annotations.Nullable;
import reborncore.common.crafting.RebornFluidRecipe;
import reborncore.common.crafting.RebornRecipe;
import reborncore.common.crafting.RebornRecipeType;
import reborncore.common.fluid.container.FluidInstance;
import reborncore.common.util.Tank;

/**
 * Fluid Recipe Type To Produce Liquids instead of RebornCore's Fluid Recipe To Consume Liquids (see {@link RebornFluidRecipe}).
 */
public class GenericFluidRecipe extends RebornFluidRecipe {
    /**
     * Create a Fluid Recipe For Use With RandomTech machines.
     * <br><br>
     *
     * For the type, the example is:
     * <pre><code>
     * public static final RebornRecipeType<GenericFluidRecipe> LIQUID_FUSER = RecipeManager.newRecipeType(GenericFluidRecipe::new, new Identifier(Main.MODID, "fuser"));
     * </code></pre>
     *
     * @param type the type of recipe (used to help determine which machine processes recipe)
     * @param name the name to be used to identify the recipe (Can be retrieved with {@link RebornRecipe#getId()})
     */
    @API(status = API.Status.STABLE)
    public GenericFluidRecipe(RebornRecipeType<?> type, Identifier name) {
        super(type, name);
    }

    /**
     * Checks if the machine has the ability to handle crafting this recipe.
     *
     * This checks if it's a {@link BasePowerAcceptorBlockEntity}, if the machine has enough energy (internally calling {@link #hasEnoughEnergy(BasePowerAcceptorBlockEntity)}, and if the tank {@link #getTank(BlockEntity)} can hold the amount of fluid produced by the recipe.
     * <br><br>
     *
     * {@inheritDoc}
     * <br><br>
     *
     * @param blockEntity the {@link BlockEntity} that extends {@link BasePowerAcceptorBlockEntity}.
     * @return true if can craft, false if cannot craft.
     */
    @API(status = API.Status.STABLE)
    @Override
    public boolean canCraft(BlockEntity blockEntity) {
        // Super checks if tank's current fluid level
        // is more than or equal to recipe fluid when I
        // need it to be checking the tank's free space instead

        if (!(blockEntity instanceof BasePowerAcceptorBlockEntity))
            return false;

        if (!hasEnoughEnergy((BasePowerAcceptorBlockEntity) blockEntity))
            return false;

        final Tank tank = getTank(blockEntity);

        if (tank == null)
            return false;

        // To Not Waste Energy on Last "Possible" Craft
        return tank.getFreeSpace().equalOrMoreThan(getFluidInstance().getAmount());
    }

    /**
     * Checks if the machine has enough energy to process the recipe.
     * <br><br>
     *
     * @param powerAcceptorBlockEntity the power accepting block entity
     * @return true if can process the recipe at least for one turn. false if cannot.
     */
    @API(status = API.Status.STABLE)
    public boolean hasEnoughEnergy(BasePowerAcceptorBlockEntity powerAcceptorBlockEntity) {
        return powerAcceptorBlockEntity.hasEnoughEnergy(getPower() * getTime());
    }

    /**
     * Called when RebornCore performs a crafting operation.
     * <br><br>
     *
     * {@inheritDoc}
     * <br><br>
     *
     * @param blockEntity the machine's {@link BlockEntity}.
     * @return true if crafting, false if not
     */
    @API(status = API.Status.STABLE)
    @Override
    public boolean onCraft(BlockEntity blockEntity) {
        // Super Is responsible for removing fluid.
        // So I'm not calling super

        final Tank tank = getTank(blockEntity);

        if (tank == null)
            return false;

        final FluidInstance recipeFluid = getFluidInstance();
        final FluidInstance tankFluid = tank.getFluidInstance();

        if (tankFluid.getFluid() instanceof EmptyFluid)
            tankFluid.setFluid(recipeFluid.getFluid());

        if (tankFluid.getFluid().equals(recipeFluid.getFluid()) && tank.getFreeSpace().equalOrMoreThan(recipeFluid.getAmount())) {
            tankFluid.addAmount(recipeFluid.getAmount());
            return true;
        }

        return false;
    }

    /**
     * Retrieve's tank from {@link BlockEntity}. Block entity must be an instance of {@link FluidMachineBlockEntityBase}.
     * <br><br>
     *
     * {@inheritDoc}
     * <br><br>
     *
     * @param blockEntity a {@link BlockEntity} that's an instance of {@link FluidMachineBlockEntityBase}.
     * @return the BlockEntity's {@link Tank} or null.
     */
    @API(status = API.Status.STABLE)
    @Nullable
    @Override
    public Tank getTank(BlockEntity blockEntity) {
        if (blockEntity instanceof FluidMachineBlockEntityBase)
            return ((FluidMachineBlockEntityBase) blockEntity).getTank();

        return null;
    }
}
