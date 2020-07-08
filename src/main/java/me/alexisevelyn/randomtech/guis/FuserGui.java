package me.alexisevelyn.randomtech.guis;

import me.alexisevelyn.randomtech.blockentities.FuserBlockEntity;
import me.alexisevelyn.randomtech.blockentities.TeleporterBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import reborncore.client.gui.builder.GuiBase;
import reborncore.client.screen.builder.BuiltScreenHandler;
import reborncore.common.fluid.FluidUtil;
import reborncore.common.util.Color;

@Environment(EnvType.CLIENT)
public class FuserGui extends GuiBase<BuiltScreenHandler> {
    FuserBlockEntity blockEntity;

    public FuserGui(int syncID, PlayerEntity player, FuserBlockEntity blockEntity) {
        super(player, blockEntity, blockEntity.createScreenHandler(syncID, player));
        this.blockEntity = blockEntity;
    }

    @Override
    protected void drawBackground(MatrixStack matrixStack, float lastFrameDuration, int mouseX, int mouseY) {
        super.drawBackground(matrixStack, lastFrameDuration, mouseX, mouseY);
        final Layer layer = Layer.BACKGROUND;

        drawSlot(matrixStack, 8, 72, layer);
        drawSlot(matrixStack, 26, 72, layer);
    }

    @Override
    protected void drawForeground(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.drawForeground(matrixStack, mouseX, mouseY);
        final Layer layer = Layer.FOREGROUND;

        builder.drawMultiEnergyBar(matrixStack, this, 9, 19, (int) blockEntity.getEnergy(), (int) blockEntity.getMaxPower(), mouseX, mouseY, 0, layer);
        builder.drawFluid(matrixStack, this, blockEntity.getFluid(), 25, 19, 16, 50, blockEntity.getMaxFluidLevel().getRawValue());

        builder.drawText(matrixStack, this, new LiteralText("Holding: " + blockEntity.getFluidLevel() + "/" + blockEntity.getMaxFluidLevel() + " of " + FluidUtil.getFluidName(blockEntity.getFluid()).replace("_", " ")), 10, 30, Color.BLUE.getColor());
    }
}
