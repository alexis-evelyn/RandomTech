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
 * The type Magic fluid.
 */
// TODO: Magic Based on the Magical Properties of Lapis (A Purified Form, An Extract If You Want)
public abstract class MagicFluid extends BaseFluid {
    /**
     * Gets particle.
     *
     * @return the particle
     */
    @Override
    @Environment(EnvType.CLIENT)
    public ParticleEffect getParticle() {
        return ParticleTypes.DRIPPING_OBSIDIAN_TEAR;
    }

    /**
     * Gets flowing.
     *
     * @return the flowing
     */
    @Override
    public Fluid getFlowing() {
        return RegistryHelper.MAGIC_FLUID_FLOWING;
    }

    /**
     * Gets still.
     *
     * @return the still
     */
    @Override
    public Fluid getStill() {
        return RegistryHelper.MAGIC_FLUID;
    }

    /**
     * Gets bucket item.
     *
     * @return the bucket item
     */
    @Override
    public Item getBucketItem() {
        return RegistryHelper.MAGIC_BUCKET;
    }

    /**
     * To block state block state.
     *
     * @param state the state
     * @return the block state
     */
    @Override
    protected BlockState toBlockState(FluidState state) {
        return RegistryHelper.MAGIC_FLUID_BLOCK.getDefaultState().with(Properties.LEVEL_15, BaseFluid.calculateLevel(state));
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
     * Gets tick rate.
     *
     * @param worldView the world view
     * @return the tick rate
     */
    @Override
    public int getTickRate(WorldView worldView) {
        return 25;
    }

    /**
     * The type Still.
     */
    public static class Still extends MagicFluid {
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
    public static class Flowing extends MagicFluid {
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