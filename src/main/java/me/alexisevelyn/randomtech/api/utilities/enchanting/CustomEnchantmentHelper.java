package me.alexisevelyn.randomtech.api.utilities.enchanting;

import me.alexisevelyn.randomtech.api.items.armor.generic.GenericPoweredArmor;
import me.alexisevelyn.randomtech.api.items.tools.generic.*;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class CustomEnchantmentHelper {
    public enum ValidEnchant {
        VANILLA,
        FALSE,
        TRUE
    }

    // I need to be able to tell whether or not to interfere with vanilla enchant mechanics, so I'm using an enum to have 3 possible outcomes instead of just 2.
    public static ValidEnchant isValidEnchantment(ItemStack itemStack, EnchantmentTarget target) {
        if (itemStack.getItem() instanceof GenericPoweredTool || itemStack.getItem() instanceof GenericPoweredArmor) {
            // I use unbreaking to affect energy usage, so I'm allowing Unbreaking and Mending (although mending is useless).
            if (target == EnchantmentTarget.BREAKABLE) {
                return ValidEnchant.TRUE;
            }
        }

        // Every Vanilla Mining Tool Except For Swords
        if ((itemStack.getItem() instanceof GenericPoweredAxe || itemStack.getItem() instanceof GenericPoweredHoe || itemStack.getItem() instanceof GenericPoweredPickaxe || itemStack.getItem() instanceof GenericPoweredShovel) && target == EnchantmentTarget.DIGGER) {
            return ValidEnchant.TRUE;
        }

        // For Swords and Axes Specifically
        if ((itemStack.getItem() instanceof GenericPoweredSword || itemStack.getItem() instanceof GenericPoweredAxe) && target == EnchantmentTarget.WEAPON) {
            return ValidEnchant.TRUE;
        }

        // Disables Mining Tool Enchants For Sword
        // NOTE: Don't disable axe, vanilla allows axe to use Mining Tool Enchants
        // I don't know why vanilla axes don't allow Looting, but I'm allowing it on my axes
        if ((itemStack.getItem() instanceof GenericPoweredSword) && target == EnchantmentTarget.DIGGER) {
            return ValidEnchant.FALSE;
        }

        // For Armor - Should be unnecessary given that I extend ArmorItem, but it can't hurt.
        if (itemStack.getItem() instanceof GenericPoweredArmor) {
            GenericPoweredArmor armor = (GenericPoweredArmor) itemStack.getItem();

            if (target == EnchantmentTarget.ARMOR)
                return ValidEnchant.TRUE;
            else if (armor.getSlotType() == EquipmentSlot.HEAD && target == EnchantmentTarget.ARMOR_HEAD)
                return ValidEnchant.TRUE;
            else if (armor.getSlotType() == EquipmentSlot.CHEST && target == EnchantmentTarget.ARMOR_CHEST)
                return ValidEnchant.TRUE;
            else if (armor.getSlotType() == EquipmentSlot.LEGS && target == EnchantmentTarget.ARMOR_LEGS)
                return ValidEnchant.TRUE;
            else if (armor.getSlotType() == EquipmentSlot.FEET && target == EnchantmentTarget.ARMOR_FEET)
                return ValidEnchant.TRUE;
        }

        // TODO: Implement these tool and armor enchants when I create tool and armor that needs them.
        // Unimplemented Custom Items
        // Fishing Rod, Trident, Bow, Wearable, Crossbow, Vanishable

        return ValidEnchant.VANILLA;
    }
}
