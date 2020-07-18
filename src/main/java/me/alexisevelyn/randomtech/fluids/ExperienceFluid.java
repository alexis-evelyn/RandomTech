package me.alexisevelyn.randomtech.fluids;

import me.alexisevelyn.randomtech.api.fluids.BaseFluid;
import me.alexisevelyn.randomtech.utility.RegistryHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.world.WorldView;

public abstract class ExperienceFluid extends BaseFluid {
    @Override
    @Environment(EnvType.CLIENT)
    public ParticleEffect getParticle() {
        return ParticleTypes.DRIPPING_WATER;
    }

    @Override
    public Fluid getFlowing() {
        return RegistryHelper.EXPERIENCE_FLUID_FLOWING;
    }

    @Override
    public Fluid getStill() {
        return RegistryHelper.EXPERIENCE_FLUID;
    }

    @Override
    public Item getBucketItem() {
        return RegistryHelper.EXPERIENCE_BUCKET;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return RegistryHelper.EXPERIENCE_FLUID_BLOCK.getDefaultState().with(Properties.LEVEL_15, BaseFluid.calculateLevel(state));
    }

    @Override
    protected int getFlowSpeed(WorldView world) {
        return 4;
    }

    @Override
    public int getTickRate(WorldView worldView) {
        return 15;
    }

    public static class Still extends ExperienceFluid {
        @Override
        public boolean isStill(FluidState state) {
            return true;
        }

        @Override
        public int getLevel(FluidState state) {
            return 8;
        }
    }

    public static class Flowing extends ExperienceFluid {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public boolean isStill(FluidState state) {
            return false;
        }

        @Override
        public int getLevel(FluidState state) {
            return state.get(LEVEL);
        }
    }
}