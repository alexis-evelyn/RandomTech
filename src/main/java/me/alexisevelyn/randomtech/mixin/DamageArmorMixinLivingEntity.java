package me.alexisevelyn.randomtech.mixin;

import me.alexisevelyn.randomtech.api.items.armor.generic.GenericPoweredArmor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * The type Damage armor mixin living entity.
 */
@SuppressWarnings("UnusedMixin") // The mixin is used, just is loaded by Fabric and not Sponge methods
@Mixin(LivingEntity.class)
public abstract class DamageArmorMixinLivingEntity {

	/**
	 * Gets armor items.
	 *
	 * @return the armor items
	 */
    // @Shadow @Final public PlayerInventory inventory;
	@Shadow public abstract Iterable<ItemStack> getArmorItems();

	/**
	 * Gets attacking.
	 *
	 * @return the attacking
	 */
	@Shadow @Nullable public abstract LivingEntity getAttacking();

	/**
	 * Damage armor.
	 *
	 * @param damageSource the damage source
	 * @param damage       the damage
	 * @param info         the info
	 */
	@Inject(at = @At("HEAD"), method = "damageArmor(Lnet/minecraft/entity/damage/DamageSource;F)V")
	private void damageArmor(DamageSource damageSource, float damage, CallbackInfo info) {
		if (damage > 0.0F) {

			// Divide damage by 4. May remove this later.
			damage /= 4.0F;

			// If damage is less than one, but greater than 0, set to 1.
			if (damage < 1.0F)
				damage = 1.0F;

			float finalDamage = damage;
			this.getArmorItems().forEach(armor -> {
				System.out.println("Armor Piece: " + armor.getItem());
				if ((!damageSource.isFire() || !armor.getItem().isFireproof()) && armor.getItem() instanceof GenericPoweredArmor) {
					((GenericPoweredArmor) armor.getItem()).addDamage(armor, this.getAttacking(), damageSource, finalDamage);
				}
			});
		}
	}
}
