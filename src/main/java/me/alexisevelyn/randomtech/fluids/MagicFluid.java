package me.alexisevelyn.randomtech.fluids;

import me.alexisevelyn.randomtech.Main;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.world.WorldView;

// TODO: Magic Based on the Magical Properties of Lapis (A Purified Form, An Extract If You Want)
public abstract class MagicFluid extends BaseFluid {
    @Override
    public Fluid getFlowing() {
        return Main.MAGIC_FLUID_FLOWING;
    }

    @Override
    public Fluid getStill() {
        return Main.MAGIC_FLUID;
    }

    @Override
    public Item getBucketItem() {
        return Main.MAGIC_BUCKET;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return Main.MAGIC_FLUID_BLOCK.getDefaultState().with(Properties.LEVEL_15, BaseFluid.calculateLevel(state));
    }

    @Override
    protected int getFlowSpeed(WorldView world) {
        return 4;
    }

    public static class Still extends MagicFluid {
        @Override
        public boolean isStill(FluidState state) {
            return true;
        }

        @Override
        public int getLevel(FluidState state) {
            return 8;
        }
    }

    public static class Flowing extends MagicFluid {
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