package me.alexisevelyn.randomtech.mixin;

import me.alexisevelyn.randomtech.api.armor.energy.EnergyHelper;
import me.alexisevelyn.randomtech.items.armor.generic.GenericPoweredArmor;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@SuppressWarnings("UnusedMixin") // The mixin is used, just is loaded by Fabric and not Sponge methods
@Mixin(PlayerEntity.class)
public abstract class EnergyHelperMixin {

	@Shadow @Final public PlayerInventory inventory;

	@Inject(at = @At("INVOKE"), method = "damageArmor(Lnet/minecraft/entity/damage/DamageSource;F)V")
	private void damageArmor(DamageSource damageSource, float damage, CallbackInfo info) {
		if (damage > 0.0F) {
			for(int i = 0; i < this.inventory.armor.size(); ++i) {
				ItemStack itemStack = this.inventory.armor.get(i);

				if ((!damageSource.isFire() || !itemStack.getItem().isFireproof()) && itemStack.getItem() instanceof EnergyHelper) {
					((EnergyHelper) itemStack.getItem()).addDamage(itemStack, damageSource, damage);
				}
			}
		}
	}
}
