package me.alexisevelyn.randomtech.fluids.blocks;

import me.alexisevelyn.randomtech.api.blocks.fluids.BaseFluidBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;

/**
 * The type Magic fluid block.
 */
public class MagicFluidBlock extends BaseFluidBlock {
    final int levitation_level = 1;
    final int night_vision_level = 1;

    /**
     * Instantiates a new Magic fluid block.
     *
     * @param fluid the fluid
     */
    public MagicFluidBlock(FlowableFluid fluid) {
        super(fluid, FabricBlockSettings
                .copy(Blocks.WATER)
                .lightLevel(getLightLevel()));
    }

    /**
     * Apply effects.
     *
     * @param livingEntity the living entity
     */
    public void applyEffects(LivingEntity livingEntity) {
        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 20 * 2 * levitation_level, levitation_level - 1));

        // When repeatedly applying Night Vision, the sky flashes rapidly. To prevent potential seizures, we will only apply Night Vision once it runs out.
        if (!livingEntity.hasStatusEffect(StatusEffects.NIGHT_VISION))
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 20 * 2 * night_vision_level, night_vision_level - 1));
    }

    /**
     * Apply shader.
     *
     * @param playerEntity the player entity
     */
    @SuppressWarnings("PMD.UncommentedEmptyMethodBody")
    public void applyShader(PlayerEntity playerEntity) {
        // Intentionally Left Empty
    }

    /**
     * Remove shader.
     *
     * @param playerEntity the player entity
     */
    @SuppressWarnings("PMD.UncommentedEmptyMethodBody")
    public void removeShader(PlayerEntity playerEntity) {
        // Intentionally Left Empty
    }
}
