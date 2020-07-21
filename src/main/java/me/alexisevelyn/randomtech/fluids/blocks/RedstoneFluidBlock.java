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

public class RedstoneFluidBlock extends BaseFluidBlock {
    int swiftness_level = 3;
    int nausea_level = 2;

    protected RedstoneFluidBlock(FlowableFluid fluid) {
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
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);

        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;

            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 20 * 2 * swiftness_level, swiftness_level - 1));
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20 * 2 * nausea_level, nausea_level - 1));
        }

        if (entity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) entity;

            if (isEyeInFluid(playerEntity, pos))
                return; // TODO: Apply shader to player
        }
    }
}
