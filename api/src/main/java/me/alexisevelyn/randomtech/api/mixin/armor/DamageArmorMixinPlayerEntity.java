package me.alexisevelyn.randomtech.api.mixin.armor;

import me.alexisevelyn.randomtech.api.items.armor.generic.GenericPoweredArmor;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * The type Damage armor mixin player entity.
 */
@SuppressWarnings("UnusedMixin") // The mixin is used, just is loaded by Fabric and not Sponge methods
@Mixin(PlayerEntity.class)
public abstract class DamageArmorMixinPlayerEntity {
	@Shadow @Final public PlayerInventory inventory;

	/**
	 * Damage armor.
	 *
	 * @param damageSource the damage source
	 * @param damage       the damage
	 * @param info         the info
	 */
	@SuppressWarnings({"PMD.UnusedPrivateMethod", "PMD.UnusedFormalParameter"})
	@Inject(at = @At("INVOKE"), method = "damageArmor(Lnet/minecraft/entity/damage/DamageSource;F)V")
	private void damageArmor(DamageSource damageSource, float damage, CallbackInfo info) {
		float currentDamage = damage;

		if (currentDamage > 0.0F) {

			// Divide damage by 4. May remove this later.
			currentDamage /= 4.0F;

			// If damage is less than one, but greater than 0, set to 1.
			if (currentDamage < 1.0F)
				currentDamage = 1.0F;

			for (ItemStack armorPiece : this.inventory.armor) {
				if ((!damageSource.isFire() || !armorPiece.getItem().isFireproof()) && armorPiece.getItem() instanceof GenericPoweredArmor) {
					((GenericPoweredArmor) armorPiece.getItem()).addDamage(armorPiece, this.inventory.player, damageSource, currentDamage);
				}
			}
		}
	}
}