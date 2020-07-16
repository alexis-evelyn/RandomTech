package me.alexisevelyn.randomtech.items.tools.generic;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import team.reborn.energy.EnergyTier;

import java.util.Map;
import java.util.Set;

public class GenericPoweredShovel extends GenericPoweredTool {
    private static final Set<Block> EFFECTIVE_BLOCKS;
    protected static final Map<Block, BlockState> PATH_STATES;

    public GenericPoweredShovel(ToolMaterial material, int energyCapacity, EnergyTier tier, int cost, float poweredSpeed, float unpoweredSpeed, Item referenceTool, Settings settings) {
        super(material, energyCapacity, tier, cost, poweredSpeed, unpoweredSpeed, referenceTool, EFFECTIVE_BLOCKS, settings);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);

        // Don't Perform Actions From the Bottom Side of A Block
        if (context.getSide() == Direction.DOWN) {
            return super.useOnBlock(context);
        }

        PlayerEntity playerEntity = context.getPlayer();
        BlockState currentPathState = PATH_STATES.get(blockState.getBlock());
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

            return ActionResult.success(world.isClient);
        }

        return super.useOnBlock(context);
    }

    static {
        // I would just reference the ShovelItem's Effective Blocks and Path States, but Mojang has those variables marked private.
        EFFECTIVE_BLOCKS = Sets.newHashSet(Blocks.CLAY,
                Blocks.DIRT,
                Blocks.COARSE_DIRT,
                Blocks.PODZOL,
                Blocks.FARMLAND,
                Blocks.GRASS_BLOCK,
                Blocks.GRAVEL,
                Blocks.MYCELIUM,
                Blocks.SAND,
                Blocks.RED_SAND,
                Blocks.SNOW_BLOCK,
                Blocks.SNOW,
                Blocks.SOUL_SAND,
                Blocks.GRASS_PATH,
                Blocks.WHITE_CONCRETE_POWDER,
                Blocks.ORANGE_CONCRETE_POWDER,
                Blocks.MAGENTA_CONCRETE_POWDER,
                Blocks.LIGHT_BLUE_CONCRETE_POWDER,
                Blocks.YELLOW_CONCRETE_POWDER,
                Blocks.LIME_CONCRETE_POWDER,
                Blocks.PINK_CONCRETE_POWDER,
                Blocks.GRAY_CONCRETE_POWDER,
                Blocks.LIGHT_GRAY_CONCRETE_POWDER,
                Blocks.CYAN_CONCRETE_POWDER,
                Blocks.PURPLE_CONCRETE_POWDER,
                Blocks.BLUE_CONCRETE_POWDER,
                Blocks.BROWN_CONCRETE_POWDER,
                Blocks.GREEN_CONCRETE_POWDER,
                Blocks.RED_CONCRETE_POWDER,
                Blocks.BLACK_CONCRETE_POWDER,
                Blocks.SOUL_SOIL);

        PATH_STATES = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.GRASS_PATH.getDefaultState()));
    }
}