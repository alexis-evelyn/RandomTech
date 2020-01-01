package me.alexisevelyn.randomtech.items.tools.generic;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import team.reborn.energy.EnergyTier;

import java.util.Set;

public class GenericPoweredSword extends GenericPoweredTool {
    private static final Set<Block> EFFECTIVE_BLOCKS;

    public GenericPoweredSword(ToolMaterial material, int energyCapacity, EnergyTier tier, int cost, float poweredSpeed, float unpoweredSpeed, Item referenceTool, Settings settings) {
        super(material, energyCapacity, tier, cost, poweredSpeed, unpoweredSpeed, referenceTool, EFFECTIVE_BLOCKS, settings);
    }

    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        if (miner.isCreative())
            return false;

        return super.canMine(state, world, pos, miner);
    }

    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        if (state.isOf(Blocks.COBWEB)) {
            return 15.0F;
        } else {
            Material material = state.getMaterial();
            return material != Material.PLANT && material != Material.REPLACEABLE_PLANT && material != Material.UNUSED_PLANT && !state.isIn(BlockTags.LEAVES) && material != Material.GOURD ? 1.0F : 1.5F;
        }
    }

    public boolean isEffectiveOn(BlockState state) {
        return EFFECTIVE_BLOCKS.contains(state.getBlock());
    }

    static {
        // There is no static reference for SwordItem as their is only one block that a sword is effective on in vanilla.
        EFFECTIVE_BLOCKS = Sets.newHashSet(Blocks.COBWEB);
    }
}