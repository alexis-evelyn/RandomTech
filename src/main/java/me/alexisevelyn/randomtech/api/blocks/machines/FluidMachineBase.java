package me.alexisevelyn.randomtech.api.blocks.machines;

import me.alexisevelyn.randomtech.api.blockentities.FluidMachineBlockEntityBase;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import reborncore.common.fluid.FluidValue;

public abstract class FluidMachineBase extends PowerAcceptorBlock {
    public FluidMachineBase(AbstractBlock.Settings settings) {
        super(settings);
    }

    @SuppressWarnings("unused")
    public FluidMachineBase(AbstractBlock.Settings settings, boolean customStates) {
        super(settings, customStates);
    }

    @Nullable
    @SuppressWarnings("unused")
    public FluidValue getFluidLevel(BlockState state, World world, BlockPos pos) {
        FluidMachineBlockEntityBase fluidMachineBlockEntity = (FluidMachineBlockEntityBase) world.getBlockEntity(pos);

        if (fluidMachineBlockEntity == null) {
            return null;
        }

        return fluidMachineBlockEntity.getFluidLevel();
    }

    @Nullable
    @SuppressWarnings("unused")
    public FluidValue getMaxFluidLevel(BlockState state, World world, BlockPos pos) {
        FluidMachineBlockEntityBase fluidMachineBlockEntity = (FluidMachineBlockEntityBase) world.getBlockEntity(pos);

        if (fluidMachineBlockEntity == null) {
            return null;
        }

        return fluidMachineBlockEntity.getMaxFluidLevel();
    }
}
