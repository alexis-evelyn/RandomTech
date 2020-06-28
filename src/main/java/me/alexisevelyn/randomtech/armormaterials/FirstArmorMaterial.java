package me.alexisevelyn.randomtech.armormaterials;

import me.alexisevelyn.randomtech.Main;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class FirstArmorMaterial implements ArmorMaterial {
    private static final int[] BASE_DURABILITY = new int[] {1337, 1338, 1339, 1340};
    private static final int[] PROTECTION_AMOUNTS = new int[] {2, 5, 6, 3};

    @Override
    public int getDurability(EquipmentSlot slot) {
        return BASE_DURABILITY[slot.getEntitySlotId()];
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return PROTECTION_AMOUNTS[slot.getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return 30;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Main.FIRST_ITEM);
    }

    @Override
    public String getName() {
        return "leet";
    }

    @Override
    public float getToughness() {
        return 2;
    }

    @Override
    public float getKnockbackResistance() {
        return 30;
    }
}
