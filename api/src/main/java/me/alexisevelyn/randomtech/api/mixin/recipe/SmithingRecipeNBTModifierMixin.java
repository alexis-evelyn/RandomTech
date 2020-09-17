package me.alexisevelyn.randomtech.api.mixin.recipe;

import me.alexisevelyn.randomtech.api.items.energy.EnergyHelper;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.SmithingRecipe;
import org.apiguardian.api.API;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Modifies Smiting Table Recipes to Allow Setting The Energy Level of An Item From The Durability Level of An Old Item
 */
@API(status = API.Status.INTERNAL)
@SuppressWarnings("UnusedMixin") // The mixin is used, just is loaded by Fabric and not Sponge methods
@Mixin(SmithingRecipe.class)
public abstract class SmithingRecipeNBTModifierMixin {
//	@Shadow @Final private Identifier id; // Recipe ID
//	@Shadow @Final private Ingredient base; // Old Item
//	@Shadow @Final private Ingredient addition; // Conversion Ingredient
	@Shadow @Final private ItemStack result; // New Item

	/**
	 * When the smithing table tries to figure out what the output recipe
	 *
	 * @param inventory the Smithing Table's Inventory
	 * @param info      Used to modify the return type (See {@link CallbackInfoReturnable}).
	 */
    // Cancellable must be allowed, otherwise the game crashes when trying to modify the return value
	@API(status = API.Status.INTERNAL)
	@SuppressWarnings("PMD.UnusedPrivateMethod")
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