package me.alexisevelyn.randomtech.mixin;

import me.alexisevelyn.randomtech.api.items.armor.generic.GenericPoweredArmor;
import me.alexisevelyn.randomtech.api.items.tools.generic.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("UnusedMixin") // The mixin is used, just is loaded by Fabric and not Sponge methods
@Mixin(Enchantment.class)
public abstract class EnchantHelperMixin {

	@Shadow @Final public EnchantmentTarget type;

	@Inject(at = @At("TAIL"), method = "isAcceptableItem(Lnet/minecraft/item/ItemStack;)Z", cancellable = true)
	private void isAcceptableItem(ItemStack itemStack, CallbackInfoReturnable<Boolean> info) {
		System.out.println("---");
		System.out.println("Item: " + new TranslatableText(itemStack.getItem().getTranslationKey()));
		System.out.println("Item Type: " + this.type);
		System.out.println("Return: " + info.getReturnValue());
		System.out.println("---");

		if (itemStack.getItem() instanceof GenericPoweredTool || itemStack.getItem() instanceof GenericPoweredArmor) {
			// I use unbreaking to affect energy usage, so I'm allowing Unbreaking and Mending (although mending is useless).
			if (this.type == EnchantmentTarget.BREAKABLE) {
				info.setReturnValue(true);
			}
		}

		// Every Vanilla Mining Tool Except For Swords
		if ((itemStack.getItem() instanceof GenericPoweredAxe || itemStack.getItem() instanceof GenericPoweredHoe || itemStack.getItem() instanceof GenericPoweredPickaxe || itemStack.getItem() instanceof GenericPoweredShovel) && this.type == EnchantmentTarget.DIGGER) {
			info.setReturnValue(true);
		}

		// For Swords and Axes Specifically
		if ((itemStack.getItem() instanceof GenericPoweredSword || itemStack.getItem() instanceof GenericPoweredAxe) && this.type == EnchantmentTarget.WEAPON) {
			info.setReturnValue(true);
		}

		// Disables Mining Tool Enchants For Sword
		// NOTE: Don't disable axe, vanilla allows axe to use Mining Tool Enchants
		// I don't know why vanilla axes don't allow Looting, but I'm allowing it on my axes
		if ((itemStack.getItem() instanceof GenericPoweredSword) && this.type == EnchantmentTarget.DIGGER) {
			info.setReturnValue(false);
		}

		// For Armor - Should be unnecessary given that I extend ArmorItem, but it can't hurt.
		if (itemStack.getItem() instanceof GenericPoweredArmor) {
			GenericPoweredArmor armor = (GenericPoweredArmor) itemStack.getItem();

			if (this.type == EnchantmentTarget.ARMOR)
				info.setReturnValue(true);
			else if (armor.getSlotType() == EquipmentSlot.HEAD && this.type == EnchantmentTarget.ARMOR_HEAD)
				info.setReturnValue(true);
			else if (armor.getSlotType() == EquipmentSlot.CHEST && this.type == EnchantmentTarget.ARMOR_CHEST)
				info.setReturnValue(true);
			else if (armor.getSlotType() == EquipmentSlot.LEGS && this.type == EnchantmentTarget.ARMOR_LEGS)
				info.setReturnValue(true);
			else if (armor.getSlotType() == EquipmentSlot.FEET && this.type == EnchantmentTarget.ARMOR_FEET)
				info.setReturnValue(true);
		}

		// TODO: Implement these tool and armor enchants when I create tool and armor that needs them.
		// Unimplemented Custom Items
		// Fishing Rod, Trident, Bow, Wearable, Crossbow, Vanishable
	}
}


// So, I'm not sure how to mixin an enum.
//@SuppressWarnings("UnusedMixin") // The mixin is used, just is loaded by Fabric and not Sponge methods
//@Mixin(EnchantmentTarget.class)
//public abstract class EnchantHelperMixin {
//
//	// Lnet/minecraft/enchantment/EnchantmentTarget$14;isAcceptableItem(Lnet/minecraft/item/Item;)Z
//	// Lnet/minecraft/enchantment/EnchantmentTarget$1;isAcceptableItem(Lnet/minecraft/item/Item;)Z
//	@Inject(at = @At("HEAD"), method = "isAcceptableItem(Lnet/minecraft/item/Item;)Z")
//	private void isAcceptableItem(Item item, CallbackInfoReturnable<Boolean> info) {
//		System.out.println("Item: " + item.getName() + " Return: " + info.getReturnValue());
//	}
//}
