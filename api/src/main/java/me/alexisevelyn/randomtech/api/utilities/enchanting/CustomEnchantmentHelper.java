package me.alexisevelyn.randomtech.api.utilities.enchanting;

import me.alexisevelyn.randomtech.api.items.armor.generic.GenericPoweredArmor;
import me.alexisevelyn.randomtech.api.items.energy.EnergyHelper;
import me.alexisevelyn.randomtech.api.items.tools.generic.*;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import org.apiguardian.api.API;

/**
 * Helper class for my mixins {@link me.alexisevelyn.randomtech.api.mixin.enchanting.AnvilEnchantHelperMixin} and {@link me.alexisevelyn.randomtech.api.mixin.enchanting.TableEnchantHelperMixin}.
 */
@API(status = API.Status.INTERNAL)
public class CustomEnchantmentHelper {
    /**
     * Enum for mixins {@link me.alexisevelyn.randomtech.api.mixin.enchanting.AnvilEnchantHelperMixin} and {@link me.alexisevelyn.randomtech.api.mixin.enchanting.TableEnchantHelperMixin} to determine how to handle enchanting an item.
     */
    @API(status = API.Status.INTERNAL)
    public enum ValidEnchant {
        VANILLA,
        FALSE,
        TRUE
    }

    /**
     * Determines if an enchantment should be modified by my mixins {@link me.alexisevelyn.randomtech.api.mixin.enchanting.AnvilEnchantHelperMixin} and {@link me.alexisevelyn.randomtech.api.mixin.enchanting.TableEnchantHelperMixin}.
     *
     * Currently does not handle the enchants for {@link EnchantmentTarget#FISHING_ROD}, {@link EnchantmentTarget#TRIDENT}, {@link EnchantmentTarget#BOW}, {@link EnchantmentTarget#WEARABLE}, {@link EnchantmentTarget#CROSSBOW}, and {@link EnchantmentTarget#VANISHABLE}.
     *
     * The unhandled enchants default to {@link ValidEnchant#VANILLA}.
     *
     * @param itemStack the tool to enchant
     * @param target    the target chosen by the enchantment in question
     * @return {@link ValidEnchant#VANILLA} if Minecraft should handle the enchantment, {@link ValidEnchant#TRUE} if apply enchantment, {@link ValidEnchant#FALSE} if deny enchantment
     */
    @API(status = API.Status.INTERNAL)
    public static ValidEnchant isValidEnchantment(ItemStack itemStack, EnchantmentTarget target) {
        if (itemStack.getItem() instanceof EnergyHelper && target == EnchantmentTarget.BREAKABLE)
            // I use unbreaking to affect energy usage, so I'm allowing Unbreaking and Mending.
            return ValidEnchant.TRUE;

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

        // Unimplemented Custom Items
        // Fishing Rod, Trident, Bow, Wearable, Crossbow, Vanishable

        return ValidEnchant.VANILLA;
    }
}