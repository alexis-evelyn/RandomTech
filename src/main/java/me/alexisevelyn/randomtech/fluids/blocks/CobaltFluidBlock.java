package me.alexisevelyn.randomtech.fluids.blocks;

import me.alexisevelyn.randomtech.api.blocks.fluids.BaseFluidBlock;
import me.alexisevelyn.randomtech.api.utilities.CustomDamageSource;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * The type Cobalt fluid block.
 */
public class CobaltFluidBlock extends BaseFluidBlock {
    private static final DamageSource COBALT_DAMAGE_SOURCE = new CustomDamageSource("cobaltBurn");

    /**
     * Instantiates a new Cobalt fluid block.
     *
     * @param fluid the fluid
     */
    public CobaltFluidBlock(FlowableFluid fluid) {
        super(fluid, FabricBlockSettings.copyOf(Blocks.LAVA).lightLevel(getLightLevel()));
    }

    /**
     * On entity collision.
     *
     * @param state  the state
     * @param world  the world
     * @param pos    the pos
     * @param entity the entity
     */
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!entity.isFireImmune()) {
            entity.setFireTicks(entity.getFireTicks() + 20);
            entity.damage(COBALT_DAMAGE_SOURCE, 4.0F);
        }
    }

    /**
     * Apply effects.
     *
     * @param livingEntity the living entity
     */
    public void applyEffects(LivingEntity livingEntity) {
        // Intentionally Left Empty
    }

    /**
     * Apply shader.
     *
     * @param playerEntity the player entity
     */
    public void applyShader(PlayerEntity playerEntity) {
        // Intentionally Left Empty
    }

    /**
     * Remove shader.
     *
     * @param playerEntity the player entity
     */
    public void removeShader(PlayerEntity playerEntity) {
        // Intentionally Left Empty
    }
}
