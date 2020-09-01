package me.alexisevelyn.randomtech.api.items.tools.generic;

import com.google.common.collect.Sets;
import org.jetbrains.annotations.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import team.reborn.energy.EnergyTier;

import java.util.Set;

/**
 * The type Generic powered sword.
 */
public abstract class GenericPoweredSword extends GenericPoweredTool {
    private static final Set<Block> EFFECTIVE_BLOCKS;
    private static final float attackDamage = 3;

    /**
     * Instantiates a new Generic powered sword.
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
    public GenericPoweredSword(ToolMaterial material, int energyCapacity, EnergyTier tier, int cost, float poweredSpeed, float unpoweredSpeed, Settings settings, @Nullable String dischargedTranslationKey) {
        super(material, energyCapacity, tier, cost, poweredSpeed, unpoweredSpeed, attackDamage, EFFECTIVE_BLOCKS, settings, dischargedTranslationKey);
    }

    /**
     * Can mine boolean.
     *
     * @param state the state
     * @param world the world
     * @param pos   the pos
     * @param miner the miner
     * @return the boolean
     */
    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        if (miner.isCreative())
            return false;

        return super.canMine(state, world, pos, miner);
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

        if (material.equals(Material.PLANT) || material.equals(Material.REPLACEABLE_PLANT) || material.equals(Material.UNUSED_PLANT) || material.equals(Material.LEAVES) || material.equals(Material.GOURD))
            return 1.5F;

        return super.getMiningSpeedMultiplier(stack, state);
    }

    static {
        // There is no static reference for SwordItem as their is only one block that a sword is effective on in vanilla.
        EFFECTIVE_BLOCKS = Sets.newHashSet(Blocks.COBWEB);
    }
}