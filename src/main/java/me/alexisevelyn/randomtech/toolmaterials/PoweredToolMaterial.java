package me.alexisevelyn.randomtech.toolmaterials;

import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;
import org.jetbrains.annotations.Nullable;

public class PoweredToolMaterial implements ToolMaterial {
    @Override
    public int getDurability() {
        return -1;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 5.0F;
    }

    @Override
    public float getAttackDamage() {
        return 1;
    }

    @Override
    public int getMiningLevel() {
        return ToolMaterials.NETHERITE.getMiningLevel();
    }

    @Override
    public int getEnchantability() {
        return 30;
    }

    @Nullable
    @Override
    public Ingredient getRepairIngredient() {
        // Ingredient.ofItems(RegistryHelper.REDSTONE_INGOT)
        return null;
    }
}
