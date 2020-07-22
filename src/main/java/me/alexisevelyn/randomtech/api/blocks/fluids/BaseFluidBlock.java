package me.alexisevelyn.randomtech.api.blocks.fluids;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.text.LiteralText;
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

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        //super.onEntityCollision(state, world, pos, entity);

        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;

            applyEffects(livingEntity);
        }

        if (entity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) entity;

            if (isEyeInFluid(playerEntity, pos))
                applyShader(playerEntity);
            else
                removeShader(playerEntity);
        }
    }

    public void applyEffects(LivingEntity livingEntity) {

    }

    public void applyShader(PlayerEntity playerEntity) {

    }

    public void removeShader(PlayerEntity playerEntity) {

    }

    public boolean isEyeInFluid(PlayerEntity playerEntity, BlockPos blockPos) {
        // This activates the same as water would. Can be used to determine if needing to apply shaders.
        return (int) playerEntity.getEyeY() == blockPos.getY() && playerEntity.getBlockPos().getZ() == blockPos.getZ() && playerEntity.getBlockPos().getX() == blockPos.getX();
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
