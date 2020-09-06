package me.alexisevelyn.randomtech.mixin.experimentalscreen;

import com.mojang.serialization.Lifecycle;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * The type Gamerule based backup screen mixin.
 */
@SuppressWarnings("UnusedMixin") // The mixin is used, just is loaded by Fabric and not Sponge methods
@Mixin(LevelProperties.class)
public abstract class GameruleBasedBackupScreenMixin {

    /**
     * Gets game rules.
     *
     * @return the game rules
     */
    @Shadow @Final private Lifecycle lifecycle;
    @Shadow public abstract GameRules getGameRules();

    /**
     * Show backup screen.
     *
     * @param info the info
     */
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    @Inject(at = @At("HEAD"), method = "getLifecycle()Lcom/mojang/serialization/Lifecycle;", cancellable = true)
	private void showBackupScreen(CallbackInfoReturnable<Lifecycle> info) {
		if (getGameRules().getBoolean(RegistryHelper.HIDE_EXPERIMENTAL_SCREEN_GAMERULE) && lifecycle == Lifecycle.experimental()) {
			info.setReturnValue(Lifecycle.stable());
		}
	}
}