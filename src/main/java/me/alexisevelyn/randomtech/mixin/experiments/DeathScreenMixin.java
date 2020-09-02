package me.alexisevelyn.randomtech.mixin.experiments;

import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// The below mentioned method is what shows the death screen
// net.minecraft.client.network.ClientPlayNetworkHandler#onCombatEvent(CombatEventS2CPacket)

/**
 * The type Death screen mixin.
 */
@SuppressWarnings("UnusedMixin") // The mixin is used, just is loaded by Fabric and not Sponge methods
@Mixin(DeathScreen.class)
public abstract class DeathScreenMixin extends Screen {
	@Shadow @Final private Text message;

    /**
     * Gets text component under mouse.
     *
     * @param mouseX the mouse x
     * @return the text component under mouse
     */
    @Shadow protected abstract Style getTextComponentUnderMouse(int mouseX);

    /**
     * Instantiates a new Death screen mixin.
     *
     * @param title the title
     */
    protected DeathScreenMixin(Text title) {
		super(title);
	}

    /**
     * Init.
     *
     * @param info the info
     */
    @Inject(at = @At("HEAD"), method = "init()V", cancellable = true)
	private void init(CallbackInfo info) {
		// Nothing For Now. Commented Out print line to prevent unnecessary messages in the log
		//System.out.println("This line is printed by an example mod mixin!");
	}

    /**
     * Render.
     *
     * @param matrices the matrices
     * @param mouseX   the mouse x
     * @param mouseY   the mouse y
     * @param delta    the delta
     * @param info     the info
     */
    @Inject(at = @At("INVOKE"), method = "render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V", cancellable = true)
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
		// Allow option of showing coordinates on death
		// Make special changes to death screen depending on cause of death

//		this.fillGradient(matrices, 0, 0, this.width, this.height, Color.BLUE.getColor(), 13684735);
//
//		RenderSystem.pushMatrix();
//		RenderSystem.scalef(2.0F, 2.0F, 2.0F);
//		this.drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2 / 2, 30, 16777215);
//		RenderSystem.popMatrix();
//		if (this.message != null) {
//			this.drawCenteredText(matrices, this.textRenderer, this.message, this.width / 2, 85, 16777215);
//		}
//
//		this.drawCenteredString(matrices, this.textRenderer, I18n.translate("deathScreen.score") + ": " + Formatting.YELLOW + this.client.player.getScore(), this.width / 2, 100, 16777215);
//
//		if (this.message != null && mouseY > 85) {
//			if (mouseY < 85 + 9) {
//				Style style = this.getTextComponentUnderMouse(mouseX);
//				this.renderTextHoverEffect(matrices, style, mouseX, mouseY);
//			}
//		}
//
//		super.render(matrices, mouseX, mouseY, delta);
//
//		info.cancel();
	}
}