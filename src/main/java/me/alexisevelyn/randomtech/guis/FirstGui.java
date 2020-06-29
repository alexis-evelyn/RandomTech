package me.alexisevelyn.randomtech.guis;

import me.alexisevelyn.randomtech.blockentities.FirstBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import reborncore.client.gui.builder.GuiBase;
import reborncore.client.screen.builder.BuiltScreenHandler;

public class FirstGui extends GuiBase<BuiltScreenHandler> {
    FirstBlockEntity blockEntity;

    public FirstGui(PlayerEntity player, FirstBlockEntity blockEntity, BuiltScreenHandler screenHandler) {
        super(player, blockEntity, screenHandler);
        this.blockEntity = blockEntity;
    }

    public <T extends BlockEntity> FirstGui(int i, PlayerEntity playerEntity, FirstBlockEntity blockEntity) {
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
