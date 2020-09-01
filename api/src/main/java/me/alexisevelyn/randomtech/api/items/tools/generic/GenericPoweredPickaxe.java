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
}