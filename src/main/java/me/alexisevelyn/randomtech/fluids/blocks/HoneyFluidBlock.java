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
 * The type Honey fluid block.
 */
public class HoneyFluidBlock extends BaseFluidBlock {
    protected final int slowness_level = 3;
    protected final int slow_falling_level = 2;

    /**
     * Instantiates a new Honey fluid block.
     *
     * @param fluid the fluid
     */
    public HoneyFluidBlock(FlowableFluid fluid) {
        super(fluid, FabricBlockSettings
                .copy(Blocks.LAVA)
                .lightLevel(getZeroLightLevel()));
    }

    /**
     * Apply effects.
     *
     * @param livingEntity the living entity
     */
    public void applyEffects(LivingEntity livingEntity) {
        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 2 * slowness_level, slowness_level - 1));
        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 20 * 2 * slow_falling_level, slow_falling_level - 1));
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
