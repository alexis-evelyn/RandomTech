package me.alexisevelyn.randomtech.guis;

import me.alexisevelyn.randomtech.blockentities.TeleporterBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import reborncore.client.gui.builder.GuiBase;
import reborncore.client.screen.builder.BuiltScreenHandler;

@Environment(EnvType.CLIENT)
public class TeleporterGui extends GuiBase<BuiltScreenHandler> {
    TeleporterBlockEntity blockEntity;

    public TeleporterGui(int syncID, PlayerEntity player, TeleporterBlockEntity blockEntity) {
        super(player, blockEntity, blockEntity.createScreenHandler(syncID, player));
        this.blockEntity = blockEntity;
    }

    @Override
    protected void drawBackground(MatrixStack matrixStack, float lastFrameDuration, int mouseX, int mouseY) {
        super.drawBackground(matrixStack, lastFrameDuration, mouseX, mouseY);
        final GuiBase.Layer layer = GuiBase.Layer.BACKGROUND;
    }

    @Override
    protected void drawForeground(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.drawForeground(matrixStack, mouseX, mouseY);
        final GuiBase.Layer layer = GuiBase.Layer.FOREGROUND;

        builder.drawMultiEnergyBar(matrixStack, this, 156, 19, (int) blockEntity.getEnergy(), (int) blockEntity.getMaxPower(), mouseX, mouseY, 0, layer);

        builder.drawText(matrixStack, this, new LiteralText("Generating: " + blockEntity.getPowerChange() + " E/t"), 10, 30, 0);
    }
}
