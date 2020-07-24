package me.alexisevelyn.randomtech.fluids.blocks;

import me.alexisevelyn.randomtech.api.blocks.fluids.BaseFluidBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.ToIntFunction;

public class CobaltFluidBlock extends BaseFluidBlock {
    public CobaltFluidBlock(FlowableFluid fluid) {
        super(fluid, FabricBlockSettings
                .copy(Blocks.LAVA)
                .lightLevel(getLightLevel()));
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!entity.isFireImmune()) {
            entity.setFireTicks(entity.getFireTicks() + 20);
            entity.damage(DamageSource.LAVA, 4.0F); // TODO: Create custom damage source - Base it off of lava, but replace with Molten Cobalt
        }
    }

    public void applyEffects(LivingEntity livingEntity) {

    }

    public void applyShader(PlayerEntity playerEntity) {

    }

    public void removeShader(PlayerEntity playerEntity) {

    }
}
