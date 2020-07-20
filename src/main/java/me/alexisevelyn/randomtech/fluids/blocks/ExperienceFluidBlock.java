package me.alexisevelyn.randomtech.fluids.blocks;

import me.alexisevelyn.randomtech.api.blocks.fluids.BaseFluidBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.ToIntFunction;

public class ExperienceFluidBlock extends BaseFluidBlock {
    int luck_level = 3;
    int glow_level = 1;

    protected ExperienceFluidBlock(FlowableFluid fluid) {
        super(fluid, FabricBlockSettings
                .copy(Blocks.WATER)
                .lightLevel(getLightLevel()));
    }

    public static ToIntFunction<BlockState> getLightLevel() {
        return (state) -> {
            return getLightLevel(state.get(LEVEL));
        };
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);

        // TODO: Set Shader if Player Head in Liquid

        // TODO: Get level fluid is at and determine if head is under liquid
        // TODO: Also properly lower entity air. The player starts with 300 seconds of Air
        // entity.setAir(entity.getAir() - 1);

        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;

            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.LUCK, 20 * 2 * luck_level, luck_level - 1));
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 20 * 2 * glow_level, glow_level - 1));
        }
    }
}
