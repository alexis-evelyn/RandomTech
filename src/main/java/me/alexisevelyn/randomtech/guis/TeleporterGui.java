package me.alexisevelyn.randomtech.guis;

import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.blockentities.TeleporterBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import reborncore.client.gui.builder.GuiBase;
import reborncore.client.gui.guibuilder.GuiBuilder;
import reborncore.client.screen.builder.BuiltScreenHandler;

@Environment(EnvType.CLIENT)
public class TeleporterGui extends GuiBase<BuiltScreenHandler> {
    final TeleporterBlockEntity blockEntity;
    public GuiBuilder builder = new GuiBuilder(new Identifier(Main.MODID, "textures/gui/guielements.png"));

    // Background
//    public static final int backgroundCenterX = 176 / 2; // backgroundWidth = 176
//    public static final int backgroundCenterY = 166 / 2; // backgroundHeight = 166

    // Energy Bar
    public static final int multiEnergyBarX = 9;
    public static final int multiEnergyBarY = 19;

    // Slot Info
//    public static final int inputSlotX = 18;
//    public static final int inputSlotY = 18;

//    public static final int outputSlotX = 26;
//    public static final int outputSlotY = 26;

    // Slots - Give 2-4 Pixels of Room
    public static final int linkerSlotX = 8;
    public static final int linkerSlotY = 72;

    public TeleporterGui(int syncID, PlayerEntity player, TeleporterBlockEntity blockEntity) {
        super(player, blockEntity, blockEntity.createScreenHandler(syncID, player));
        this.blockEntity = blockEntity;
    }

    @Override
    protected void drawBackground(MatrixStack matrixStack, float lastFrameDuration, int mouseX, int mouseY) {
        super.drawBackground(matrixStack, lastFrameDuration, mouseX, mouseY);
        final GuiBase.Layer layer = GuiBase.Layer.BACKGROUND;

        drawSlot(matrixStack, linkerSlotX, linkerSlotY, layer);
    }

    @Override
    protected void drawForeground(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.drawForeground(matrixStack, mouseX, mouseY);
        final GuiBase.Layer layer = GuiBase.Layer.FOREGROUND;

        builder.drawMultiEnergyBar(matrixStack, this, multiEnergyBarX, multiEnergyBarY, (int) blockEntity.getEnergy(), (int) blockEntity.getMaxPower(), mouseX, mouseY, 0, layer);
    }
}
