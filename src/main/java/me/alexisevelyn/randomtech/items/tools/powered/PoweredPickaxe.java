package me.alexisevelyn.randomtech.items.tools.powered;

import me.alexisevelyn.randomtech.api.items.tools.generic.GenericPoweredPickaxe;
import me.alexisevelyn.randomtech.api.utilities.MiningManager;
import me.alexisevelyn.randomtech.toolmaterials.poweredtools.PoweredToolMaterial;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import team.reborn.energy.EnergyTier;

/**
 * The type Powered pickaxe.
 */
public class PoweredPickaxe extends GenericPoweredPickaxe {
    private static final String dischargedTranslationKey = "item.randomtech.unpowered_pickaxe";

    /**
     * Instantiates a new Powered pickaxe.
     *
     * @param settings the settings
     */
    public PoweredPickaxe(Settings settings) {
        super(new PoweredToolMaterial(), 2561, EnergyTier.HIGH, 1, 20, -2.8F, settings, dischargedTranslationKey);
    }

    /**
     * Post mine boolean.
     *
     * @param stack the stack
     * @param world the world
     * @param state the state
     * @param pos   the pos
     * @param miner the miner
     * @return the boolean
     */
    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        // TODO: Decide what to do with mining tool/player when mining unbreakable block.
//        if (state.getBlock().is(Blocks.BEDROCK) || state.getBlock().is(Blocks.END_PORTAL_FRAME)) {
//            setEnergy(stack, 0);
//            return true;
//        }

        return super.postMine(stack, world, state, pos, miner);
    }

    /**
     * Can break unbreakable block boolean.
     *
     * @param state  the state
     * @param player the player
     * @param world  the world
     * @param pos    the pos
     * @return the boolean
     */
    @Override
    public boolean canBreakUnbreakableBlock(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        // TODO: Add Upgrades to Pickaxe and determine if upgrade is installed to allow mining these blocks
        return state.getBlock().is(Blocks.BEDROCK) || state.getBlock().is(Blocks.END_PORTAL_FRAME);
    }

    /**
     * Gets unbreakable block difficulty multiplier.
     *
     * @param state  the state
     * @param player the player
     * @param world  the world
     * @param pos    the pos
     * @return the unbreakable block difficulty multiplier
     */
    @Override
    public float getUnbreakableBlockDifficultyMultiplier(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        if (state.getBlock().is(Blocks.BEDROCK))
            return 15.0F;

        return 1.0F;
    }

    /**
     * Is fireproof boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean isFireproof() {
        return true;
    }

    /**
     * Use on block action result.
     *
     * @param context the context
     * @return the action result
     */
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
