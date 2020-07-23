package me.alexisevelyn.randomtech.items.tools.powered;

import me.alexisevelyn.randomtech.api.items.tools.generic.GenericPoweredPickaxe;
import me.alexisevelyn.randomtech.api.utilities.MiningManager;
import me.alexisevelyn.randomtech.toolmaterials.poweredtools.PoweredToolMaterial;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
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

        if (playerEntity != null) {
            boolean canMine = MiningManager.canMine(context);

            MutableText canMineVariable = new TranslatableText(canMine ? "text.randomtech.true" : "text.randomtech.false");

            // Color red or green depending on if can mine. Green is true, red is false.
            canMineVariable = canMine ? canMineVariable.formatted(Formatting.DARK_GREEN, Formatting.BOLD) : canMineVariable.formatted(Formatting.DARK_RED, Formatting.BOLD);

            Text canMineMessage = new TranslatableText("text.randomtech.can_mine", canMineVariable)
                    .formatted(Formatting.GOLD, Formatting.BOLD);

            playerEntity.sendMessage(canMineMessage, true);
        }

        return super.useOnBlock(context);
    }
}
