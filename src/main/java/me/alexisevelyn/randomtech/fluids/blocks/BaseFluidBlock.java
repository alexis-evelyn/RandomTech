package me.alexisevelyn.randomtech.fluids.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BaseFluidBlock extends FluidBlock {
    protected BaseFluidBlock(FlowableFluid fluid, Settings settings) {
        super(fluid, settings);
    }

    protected BaseFluidBlock(FlowableFluid fluid) {
        super(fluid, FabricBlockSettings.copy(Blocks.WATER));
    }

    // TODO: Add proper entity push physics
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        //super.onEntityCollision(state, world, pos, entity);

        // Test Movement for Pushing Entities
        // entity.addVelocity(0, 10, 0);
    }
}
