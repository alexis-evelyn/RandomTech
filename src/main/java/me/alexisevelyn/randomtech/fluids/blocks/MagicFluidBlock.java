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

public class MagicFluidBlock extends BaseFluidBlock {
    int levitation_level = 1;
    int night_vision_level = 1;

    protected MagicFluidBlock(FlowableFluid fluid) {
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
    public void applyEffects(LivingEntity livingEntity) {
        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 20 * 2 * levitation_level, levitation_level - 1));

        // When repeatedly applying Night Vision, the sky flashes rapidly. To prevent potential seizures, we will only apply Night Vision once it runs out.
        if (!livingEntity.hasStatusEffect(StatusEffects.NIGHT_VISION))
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 20 * 2 * night_vision_level, night_vision_level - 1));
    }

    @Override
    public void applyShader(PlayerEntity playerEntity) {

    }

    @Override
    public void removeShader(PlayerEntity playerEntity) {

    }
}
