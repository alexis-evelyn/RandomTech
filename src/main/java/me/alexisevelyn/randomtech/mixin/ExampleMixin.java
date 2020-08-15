package me.alexisevelyn.randomtech.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * The type Example mixin.
 */
@SuppressWarnings({"UnusedMixin", "EmptyMethod"}) // The mixin is used, just is loaded by Fabric and not Sponge methods
@Mixin(TitleScreen.class)
public class ExampleMixin {
    /**
     * Init.
     *
     * @param info the info
     */
    @Inject(at = @At("HEAD"), method = "init()V")
	private void init(CallbackInfo info) {
		// Nothing For Now. Commented Out print line to prevent unnecessary messages in the log
		//System.out.println("This line is printed by an example mod mixin!");
	}
}