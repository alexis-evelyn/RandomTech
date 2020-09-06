package me.alexisevelyn.randomtech.api.items.tools.generic;

import me.alexisevelyn.randomtech.api.utilities.ItemManagerHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.EnergyTier;

/**
 * The type Generic powered axe.
 */
public abstract class GenericPoweredAxe extends GenericPoweredTool {
    private static final float attackDamage = 5.0F;

    /**
     * Instantiates a new Generic powered axe.
     *
     * @param material                 the material
     * @param energyCapacity           the energy capacity
     * @param tier                     the tier
     * @param cost                     the cost
     * @param poweredSpeed             the powered speed
     * @param unpoweredSpeed           the unpowered speed
     * @param settings                 the settings
     * @param dischargedTranslationKey the discharged translation key
     */
    public GenericPoweredAxe(ToolMaterial material, int energyCapacity, EnergyTier tier, int cost, float poweredSpeed, float unpoweredSpeed, Settings settings, @Nullable String dischargedTranslationKey) {
        super(material, energyCapacity, tier, cost, poweredSpeed, unpoweredSpeed, attackDamage, AxeItem.EFFECTIVE_BLOCKS, settings, dischargedTranslationKey);
    }

    /**
     * Gets mining speed multiplier.
     *
     * @param stack the stack
     * @param state the state
     * @return the mining speed multiplier
     */
    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        Material material = state.getMaterial();

        // I know field_23139 as NATURAL_EFFECTIVE_MATERIALS
        return AxeItem.field_23139.contains(material) ? this.miningSpeed : super.getMiningSpeedMultiplier(stack, state);
    }

    /**
     * Use on block action result.
     *
     * @param context the context
     * @return the action result
     */
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        Block block = AxeItem.STRIPPED_BLOCKS.get(blockState.getBlock());

        if (isUsable(context.getStack()) && block != null) {
            PlayerEntity playerEntity = context.getPlayer();
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);

            if (!world.isClient) {
                world.setBlockState(blockPos, block.getDefaultState().with(PillarBlock.AXIS, blockState.get(PillarBlock.AXIS)), 11);
            }

            ItemManagerHelper.useEnergy(playerEntity, context.getStack(), cost); // To Make Sure Item Uses Durability
            return ActionResult.success(world.isClient);
        }

        return super.useOnBlock(context);
    }

    /**
     * Is effective on boolean.
     *
     * @param state the state
     * @return the boolean
     */
    @Override
    public boolean isEffectiveOn(BlockState state) {
        if (super.isEffectiveOn(state))
            return true;

        // If not one of the explicitly stated effective blocks, then check the Material Type
        return AxeItem.field_23139.contains(state.getMaterial());
    }
}