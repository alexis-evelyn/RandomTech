package me.alexisevelyn.randomtech.fluids.blocks;

import me.alexisevelyn.randomtech.api.blocks.fluids.BaseFluidBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.ToIntFunction;

public class ExperienceFluidBlock extends BaseFluidBlock {
    int luck_level = 3;
    int glow_level = 1;

    public ExperienceFluidBlock(FlowableFluid fluid) {
        super(fluid, FabricBlockSettings
                .copy(Blocks.WATER)
                .lightLevel(getLightLevel()));
    }

    public void applyEffects(LivingEntity livingEntity) {
        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.LUCK, 20 * 2 * luck_level, luck_level - 1));
        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 20 * 2 * glow_level, glow_level - 1));
    }

    public void applyShader(PlayerEntity playerEntity) {

    }

    public void removeShader(PlayerEntity playerEntity) {

    }
}
