package me.alexisevelyn.randomtech.mixin;

import com.google.common.collect.Lists;
import me.alexisevelyn.randomtech.api.items.armor.generic.GenericPoweredArmor;
import me.alexisevelyn.randomtech.api.items.tools.generic.*;
import me.alexisevelyn.randomtech.api.utilities.enchanting.CustomEnchantmentHelper;
import net.minecraft.enchantment.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;
import java.util.List;

@SuppressWarnings("UnusedMixin") // The mixin is used, just is loaded by Fabric and not Sponge methods
@Mixin(EnchantmentHelper.class)
public abstract class TableEnchantHelperMixin {
	// Cancellable must be allowed, otherwise the game crashes when trying to modify the return value
	@Inject(at = @At("TAIL"), method = "getPossibleEntries(ILnet/minecraft/item/ItemStack;Z)Ljava/util/List;", cancellable = true)
	private static void getPossibleEntries(int power, ItemStack itemStack, boolean treasureAllowed, CallbackInfoReturnable<List<EnchantmentLevelEntry>> info) {
		// Only mess with enchantment table (and other enchanted loot generation) if our items.
		if (!(itemStack.getItem() instanceof GenericPoweredTool || itemStack.getItem() instanceof GenericPoweredArmor))
			return;

		Iterator<Enchantment> enchantmentIterator = Registry.ENCHANTMENT.iterator();
		EnchantmentTarget enchantmentTarget = enchantmentIterator.next().type;

		System.out.println("---");
		System.out.println("Item: " + new TranslatableText(itemStack.getItem().getTranslationKey()));
		System.out.println("Item Type: " + enchantmentTarget);

//		System.out.println("Return: ");
//		for (EnchantmentLevelEntry entry : info.getReturnValue())
//			System.out.println(entry.enchantment.toString());

		System.out.println("---");

		List<EnchantmentLevelEntry> customAllowedEnchants = Lists.newArrayList();
		while (enchantmentIterator.hasNext()) {
			Enchantment enchantment = enchantmentIterator.next();

			if (CustomEnchantmentHelper.isValidEnchantment(itemStack, enchantment.type) == CustomEnchantmentHelper.ValidEnchant.TRUE)
				customAllowedEnchants.add(new EnchantmentLevelEntry(enchantment, 1));
		}

		info.setReturnValue(customAllowedEnchants);
	}


}