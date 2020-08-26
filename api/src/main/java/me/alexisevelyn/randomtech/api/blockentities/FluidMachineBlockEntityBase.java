package me.alexisevelyn.randomtech.api.blockentities;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.EmptyFluid;
import net.minecraft.fluid.Fluid;
import reborncore.common.fluid.FluidValue;
import reborncore.common.fluid.container.FluidInstance;
import reborncore.common.util.Tank;

/**
 * The type Fluid machine block entity base.
 */
public abstract class FluidMachineBlockEntityBase extends BasePowerAcceptorBlockEntity {
    protected Tank tank;

    /**
     * Instantiates a new Fluid machine block entity base.
     *
     * @param blockEntityType the block entity type
     */
    public FluidMachineBlockEntityBase(BlockEntityType<?> blockEntityType) {
        super(blockEntityType);
    }

    /**
     * Tick.
     */
    @SuppressWarnings("EmptyMethod")
    @Override
    public void tick() {
        super.tick();
    }

    /**
     * Gets max fluid level.
     *
     * @return the max fluid level
     */
    public FluidValue getMaxFluidLevel() {
        return tank.getCapacity();
    }

    /**
     * Gets fluid level.
     *
     * @return the fluid level
     */
    public FluidValue getFluidLevel() {
        // tank.getFluidAmount();
        return tank.getFluidInstance().getAmount();
    }

    /**
     * Gets tank.
     *
     * @return the tank
     */
    @Override
    public Tank getTank() {
        return tank;
    }

    /**
     * Gets fluid.
     *
     * @return the fluid
     */
    public FluidInstance getFluid() {
        return tank.getFluidInstance();
    }

    /**
     * Gets fluid type.
     *
     * @return the fluid type
     */
    @SuppressWarnings("unused")
    public Fluid getFluidType() {
        return tank.getFluid();
    }

    /**
     * Sets fluid amount.
     *
     * @param fluidAmount the fluid amount
     */
    @SuppressWarnings("unused")
    public void setFluidAmount(FluidValue fluidAmount) {
        tank.getFluidInstance().setAmount(fluidAmount);
    }

    /**
     * Sets fluid.
     *
     * @param fluid the fluid
     */
    @SuppressWarnings("unused")
    public void setFluid(Fluid fluid) {
        tank.setFluid(fluid);
    }

    /**
     * Is empty boolean.
     *
     * @return the boolean
     */
    public boolean isEmpty() {
        return tank.isEmpty();
    }

    /**
     * Has fluid boolean.
     *
     * @return the boolean
     */
    @SuppressWarnings("unused")
    public boolean hasFluid() {
        return !(tank.getFluid() instanceof EmptyFluid);
    }
}
