package me.alexisevelyn.randomtech.mixin.enchanting;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import me.alexisevelyn.randomtech.api.utilities.enchanting.CustomEnchantmentHelper;

/**
 * The type Anvil enchant helper mixin.
 */
@SuppressWarnings("UnusedMixin") // The mixin is used, just is loaded by Fabric and not Sponge methods
@Mixin(Enchantment.class)
public abstract class AnvilEnchantHelperMixin {

	@Shadow @Final public EnchantmentTarget type;

	/**
	 * Is acceptable item.
	 *
	 * @param itemStack the item stack
	 * @param info      the info
	 */
    // Cancellable must be allowed, otherwise the game crashes when trying to modify the return value
	@Inject(at = @At("TAIL"), method = "isAcceptableItem(Lnet/minecraft/item/ItemStack;)Z", cancellable = true)
	private void isAcceptableItem(ItemStack itemStack, CallbackInfoReturnable<Boolean> info) {
//		System.out.println("---");
//		System.out.println("Item: " + new TranslatableText(itemStack.getItem().getTranslationKey()));
//		System.out.println("Item Type: " + this.type);
//		System.out.println("Return: " + info.getReturnValue());
//		System.out.println("---");

		CustomEnchantmentHelper.ValidEnchant isValid = CustomEnchantmentHelper.isValidEnchantment(itemStack, this.type);

		if (isValid == CustomEnchantmentHelper.ValidEnchant.TRUE)
			info.setReturnValue(true);
		else if (isValid == CustomEnchantmentHelper.ValidEnchant.FALSE)
			info.setReturnValue(false);
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
