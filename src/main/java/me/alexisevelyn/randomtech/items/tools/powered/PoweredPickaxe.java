package me.alexisevelyn.randomtech.items.tools.powered;

import me.alexisevelyn.randomtech.api.items.tools.generic.GenericPoweredPickaxe;
import me.alexisevelyn.randomtech.toolmaterials.poweredtools.PoweredToolMaterial;
import me.alexisevelyn.randomtech.utility.RegistryHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import team.reborn.energy.EnergyTier;

public class PoweredPickaxe extends GenericPoweredPickaxe {
    private static final String dischargedTranslationKey = "item.randomtech.unpowered_pickaxe";

    public PoweredPickaxe(Settings settings) {
        super(new PoweredToolMaterial(), 2561, EnergyTier.HIGH, 1, 20, -2.8F, settings, dischargedTranslationKey);
    }

    @Override
    public boolean isFireproof() {
        return true;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity playerEntity = context.getPlayer();
        ItemStack itemStack = context.getStack();

        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);

        if (playerEntity != null) {
            playerEntity.sendMessage(new LiteralText("Effective Tool: " + playerEntity.isUsingEffectiveTool(blockState)), true);
        }

        return super.useOnBlock(context);
    }

    @Override
    public boolean isEffectiveOn(BlockState state) {
        int i = this.getMaterial().getMiningLevel();

        // TODO: Figure out how to generalize Mining Level
        if (state.isOf(RegistryHelper.COBALT_BLOCK) || state.isOf(RegistryHelper.COBALT_ORE))
            return true;

        return super.isEffectiveOn(state);
    }
}
