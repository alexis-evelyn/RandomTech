package me.alexisevelyn.randomtech.toolmaterials.poweredtools;

import me.alexisevelyn.randomtech.utility.MiningLevel;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import org.jetbrains.annotations.Nullable;

/**
 * The type Powered tool material.
 */
public class PoweredToolMaterial implements ToolMaterial {
    /**
     * Gets durability.
     *
     * @return the durability
     */
    @Override
    public int getDurability() {
        return -1;
    } // Durability is Ignored as This is Energy Based

    /**
     * Gets mining speed multiplier.
     *
     * @return the mining speed multiplier
     */
    @Override
    public float getMiningSpeedMultiplier() {
        return 15.0F;
    } // Gold is 12

    /**
     * Gets attack damage.
     *
     * @return the attack damage
     */
    @Override
    public float getAttackDamage() {
        return 5.0F;
    } // Netherite is 4

    /**
     * Gets mining level.
     *
     * @return the mining level
     */
    @Override
    public int getMiningLevel() {
        return MiningLevel.POWERED.getValue();
    } // Netherite is 4

    /**
     * Gets enchantability.
     *
     * @return the enchantability
     */
    @Override
    public int getEnchantability() {
        return 30;
    } // Gold is 22. Netherite and Wood is 15. Diamond is 10

    /**
     * Gets repair ingredient.
     *
     * @return the repair ingredient
     */
    // Repair Ingredient is Ignored As We Want To Use Energy To Repair These Tools
    @Nullable
    @Override
    public Ingredient getRepairIngredient() {
        // Ingredient.ofItems(RegistryHelper.REDSTONE_INGOT)
        return null;
    }
}
