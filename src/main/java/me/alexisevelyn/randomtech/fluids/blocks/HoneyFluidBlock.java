package me.alexisevelyn.randomtech.fluids.blocks;

import me.alexisevelyn.randomtech.api.blocks.fluids.BaseFluidBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;

public class HoneyFluidBlock extends BaseFluidBlock {
    final int slowness_level = 3;
    final int slow_falling_level = 2;

    public HoneyFluidBlock(FlowableFluid fluid) {
        super(fluid, FabricBlockSettings
                .copy(Blocks.LAVA)
                .lightLevel(getZeroLightLevel()));
    }

    public void applyEffects(LivingEntity livingEntity) {
        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 2 * slowness_level, slowness_level - 1));
        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 20 * 2 * slow_falling_level, slow_falling_level - 1));
    }

    public void applyShader(PlayerEntity playerEntity) {

    }

    public void removeShader(PlayerEntity playerEntity) {

    }
}
