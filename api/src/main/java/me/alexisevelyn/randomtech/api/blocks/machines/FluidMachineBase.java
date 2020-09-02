package me.alexisevelyn.randomtech.api.blocks.machines;

import me.alexisevelyn.randomtech.api.blockentities.FluidMachineBlockEntityBase;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import reborncore.common.fluid.FluidValue;

/**
 * The type Fluid machine base.
 */
public abstract class FluidMachineBase extends PowerAcceptorBlock {
    /**
     * Instantiates a new Fluid machine base.
     *
     * @param settings the settings
     */
    public FluidMachineBase(AbstractBlock.Settings settings) {
        super(settings);
    }

    /**
     * Instantiates a new Fluid machine base.
     *
     * @param settings     the settings
     * @param customStates the custom states
     */
    public FluidMachineBase(AbstractBlock.Settings settings, boolean customStates) {
        super(settings, customStates);
    }

    /**
     * Gets fluid level.
     *
     * @param state the state
     * @param world the world
     * @param pos   the pos
     * @return the fluid level
     */
    @Nullable
    public FluidValue getFluidLevel(BlockState state, World world, BlockPos pos) {
        FluidMachineBlockEntityBase fluidMachineBlockEntity = (FluidMachineBlockEntityBase) world.getBlockEntity(pos);

        if (fluidMachineBlockEntity == null) {
            return null;
        }

        return fluidMachineBlockEntity.getFluidLevel();
    }

    /**
     * Gets max fluid level.
     *
     * @param state the state
     * @param world the world
     * @param pos   the pos
     * @return the max fluid level
     */
    @Nullable
    public FluidValue getMaxFluidLevel(BlockState state, World world, BlockPos pos) {
        FluidMachineBlockEntityBase fluidMachineBlockEntity = (FluidMachineBlockEntityBase) world.getBlockEntity(pos);

        if (fluidMachineBlockEntity == null) {
            return null;
        }

        return fluidMachineBlockEntity.getMaxFluidLevel();
    }
}
