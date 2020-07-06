package me.alexisevelyn.randomtech.fluids.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ExperienceFluidBlock extends BaseFluidBlock {
    int luck_level = 3;
    int glow_level = 1;

    protected ExperienceFluidBlock(FlowableFluid fluid, Settings settings) {
        super(fluid, settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);

        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;

            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.LUCK, 20 * 2 * luck_level, luck_level - 1));
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 20 * 2 * glow_level, glow_level - 1));
        }
    }
}
