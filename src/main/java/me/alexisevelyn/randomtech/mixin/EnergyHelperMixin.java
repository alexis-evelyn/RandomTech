package me.alexisevelyn.randomtech.mixin;

import me.alexisevelyn.randomtech.api.armor.energy.EnergyHelper;
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

@SuppressWarnings("UnusedMixin") // The mixin is used, just is loaded by Fabric and not Sponge methods
@Mixin(PlayerEntity.class)
public abstract class EnergyHelperMixin {

	@Shadow @Final public PlayerInventory inventory;

	@Inject(at = @At("INVOKE"), method = "damageArmor(Lnet/minecraft/entity/damage/DamageSource;F)V")
	private void damageArmor(DamageSource damageSource, float damage, CallbackInfo info) {
		if (damage > 0.0F) {

			// Divide damage by 4. May remove this later.
			damage /= 4.0F;

			// If damage is less than one, but greater than 0, set to 1.
			if (damage < 1.0F)
				damage = 1.0F;

 			for(int i = 0; i < this.inventory.armor.size(); ++i) {
				ItemStack itemStack = this.inventory.armor.get(i);

				if ((!damageSource.isFire() || !itemStack.getItem().isFireproof()) && itemStack.getItem() instanceof EnergyHelper) {
					((EnergyHelper) itemStack.getItem()).addDamage(itemStack, this.inventory.player, damageSource, damage);
				}
			}
		}
	}
}
