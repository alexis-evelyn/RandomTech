package me.alexisevelyn.randomtech.api.items.tools.generic;

import me.alexisevelyn.randomtech.api.utilities.ItemManager;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.EnergyTier;

/**
 * The type Generic powered hoe.
 */
public abstract class GenericPoweredHoe extends GenericPoweredTool {
    private static final float attackDamage = -4.0F;

    /**
     * Instantiates a new Generic powered hoe.
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
    public GenericPoweredHoe(ToolMaterial material, int energyCapacity, EnergyTier tier, int cost, float poweredSpeed, float unpoweredSpeed, Settings settings, @Nullable String dischargedTranslationKey) {
        super(material, energyCapacity, tier, cost, poweredSpeed, unpoweredSpeed, attackDamage, HoeItem.EFFECTIVE_BLOCKS, settings, dischargedTranslationKey);
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

        // If Failed to be Usable, Then Fail Action
        if (!isUsable(context.getStack()))
            return ActionResult.FAIL;

        if (context.getSide() != Direction.DOWN && world.getBlockState(blockPos.up()).isAir()) {
            BlockState blockState = HoeItem.TILLED_BLOCKS.get(world.getBlockState(blockPos).getBlock());

            if (blockState != null) {
                PlayerEntity playerEntity = context.getPlayer();
                world.playSound(playerEntity, blockPos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

                if (!world.isClient) {
                    world.setBlockState(blockPos, blockState, 11);
                }

                ItemManager.useEnergy(playerEntity, context.getStack(), cost); // To Make Sure Item Uses Durability
                return ActionResult.success(world.isClient);
            }
        }

        return super.useOnBlock(context);
    }
}