package me.alexisevelyn.randomtech.api.mixin.overpowered;

import me.alexisevelyn.randomtech.api.items.armor.generic.InvulnerabilityHandler;
import me.alexisevelyn.randomtech.api.utilities.CustomDamageSource;
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

/**
 * The type Kill prevention mixin.
 */
@SuppressWarnings("UnusedMixin") // The mixin is used, just is loaded by Fabric and not Sponge methods
@Mixin(LivingEntity.class)
public abstract class KillPreventionMixin {
	// For /kill invulnerability for api
    // Also, for general damage prevention

    /**
     * Damage boolean.
     *
     * @param source the source
     * @param amount the amount
     * @return the boolean
     */
    @Shadow public abstract boolean damage(DamageSource source, float amount);

    /**
     * General damage.
     *
     * @param damageSource the damage source
     * @param amount       the amount
     * @param info         the info
     */
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    @Inject(at = @At("HEAD"), method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", cancellable = true)
	private void generalDamage(DamageSource damageSource, float amount, CallbackInfoReturnable<Boolean> info) {
        //noinspection ConstantConditions
        if ((Object) this instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) (Object) this;

            handlePlayerDamage(playerEntity, damageSource, amount, info);
            return;
        }

        //noinspection ConstantConditions
        if (!((Object) this instanceof LivingEntity))
            return;

        LivingEntity livingEntity = (LivingEntity) (Object) this;
        handleOtherLivingEntityDamage(livingEntity, damageSource, amount, info);
	}

    /**
     * Kill command.
     *
     * @param info the info
     */
    @Inject(at = @At("INVOKE"), method = "kill()V", cancellable = true)
	private void killCommand(CallbackInfo info) {
        //noinspection ConstantConditions - This claims to always return false, but it doesn't. That's because `this` becomes LivingEntity on runtime.
        if ((Object) this instanceof PlayerEntity) { // if ((LivingEntity) (Object) this instanceof PlayerEntity)
            PlayerEntity playerEntity = (PlayerEntity) (Object) this;

            handlePlayerKillCommand(playerEntity, info);
        } else //noinspection ConstantConditions
            if (((Object) this instanceof LivingEntity)) {
            LivingEntity livingEntity = (LivingEntity) (Object) this;

            handleOtherLivingEntityKillCommand(livingEntity, info);
        }

        // If not already cancelled, cancel the kill command and replace it with a new damage source just for it.
        // This is so I can differentiate between the kill command and the void in the general damage code
        if (!info.isCancelled()) {
            this.damage(CustomDamageSource.KILL_COMMAND, Float.MAX_VALUE);
            info.cancel();
        }
    }

    /**
     * Handle player damage.
     *
     * @param playerEntity the player entity
     * @param damageSource the damage source
     * @param amount       the amount
     * @param info         the info
     */
    // Generic Damage to Players
    private void handlePlayerDamage(PlayerEntity playerEntity, DamageSource damageSource, float amount, CallbackInfo info) {
        PlayerInventory inventory = playerEntity.inventory;
        DefaultedList<ItemStack> armorItems = inventory.armor;

        for (ItemStack armorPiece : armorItems) {
            if (!(armorPiece.getItem() instanceof InvulnerabilityHandler))
                continue;

            InvulnerabilityHandler invulnerabilityHandlerPiece = (InvulnerabilityHandler) armorPiece.getItem();

            if (invulnerabilityHandlerPiece.denyGeneralDamage(armorPiece, damageSource, amount, playerEntity))
                info.cancel();
        }
    }

    /**
     * Handle other living entity damage.
     *
     * @param livingEntity the living entity
     * @param damageSource the damage source
     * @param amount       the amount
     * @param info         the info
     */
    // Generic Damage to Other Living Entities
    private void handleOtherLivingEntityDamage(LivingEntity livingEntity, DamageSource damageSource, float amount, CallbackInfo info) {
        Iterable<ItemStack> armorItems = livingEntity.getArmorItems();

        for (ItemStack armorPiece : armorItems) {
            if (!(armorPiece.getItem() instanceof InvulnerabilityHandler))
                continue;

            InvulnerabilityHandler invulnerabilityHandlerPiece = (InvulnerabilityHandler) armorPiece.getItem();

            if (invulnerabilityHandlerPiece.denyGeneralDamage(armorPiece, damageSource, amount, livingEntity))
                info.cancel();
        }
    }

    /**
     * Handle player kill command.
     *
     * @param playerEntity the player entity
     * @param info         the info
     */
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

    /**
     * Handle other living entity kill command.
     *
     * @param livingEntity the living entity
     * @param info         the info
     */
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