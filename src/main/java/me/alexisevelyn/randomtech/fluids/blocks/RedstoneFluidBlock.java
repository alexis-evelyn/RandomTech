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
 * The type Redstone fluid block.
 */
public class RedstoneFluidBlock extends BaseFluidBlock {
    final int swiftness_level = 3;
    final int nausea_level = 2;

    /**
     * Instantiates a new Redstone fluid block.
     *
     * @param fluid the fluid
     */
    public RedstoneFluidBlock(FlowableFluid fluid) {
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
        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 20 * 2 * swiftness_level, swiftness_level - 1));
        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20 * 2 * nausea_level, nausea_level - 1));
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
