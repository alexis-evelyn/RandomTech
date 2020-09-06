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
 * The type Experience fluid block.
 */
public class ExperienceFluidBlock extends BaseFluidBlock {
    protected final int luck_level = 3;
    protected final int glow_level = 1;

    /**
     * Instantiates a new Experience fluid block.
     *
     * @param fluid the fluid
     */
    public ExperienceFluidBlock(FlowableFluid fluid) {
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
        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.LUCK, 20 * 2 * luck_level, luck_level - 1));
        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 20 * 2 * glow_level, glow_level - 1));
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
