package me.alexisevelyn.randomtech.items.tools.generic;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import team.reborn.energy.EnergyTier;

import java.util.Set;

public class GenericPoweredAxe extends GenericPoweredTool {
    private static final Set<Block> EFFECTIVE_BLOCKS;
    protected static final ImmutableMap<Block, Block> STRIPPED_BLOCKS;

    public GenericPoweredAxe(ToolMaterial material, int energyCapacity, EnergyTier tier, int cost, float poweredSpeed, float unpoweredSpeed, Item referenceTool, Settings settings) {
        super(material, energyCapacity, tier, cost, poweredSpeed, unpoweredSpeed, referenceTool, EFFECTIVE_BLOCKS, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        Block block = STRIPPED_BLOCKS.get(blockState.getBlock());

        if (block != null) {
            PlayerEntity playerEntity = context.getPlayer();
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);

            if (!world.isClient) {
                world.setBlockState(blockPos, block.getDefaultState().with(PillarBlock.AXIS, blockState.get(PillarBlock.AXIS)), 11);
            }

            return ActionResult.success(world.isClient);
        }

        return super.useOnBlock(context);
    }

    static {
        // I would just reference the AxeItem's Effective Blocks and Stripped Blocks, but Mojang has those variables marked private.
        EFFECTIVE_BLOCKS = Sets.newHashSet(Blocks.LADDER,
                Blocks.SCAFFOLDING,
                Blocks.OAK_BUTTON,
                Blocks.SPRUCE_BUTTON,
                Blocks.BIRCH_BUTTON,
                Blocks.JUNGLE_BUTTON,
                Blocks.DARK_OAK_BUTTON,
                Blocks.ACACIA_BUTTON,
                Blocks.CRIMSON_BUTTON,
                Blocks.WARPED_BUTTON);

        STRIPPED_BLOCKS = new ImmutableMap.Builder<Block, Block>()
                .put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD)
                .put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG)
                .put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD)
                .put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG)
                .put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD)
                .put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG)
                .put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD)
                .put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG)
                .put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD)
                .put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG)
                .put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD)
                .put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG)
                .put(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM)
                .put(Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE)
                .put(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM)
                .put(Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE).build();
    }
}