package me.alexisevelyn.randomtech.api.mixin.overpowered;

import me.alexisevelyn.randomtech.api.items.armor.generic.InvulnerabilityHandler;
import me.alexisevelyn.randomtech.api.utilities.CustomDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.apiguardian.api.API;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Allows /kill Invulnerability and general damage cancelling for {@link net.minecraft.item.Wearable} items that implement {@link InvulnerabilityHandler}.
 */
@API(status = API.Status.INTERNAL)
@SuppressWarnings("UnusedMixin") // The mixin is used, just is loaded by Fabric and not Sponge methods
@Mixin(LivingEntity.class)
public abstract class KillPreventionMixin {
    /**
     * Vanilla method to determine if damage is allowed or cancelled.
     * <br><br>
     *
     * @param source the source of damage. Can take any class that extends {@link DamageSource}. For example, {@link CustomDamageSource}.
     * @param amount the amount of damage to inflict on this entity.
     * @return true if damage is allowed. false if damage is cancelled.
     */
    @API(status = API.Status.INTERNAL)
    @Shadow public abstract boolean damage(DamageSource source, float amount);

    /**
     * Handles general damage to players and other living entities.
     *
     * Internally calls {@link #handlePlayerDamage(PlayerEntity, DamageSource, float, CallbackInfo)} for handling player damage and calls {@link #handleOtherLivingEntityDamage(LivingEntity, DamageSource, float, CallbackInfo)} for other living entities.
     * <br><br>
     *
     * @param damageSource the source of damage.
     * @param amount       the amount of damage to inflict on this entity.
     * @param info         Used to modify the return type (See {@link CallbackInfoReturnable}).
     */
    @API(status = API.Status.INTERNAL)
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
     * Handles damage from the kill command.
     *
     * We change the kill command to a custom damage source (see {@link CustomDamageSource#KILL_COMMAND}), so {@link #generalDamage(DamageSource, float, CallbackInfoReturnable)} does not handle the kill command later on.
     *
     * Internally calls {@link #handlePlayerKillCommand(PlayerEntity, CallbackInfo)} for player kills and calls {@link #handleOtherLivingEntityKillCommand(LivingEntity, CallbackInfo)} for other living entities.
     * <br><br>
     *
     * @param info Used to modify the return type (See {@link CallbackInfoReturnable}).
     */
    @API(status = API.Status.INTERNAL)
    @SuppressWarnings("PMD.UnusedPrivateMethod")
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
     * Handles whether or not to damage the player.
     *
     * Loops through the the armor the player is wearing and internally calls {@link InvulnerabilityHandler#denyGeneralDamage(ItemStack, DamageSource, float, LivingEntity)} for any armor that implements {@link InvulnerabilityHandler}.
     * <br><br>
     *
     * @param playerEntity the player.
     * @param damageSource the source of damage.
     * @param amount       the amount of damage to inflict on the player.
     * @param info         Used to modify the return type (See {@link CallbackInfoReturnable}).
     */
    @API(status = API.Status.INTERNAL)
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
     * Handles whether or not to damage living entities (other than players).
     *
     * Loops through the the armor the living entity is wearing and internally calls {@link InvulnerabilityHandler#denyGeneralDamage(ItemStack, DamageSource, float, LivingEntity)} for any armor that implements {@link InvulnerabilityHandler}.
     * <br><br>
     *
     * @param livingEntity the living entity
     * @param damageSource the source of damage
     * @param amount       the amount of damage to inflict on the entity
     * @param info         Used to modify the return type (See {@link CallbackInfoReturnable}).
     */
    @API(status = API.Status.INTERNAL)
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
     * Handles whether or not to kill the player.
     *
     * Loops through the the armor the player is wearing and internally calls {@link InvulnerabilityHandler#denyKillCommand(ItemStack, LivingEntity)} for any armor that implements {@link InvulnerabilityHandler}.
     * <br><br>
     *
     * @param playerEntity the player.
     * @param info         Used to modify the return type (See {@link CallbackInfoReturnable}).
     */
    @API(status = API.Status.INTERNAL)
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
     * Handles whether or not to kill the living entity (other than the player).
     *
     * Loops through the the armor the living entity is wearing and internally calls {@link InvulnerabilityHandler#denyKillCommand(ItemStack, LivingEntity)} for any armor that implements {@link InvulnerabilityHandler}.
     * <br><br>
     *
     * @param livingEntity the living entity.
     * @param info         Used to modify the return type (See {@link CallbackInfoReturnable}).
     */
    @API(status = API.Status.INTERNAL)
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