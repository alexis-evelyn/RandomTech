package me.alexisevelyn.randomtech.mixin.experimentalscreen;

import com.mojang.serialization.Lifecycle;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.world.GameRules;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// For some reason @Redirect is not generating a refMap (wheras @Inject does) - https://github.com/BoogieMonster1O1/mixincs-updated/blob/master/redirect.md
// Helpful Info? - https://github.com/FabricMC/fabric-loom/issues/100

/**
 * The type Gamerule based backup screen mixin.
 */
@SuppressWarnings("UnusedMixin") // The mixin is used, just is loaded by Fabric and not Sponge methods
@Mixin(MinecraftClient.class)
public abstract class ExperimentalPromptMessageMixin {

    /**
     * Gets game rules.
     *
     * @return the game rules
     */
//    @Shadow public abstract GameRules getGameRules();
//    @Shadow @Final private Lifecycle lifecycle;

    /**
     * Show backup screen.
     *
     * @param info the info
     */
    // @Inject(at = @At("HEAD"), method = "method_29601(Lnet/minecraft/client/MinecraftClient$WorldLoadAction;Ljava/lang/String;ZLjava/lang/Runnable;)V", cancellable = true)
//    @Redirect(method = "openScreen(Lnet/minecraft/client/gui/screen/Screen;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;method_29601(Lnet/minecraft/client/MinecraftClient$WorldLoadAction;Ljava/lang/String;ZLjava/lang/Runnable;)V"))
//    private void showBackupScreen(MinecraftClient self, MinecraftClient.WorldLoadAction worldLoadAction, String worldFolderName, boolean customized, Runnable startServerRunnable, CallbackInfo info) {
//        System.out.println("Hello World!!!");
//    }
}