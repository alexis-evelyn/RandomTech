package me.alexisevelyn.randomtech.blockentities;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.EmptyFluid;
import net.minecraft.fluid.Fluid;
import reborncore.common.fluid.FluidValue;
import reborncore.common.fluid.container.FluidInstance;
import reborncore.common.util.Tank;

public class FluidMachineBlockEntityBase extends BasePowerAcceptorBlockEntity {
    protected Tank tank;

    public FluidMachineBlockEntityBase(BlockEntityType<?> blockEntityType) {
        super(blockEntityType);
    }

    public FluidValue getMaxFluidLevel() {
        return tank.getCapacity();
    }

    public FluidValue getFluidLevel() {
        // tank.getFluidAmount();
        return tank.getFluidInstance().getAmount();
    }

    @Override
    public Tank getTank() {
        return tank;
    }

    public FluidInstance getFluid() {
        return tank.getFluidInstance();
    }

    public Fluid getFluidType() {
        return tank.getFluid();
    }

    public void setFluidAmount(FluidValue fluidAmount) {
        tank.getFluidInstance().setAmount(fluidAmount);
    }

    public void setFluid(Fluid fluid) {
        tank.setFluid(fluid);
    }

    public boolean isEmpty() {
        return tank.isEmpty();
    }

    public boolean hasFluid() {
        return !(tank.getFluid() instanceof EmptyFluid);
    }
}
