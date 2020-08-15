package me.alexisevelyn.randomtech.mixin;

import me.alexisevelyn.randomtech.api.items.armor.generic.GenericPoweredArmor;
import me.alexisevelyn.randomtech.api.items.energy.EnergyHelper;
import me.alexisevelyn.randomtech.api.items.tools.generic.GenericPoweredTool;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.SmithingRecipe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// NOTE: This mixin only exists because you can't specify nbt data in a crafting recipe.

/**
 * The type Smithing recipe nbt modifier mixin.
 */
@SuppressWarnings("UnusedMixin") // The mixin is used, just is loaded by Fabric and not Sponge methods
@Mixin(SmithingRecipe.class)
public abstract class SmithingRecipeNBTModifierMixin {
//	@Shadow @Final private Identifier id; // Recipe ID
//	@Shadow @Final private Ingredient base; // Old Item
//	@Shadow @Final private Ingredient addition; // Conversion Ingredient
	@Shadow @Final private ItemStack result; // New Item

	/**
	 * Craft.
	 *
	 * @param inventory the inventory
	 * @param info      the info
	 */
// Cancellable must be allowed, otherwise the game crashes when trying to modify the return value
	@Inject(at = @At("TAIL"), method = "craft(Lnet/minecraft/inventory/Inventory;)Lnet/minecraft/item/ItemStack;", cancellable = true)
	private void craft(Inventory inventory, CallbackInfoReturnable<ItemStack> info) {
		ItemStack oldItemStack = inventory.getStack(0);
		ItemStack newItemStack = result.copy();

		Item item = newItemStack.getItem();

		CompoundTag compoundTag = oldItemStack.getTag();

		if (item instanceof EnergyHelper)
			info.setReturnValue(((EnergyHelper) item).onCraft(oldItemStack, newItemStack, compoundTag));
	}
}