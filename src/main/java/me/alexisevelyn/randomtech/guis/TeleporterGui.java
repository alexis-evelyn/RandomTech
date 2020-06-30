package me.alexisevelyn.randomtech.guis;

import me.alexisevelyn.randomtech.blockentities.TeleporterBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import reborncore.client.gui.builder.GuiBase;
import reborncore.client.screen.builder.BuiltScreenHandler;

public class TeleporterGui extends GuiBase<BuiltScreenHandler> {
    TeleporterBlockEntity blockEntity;

    public TeleporterGui(PlayerEntity player, TeleporterBlockEntity blockEntity, BuiltScreenHandler screenHandler) {
        super(player, blockEntity, screenHandler);
        this.blockEntity = blockEntity;
    }

    public <T extends BlockEntity> TeleporterGui(int i, PlayerEntity playerEntity, TeleporterBlockEntity blockEntity) {
        super(playerEntity, blockEntity, blockEntity.createScreenHandler(i, playerEntity));
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

        // builder.drawMultiEnergyBar(matrixStack, this, 156, 19, (int) blockEntity.getEnergy(), (int) blockEntity.getMaxPower(), mouseX, mouseY, 0, layer);
        builder.drawMultiEnergyBar(matrixStack, this, 156, 19, 10, 20, mouseX, mouseY, 0, layer);

        builder.drawText(matrixStack, this, new LiteralText("Generating: " + 0 + " E/t"), 10, 30, 0);

    }
}
