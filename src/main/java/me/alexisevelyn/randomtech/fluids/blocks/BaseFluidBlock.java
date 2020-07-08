package me.alexisevelyn.randomtech.fluids.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

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

        // TODO: Get level fluid is at and determine if head is under liquid
        // TODO: Also properly lower entity air. The player starts with 300 seconds of Air
        entity.setAir(entity.getAir() - 1);

        // Test Movement for Pushing Entities
        // entity.addVelocity(0, 10, 0);
    }

    public static int getLightLevel(@Nullable Integer currentFluidLevel) {
        int minLightLevel = 0;
        int maxLightLevel = 15;

        int maxFluidLevel = 8;

        // For When Blockstate is Missing Level
        if (currentFluidLevel == null)
            currentFluidLevel = 8;

        int currentLightLevel = ((currentFluidLevel * maxLightLevel) / maxFluidLevel) + minLightLevel;

        // Note: I have to invert the light level as for some reason, Minecraft has fluids produce the
        // exact inverse of the light level than what is expected when basing it off of fluid levels.
        // This is not a problem with the formula above. I'm not sure why it does that though.
        return (-1 * currentLightLevel) + 15;
    }
}
