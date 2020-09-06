package me.alexisevelyn.randomtech.api.items.tools.generic;

import me.alexisevelyn.randomtech.api.utilities.ItemManagerHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShovelItem;
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
 * The type Generic powered shovel.
 */
public abstract class GenericPoweredShovel extends GenericPoweredTool {
    private static final float attackDamage = 1.5F;

    /**
     * Instantiates a new Generic powered shovel.
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
    public GenericPoweredShovel(ToolMaterial material, int energyCapacity, EnergyTier tier, int cost, float poweredSpeed, float unpoweredSpeed, Settings settings, @Nullable String dischargedTranslationKey) {
        super(material, energyCapacity, tier, cost, poweredSpeed, unpoweredSpeed, attackDamage, ShovelItem.EFFECTIVE_BLOCKS, settings, dischargedTranslationKey);
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

        // If Failed to be Usable, Then Fail Action
        if (!isUsable(context.getStack()))
            return super.useOnBlock(context);

        // Don't Perform Actions From the Bottom Side of A Block
        if (context.getSide() == Direction.DOWN)
            return super.useOnBlock(context);

        PlayerEntity playerEntity = context.getPlayer();
        BlockState currentPathState = ShovelItem.PATH_STATES.get(blockState.getBlock());
        BlockState workingBlockState = null;

        if (currentPathState != null && world.getBlockState(blockPos.up()).isAir()) {
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
            workingBlockState = currentPathState;
        } else if (blockState.getBlock() instanceof CampfireBlock && blockState.get(CampfireBlock.LIT)) {
            if (!world.isClient())
                world.syncWorldEvent(null, 1009, blockPos, 0);

            CampfireBlock.extinguish(world, blockPos, blockState);
            workingBlockState = blockState.with(CampfireBlock.LIT, false);
        }

        // If Action Set to Perform, Then Perform
        if (workingBlockState != null) {
            if (!world.isClient)
                world.setBlockState(blockPos, workingBlockState, 11);

            ItemManagerHelper.useEnergy(playerEntity, context.getStack(), cost); // To Make Sure Item Uses Durability
            return ActionResult.success(world.isClient);
        }

        return super.useOnBlock(context);
    }
}