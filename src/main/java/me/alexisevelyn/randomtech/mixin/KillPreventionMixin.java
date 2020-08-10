package me.alexisevelyn.randomtech.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("UnusedMixin") // The mixin is used, just is loaded by Fabric and not Sponge methods
@Mixin(PlayerEntity.class)
public abstract class KillPreventionMixin {
	// For /kill invulnerability for api

    @Shadow public abstract boolean damage(DamageSource source, float amount);
    // @Shadow public abstract void kill();

    @Inject(at = @At("HEAD"), method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", cancellable = true)
	private void damage(DamageSource damageSource, float amount, CallbackInfoReturnable<Boolean> info) {
//        if (damageSource.isOutOfWorld())
//            info.setReturnValue(false);
	}

    // @Inject(at = @At("HEAD"), method = "kill()V", cancellable = true)
	private void kill(CallbackInfo info) {
        // TODO: Figure out how to overwrite this method for the player only
    }
}