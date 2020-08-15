package me.alexisevelyn.randomtech.armormaterials;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

/**
 * The type Powered armor material.
 */
public class PoweredArmorMaterial implements ArmorMaterial {
    private static final int[] BASE_DURABILITY = new int[] {-1, -1, -1, -1};
    private static final int[] PROTECTION_AMOUNTS = new int[] {3, 6, 8, 3};

    /**
     * Gets durability.
     *
     * @param slot the slot
     * @return the durability
     */
    @Override
    public int getDurability(EquipmentSlot slot) {
        return BASE_DURABILITY[slot.getEntitySlotId()];
    }

    /**
     * Gets protection amount.
     *
     * @param slot the slot
     * @return the protection amount
     */
    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return PROTECTION_AMOUNTS[slot.getEntitySlotId()];
    }

    /**
     * Gets enchantability.
     *
     * @return the enchantability
     */
    @Override
    public int getEnchantability() {
        return 30;
    }

    /**
     * Gets equip sound.
     *
     * @return the equip sound
     */
    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE;
    }

    /**
     * Gets repair ingredient.
     *
     * @return the repair ingredient
     */
    @Override
    public Ingredient getRepairIngredient() {
        //return Ingredient.ofItems(RegistryHelper.REDSTONE_INGOT);
        return null;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
// Used for Finding Armor Models
    @Override
    public String getName() {
        return "powered";
    }

    /**
     * Gets toughness.
     *
     * @return the toughness
     */
// I'm not entirely sure how armor toughness works, so I'm just stepping up one from Netherite.
    @Override
    public float getToughness() {
        return 4;
    }

    /**
     * Gets knockback resistance.
     *
     * @return the knockback resistance
     */
    @Override
    public float getKnockbackResistance() {
        return 0.1F; // 0.1 is +1 Knockback Resistance. 1 is +10 Knockback Resistance.
    }
}
