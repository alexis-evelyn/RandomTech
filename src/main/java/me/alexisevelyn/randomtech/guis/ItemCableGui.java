package me.alexisevelyn.randomtech.guis;

import com.mojang.blaze3d.systems.RenderSystem;
import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.modmenu.screens.ConfigScreen;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import reborncore.client.gui.guibuilder.GuiBuilder;

import java.util.Optional;

@Environment(EnvType.CLIENT)
public class ItemCableGui extends HandledScreen<ScreenHandler> {
    private final Identifier TEXTURE;

    /**
     *
     * @param handler
     * @param inventory
     * @param title
     */
    public ItemCableGui(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);

        ConfigScreen config = AutoConfig.getConfigHolder(ConfigScreen.class).getConfig();

        Identifier theme;
        switch (config.guiTheme) {
            case ULTRADARK:
                theme = new Identifier("randomtech", "textures/gui/cable/itemcable/ultradark/itemcable_one.png");
                break;
            case DARK:
                theme = new Identifier("randomtech", "textures/gui/cable/itemcable/dark/itemcable_one.png");
                break;
            case VANILLA:
            default:
                theme = new Identifier("randomtech", "textures/gui/cable/itemcable/vanilla/itemcable_one.png");
        }

        TEXTURE = theme;
    }

    /**
     *
     * @param matrices
     * @param delta
     * @param mouseX
     * @param mouseY
     */
    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        if (client == null)
            return;

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        client.getTextureManager().bindTexture(TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);

        super.render(matrices, mouseX, mouseY, delta);

        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();

        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

    /**
     *
     * @param mouseX
     * @param mouseY
     * @return
     */
    @Override
    public Optional<Element> hoveredElement(double mouseX, double mouseY) {
        return super.hoveredElement(mouseX, mouseY);
    }

    /**
     *
     * @param mouseX
     * @param mouseY
     */
    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        super.mouseMoved(mouseX, mouseY);
    }

    /**
     *
     * @param mouseX
     * @param mouseY
     * @param amount
     * @return
     */
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    /**
     *
     * @param keyCode
     * @param scanCode
     * @param modifiers
     * @return
     */
    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    /**
     *
     * @param chr
     * @param keyCode
     * @return
     */
    @Override
    public boolean charTyped(char chr, int keyCode) {
        return super.charTyped(chr, keyCode);
    }

    /**
     *
     * @param element
     */
    @Override
    public void setInitialFocus(Element element) {
        super.setInitialFocus(element);
    }

    /**
     *
     * @param element
     */
    @Override
    public void focusOn(Element element) {
        super.focusOn(element);
    }

    /**
     *
     * @param lookForwards
     * @return
     */
    @Override
    public boolean changeFocus(boolean lookForwards) {
        return super.changeFocus(lookForwards);
    }
}