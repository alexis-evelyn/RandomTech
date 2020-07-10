package me.alexisevelyn.randomtech.toolmaterials;

import me.alexisevelyn.randomtech.utility.RegistryHelper;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;

public class PoweredToolMaterial implements ToolMaterial {
    @Override
    public int getDurability() {
        return 1337;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 5.0F;
    }

    @Override
    public float getAttackDamage() {
        return 1337;
    }

    @Override
    public int getMiningLevel() {
        return ToolMaterials.NETHERITE.getMiningLevel();
    }

    @Override
    public int getEnchantability() {
        return 30;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(RegistryHelper.REDSTONE_INGOT);
    }
}
