package me.alexisevelyn.randomtech.mixin;

import me.alexisevelyn.randomtech.api.items.armor.generic.InvulnerabilityHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("UnusedMixin") // The mixin is used, just is loaded by Fabric and not Sponge methods
@Mixin(LivingEntity.class)
public abstract class KillPreventionMixin {
	// For /kill invulnerability for api

    @Shadow public abstract boolean damage(DamageSource source, float amount);
    // @Shadow public abstract void kill();

    @Inject(at = @At("HEAD"), method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", cancellable = true)
	private void damage(DamageSource damageSource, float amount, CallbackInfoReturnable<Boolean> info) {
        //noinspection ConstantConditions
        if ((Object) this instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) (Object) this;

            handlePlayerDamage(playerEntity, info);
            return;
        }

        //noinspection ConstantConditions
        if (!((Object) this instanceof LivingEntity))
            return;

        LivingEntity livingEntity = (LivingEntity) (Object) this;
        handleOtherLivingEntityDamage(livingEntity, info);
	}

    @Inject(at = @At("INVOKE"), method = "kill()V", cancellable = true)
	private void kill(CallbackInfo info) {
        //noinspection ConstantConditions - This claims to always return false, but it doesn't. That's because `this` becomes LivingEntity on runtime.
        if ((Object) this instanceof PlayerEntity) { // if ((LivingEntity) (Object) this instanceof PlayerEntity)
            PlayerEntity playerEntity = (PlayerEntity) (Object) this;

            handlePlayerKillCommand(playerEntity, info);
            return;
        }

        //noinspection ConstantConditions
        if (!((Object) this instanceof LivingEntity))
            return;

        LivingEntity livingEntity = (LivingEntity) (Object) this;
        handleOtherLivingEntityKillCommand(livingEntity, info);
    }

    // Generic Damage to Players
    private void handlePlayerDamage(PlayerEntity playerEntity, CallbackInfo info) {
        PlayerInventory inventory = playerEntity.inventory;
        DefaultedList<ItemStack> armorItems = inventory.armor;

        for (ItemStack armorPiece : armorItems) {
            if (!(armorPiece.getItem() instanceof InvulnerabilityHandler))
                continue;

            InvulnerabilityHandler invulnerabilityHandlerPiece = (InvulnerabilityHandler) armorPiece.getItem();

            if (invulnerabilityHandlerPiece.denyGeneralKill(armorPiece, playerEntity))
                info.cancel();
        }
    }

    // Generic Damage to Other Living Entities
    private void handleOtherLivingEntityDamage(LivingEntity livingEntity, CallbackInfo info) {
        Iterable<ItemStack> armorItems = livingEntity.getArmorItems();

        for (ItemStack armorPiece : armorItems) {
            if (!(armorPiece.getItem() instanceof InvulnerabilityHandler))
                continue;

            InvulnerabilityHandler invulnerabilityHandlerPiece = (InvulnerabilityHandler) armorPiece.getItem();

            if (invulnerabilityHandlerPiece.denyGeneralKill(armorPiece, livingEntity))
                info.cancel();
        }
    }

    // Kill Command Specifically for Player
    private void handlePlayerKillCommand(PlayerEntity playerEntity, CallbackInfo info) {
        PlayerInventory inventory = playerEntity.inventory;
        DefaultedList<ItemStack> armorItems = inventory.armor;

        for (ItemStack armorPiece : armorItems) {
            if (!(armorPiece.getItem() instanceof InvulnerabilityHandler))
                continue;

            InvulnerabilityHandler invulnerabilityHandlerPiece = (InvulnerabilityHandler) armorPiece.getItem();

            if (invulnerabilityHandlerPiece.denyKillCommand(armorPiece, playerEntity))
                info.cancel();
        }
    }

    // Kill Command Specifically for Other Living Entities
    private void handleOtherLivingEntityKillCommand(LivingEntity livingEntity, CallbackInfo info) {
        Iterable<ItemStack> armorItems = livingEntity.getArmorItems();

        for (ItemStack armorPiece : armorItems) {
            if (!(armorPiece.getItem() instanceof InvulnerabilityHandler))
                continue;

            InvulnerabilityHandler invulnerabilityHandlerPiece = (InvulnerabilityHandler) armorPiece.getItem();

            if (invulnerabilityHandlerPiece.denyKillCommand(armorPiece, livingEntity))
                info.cancel();
        }
    }
}