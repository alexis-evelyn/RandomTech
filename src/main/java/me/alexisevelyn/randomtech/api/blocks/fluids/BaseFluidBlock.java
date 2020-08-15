package me.alexisevelyn.randomtech.api.blocks.fluids;

import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

/**
 * The type Base fluid block.
 */
public abstract class BaseFluidBlock extends FluidBlock {
    /**
     * Instantiates a new Base fluid block.
     *
     * @param fluid    the fluid
     * @param settings the settings
     */
    public BaseFluidBlock(FlowableFluid fluid, Settings settings) {
        super(fluid, settings);
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
        //super.onEntityCollision(state, world, pos, entity);

        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;

            applyEffects(livingEntity);
        }

        if (entity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) entity;

            if (isEyeInFluid(playerEntity, pos))
                applyShader(playerEntity);
            else
                removeShader(playerEntity);
        }
    }

    /**
     * Apply effects.
     *
     * @param livingEntity the living entity
     */
// These exist solely to override from other fluids.
    // I may eventually turn this into an interface
    protected abstract void applyEffects(@SuppressWarnings("unused") LivingEntity livingEntity);

    /**
     * Apply shader.
     *
     * @param playerEntity the player entity
     */
    @SuppressWarnings("EmptyMethod") protected abstract void applyShader(@SuppressWarnings("unused") PlayerEntity playerEntity);

    /**
     * Remove shader.
     *
     * @param playerEntity the player entity
     */
    @SuppressWarnings("EmptyMethod") protected abstract void removeShader(@SuppressWarnings("unused") PlayerEntity playerEntity);

    /**
     * Is eye in fluid boolean.
     *
     * @param playerEntity the player entity
     * @param blockPos     the block pos
     * @return the boolean
     */
    public boolean isEyeInFluid(PlayerEntity playerEntity, BlockPos blockPos) {
        // This activates the same as water would. Can be used to determine if needing to apply shaders.
        return (int) playerEntity.getEyeY() == blockPos.getY() && playerEntity.getBlockPos().getZ() == blockPos.getZ() && playerEntity.getBlockPos().getX() == blockPos.getX();
    }

    /**
     * Gets light level.
     *
     * @param currentFluidLevel the current fluid level
     * @return the light level
     */
    public static int getLightLevel(@Nullable Integer currentFluidLevel) {
        int minLightLevel = 0;
        int maxLightLevel = 15;

        int maxFluidLevel = 8;

        // For When Blockstate is Missing Level
        if (currentFluidLevel == null)
            currentFluidLevel = 8;

        int currentLightLevel = ((currentFluidLevel * maxLightLevel) / maxFluidLevel) + minLightLevel;

        // Note: I have to invert the light level as for some reason, Minecraft has fluids produce the
        // exact inverse of the light level than what is expected when basing it off of fluid levels.
        // This is not a problem with the formula above. I'm not sure why it does that though.
        return (-1 * currentLightLevel) + 15;
    }

    /**
     * Gets light level.
     *
     * @return the light level
     */
    public static ToIntFunction<BlockState> getLightLevel() {
        return (state) -> getLightLevel(state.get(LEVEL));
    }

    /**
     * Gets zero light level.
     *
     * @return the zero light level
     */
    public static ToIntFunction<BlockState> getZeroLightLevel() {
        return (state) -> 0;
    }
}
