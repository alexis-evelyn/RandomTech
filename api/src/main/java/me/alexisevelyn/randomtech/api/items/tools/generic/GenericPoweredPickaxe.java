package me.alexisevelyn.randomtech.api.items.tools.generic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.EnergyTier;

/**
 * The type Generic powered pickaxe.
 */
public abstract class GenericPoweredPickaxe extends GenericPoweredTool {
    private static final float attackDamage = 1;

    /**
     * Instantiates a new Generic powered pickaxe.
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
    public GenericPoweredPickaxe(ToolMaterial material, int energyCapacity, EnergyTier tier, int cost, float poweredSpeed, float unpoweredSpeed, Settings settings, @Nullable String dischargedTranslationKey) {
        super(material, energyCapacity, tier, cost, poweredSpeed, unpoweredSpeed, attackDamage, PickaxeItem.EFFECTIVE_BLOCKS, settings, dischargedTranslationKey);
    }

    /**
     * Is effective on boolean.
     *
     * @param state the state
     * @return the boolean
     */
    @Override
    public boolean isEffectiveOn(BlockState state) {
        int i = this.getMaterial().getMiningLevel();

        if (!state.isOf(Blocks.OBSIDIAN) && !state.isOf(Blocks.CRYING_OBSIDIAN) && !state.isOf(Blocks.NETHERITE_BLOCK) && !state.isOf(Blocks.RESPAWN_ANCHOR) && !state.isOf(Blocks.ANCIENT_DEBRIS)) {
            if (!state.isOf(Blocks.DIAMOND_BLOCK) && !state.isOf(Blocks.DIAMOND_ORE) && !state.isOf(Blocks.EMERALD_ORE) && !state.isOf(Blocks.EMERALD_BLOCK) && !state.isOf(Blocks.GOLD_BLOCK) && !state.isOf(Blocks.GOLD_ORE) && !state.isOf(Blocks.REDSTONE_ORE)) {
                if (!state.isOf(Blocks.IRON_BLOCK) && !state.isOf(Blocks.IRON_ORE) && !state.isOf(Blocks.LAPIS_BLOCK) && !state.isOf(Blocks.LAPIS_ORE)) {
                    Material material = state.getMaterial();
                    return material == Material.STONE || material == Material.METAL || material == Material.REPAIR_STATION || state.isOf(Blocks.NETHER_GOLD_ORE);
                } else {
                    return i >= 1;
                }
            } else {
                return i >= 2;
            }
        } else {
            return i >= 3;
        }
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
        return material != Material.METAL && material != Material.REPAIR_STATION && material != Material.STONE ? super.getMiningSpeedMultiplier(stack, state) : this.miningSpeed;
    }
}