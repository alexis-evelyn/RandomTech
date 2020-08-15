package me.alexisevelyn.randomtech.fluids.blocks;

import me.alexisevelyn.randomtech.api.blocks.fluids.BaseFluidBlock;
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
    /**
     * Instantiates a new Cobalt fluid block.
     *
     * @param fluid the fluid
     */
    public CobaltFluidBlock(FlowableFluid fluid) {
        super(fluid, FabricBlockSettings
                .copy(Blocks.LAVA)
                .lightLevel(getLightLevel()));
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
            entity.damage(DamageSource.LAVA, 4.0F); // TODO: Create custom damage source - Base it off of lava, but replace with Molten Cobalt
        }
    }

    /**
     * Apply effects.
     *
     * @param livingEntity the living entity
     */
    public void applyEffects(LivingEntity livingEntity) {

    }

    /**
     * Apply shader.
     *
     * @param playerEntity the player entity
     */
    public void applyShader(PlayerEntity playerEntity) {

    }

    /**
     * Remove shader.
     *
     * @param playerEntity the player entity
     */
    public void removeShader(PlayerEntity playerEntity) {

    }
}
