package me.alexisevelyn.randomtech.toolmaterials.poweredtools;

import me.alexisevelyn.randomtech.api.utilities.MiningLevel;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import org.jetbrains.annotations.Nullable;

public class PoweredToolMaterial implements ToolMaterial {
    @Override
    public int getDurability() {
        return -1;
    } // Durability is Ignored as This is Energy Based

    @Override
    public float getMiningSpeedMultiplier() {
        return 15.0F;
    } // Gold is 12

    @Override
    public float getAttackDamage() {
        return 5.0F;
    } // Netherite is 4

    @Override
    public int getMiningLevel() {
        return MiningLevel.POWERED.getValue();
    } // Netherite is 4

    @Override
    public int getEnchantability() {
        return 30;
    } // Gold is 22. Netherite and Wood is 15. Diamond is 10

    // Repair Ingredient is Ignored As We Want To Use Energy To Repair These Tools
    @Nullable
    @Override
    public Ingredient getRepairIngredient() {
        // Ingredient.ofItems(RegistryHelper.REDSTONE_INGOT)
        return null;
    }
}
