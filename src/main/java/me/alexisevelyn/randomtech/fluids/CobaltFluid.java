package me.alexisevelyn.randomtech.fluids;

import me.alexisevelyn.randomtech.api.fluids.BaseFluid;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
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

/**
 * The type Cobalt fluid.
 */
public abstract class CobaltFluid extends BaseFluid {
    /**
     * Gets particle.
     *
     * @return the particle
     */
    @Override
    @Environment(EnvType.CLIENT)
    public ParticleEffect getParticle() {
        return ParticleTypes.DRIPPING_WATER;
    }

    /**
     * Gets flowing.
     *
     * @return the flowing
     */
    @Override
    public Fluid getFlowing() {
        return RegistryHelper.COBALT_FLUID_FLOWING;
    }

    /**
     * Gets still.
     *
     * @return the still
     */
    @Override
    public Fluid getStill() {
        return RegistryHelper.COBALT_FLUID;
    }

    /**
     * Gets bucket item.
     *
     * @return the bucket item
     */
    @Override
    public Item getBucketItem() {
        return RegistryHelper.COBALT_BUCKET;
    }

    /**
     * To block state block state.
     *
     * @param state the state
     * @return the block state
     */
    @Override
    protected BlockState toBlockState(FluidState state) {
        return RegistryHelper.COBALT_FLUID_BLOCK.getDefaultState().with(Properties.LEVEL_15, BaseFluid.calculateLevel(state));
    }

    /**
     * Gets flow speed.
     *
     * @param world the world
     * @return the flow speed
     */
    @Override
    protected int getFlowSpeed(WorldView world) {
        return 4;
    }

    /**
     * Gets level decrease per block.
     *
     * @param world the world
     * @return the level decrease per block
     */
    @Override
    public int getLevelDecreasePerBlock(WorldView world) {
        return world.getDimension().isUltrawarm() ? 1 : 2;
    }

    /**
     * Gets tick rate.
     *
     * @param worldView the world view
     * @return the tick rate
     */
    @Override
    public int getTickRate(WorldView worldView) {
        return worldView.getDimension().isUltrawarm() ? 20 : 40;
    }

    /**
     * The type Still.
     */
    public static class Still extends CobaltFluid {
        /**
         * Is still boolean.
         *
         * @param state the state
         * @return the boolean
         */
        @Override
        public boolean isStill(FluidState state) {
            return true;
        }

        /**
         * Gets level.
         *
         * @param state the state
         * @return the level
         */
        @Override
        public int getLevel(FluidState state) {
            return 8;
        }
    }

    /**
     * The type Flowing.
     */
    public static class Flowing extends CobaltFluid {
        /**
         * Append properties.
         *
         * @param builder the builder
         */
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        /**
         * Is still boolean.
         *
         * @param state the state
         * @return the boolean
         */
        @Override
        public boolean isStill(FluidState state) {
            return false;
        }

        /**
         * Gets level.
         *
         * @param state the state
         * @return the level
         */
        @Override
        public int getLevel(FluidState state) {
            return state.get(LEVEL);
        }
    }
}