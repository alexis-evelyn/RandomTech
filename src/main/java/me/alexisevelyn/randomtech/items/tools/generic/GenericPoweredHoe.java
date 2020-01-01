package me.alexisevelyn.randomtech.items.tools.generic;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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

public class GenericPoweredHoe extends GenericPoweredTool {
    private static final Set<Block> EFFECTIVE_BLOCKS;
    protected static final Map<Block, BlockState> TILLED_BLOCKS;

    public GenericPoweredHoe(ToolMaterial material, int energyCapacity, EnergyTier tier, int cost, float poweredSpeed, float unpoweredSpeed, Item referenceTool, Settings settings) {
        super(material, energyCapacity, tier, cost, poweredSpeed, unpoweredSpeed, referenceTool, EFFECTIVE_BLOCKS, settings);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();

        if (context.getSide() != Direction.DOWN && world.getBlockState(blockPos.up()).isAir()) {
            BlockState blockState = TILLED_BLOCKS.get(world.getBlockState(blockPos).getBlock());

            if (blockState != null) {
                PlayerEntity playerEntity = context.getPlayer();
                world.playSound(playerEntity, blockPos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

                if (!world.isClient) {
                    world.setBlockState(blockPos, blockState, 11);
                }

                return ActionResult.success(world.isClient);
            }
        }

        return super.useOnBlock(context);
    }

    static {
        // I would just reference the HoeItem's Effective Blocks and Tilled Blocks, but Mojang has those variables marked private.
        EFFECTIVE_BLOCKS = ImmutableSet.of(Blocks.NETHER_WART_BLOCK,
                Blocks.WARPED_WART_BLOCK,
                Blocks.HAY_BLOCK,
                Blocks.DRIED_KELP_BLOCK,
                Blocks.TARGET,
                Blocks.SHROOMLIGHT,
                Blocks.SPONGE,
                Blocks.WET_SPONGE,
                Blocks.JUNGLE_LEAVES,
                Blocks.OAK_LEAVES,
                Blocks.SPRUCE_LEAVES,
                Blocks.DARK_OAK_LEAVES,
                Blocks.ACACIA_LEAVES,
                Blocks.BIRCH_LEAVES);

        TILLED_BLOCKS = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.FARMLAND.getDefaultState(),
                Blocks.GRASS_PATH, Blocks.FARMLAND.getDefaultState(),
                Blocks.DIRT, Blocks.FARMLAND.getDefaultState(),
                Blocks.COARSE_DIRT, Blocks.DIRT.getDefaultState()));
    }
}