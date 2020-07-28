package me.alexisevelyn.randomtech.guis;

import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.blockentities.BasicComputerBlockEntity;
import me.alexisevelyn.randomtech.blockentities.FuserBlockEntity;
import me.shedaniel.rei.server.ContainerInfo;
import me.shedaniel.rei.server.RecipeFinder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import reborncore.client.gui.builder.GuiBase;
import reborncore.client.gui.guibuilder.GuiBuilder;
import reborncore.client.screen.builder.BuiltScreenHandler;
import reborncore.common.util.Color;

@Environment(EnvType.CLIENT)
public class BasicComputerGui extends GuiBase<BuiltScreenHandler> {
    final BasicComputerBlockEntity blockEntity;
    public GuiBuilder builder = new GuiBuilder(new Identifier(Main.MODID, "textures/gui/guielements.png"));

    // Background
    public static final int backgroundCenterX = 176 / 2; // backgroundWidth = 176
    public static final int backgroundCenterY = 166 / 2; // backgroundHeight = 166

    // Slot Info
    public static final int inputSlotX = 18;
    public static final int inputSlotY = 18;

    public static final int outputSlotX = 26;
    public static final int outputSlotY = 26;

    public BasicComputerGui(int syncID, PlayerEntity player, BasicComputerBlockEntity blockEntity) {
        super(player, blockEntity, blockEntity.createScreenHandler(syncID, player));
        this.blockEntity = blockEntity;
    }

    @Override
    protected void drawBackground(MatrixStack matrixStack, float lastFrameDuration, int mouseX, int mouseY) {
        super.drawBackground(matrixStack, lastFrameDuration, mouseX, mouseY);
        final Layer layer = Layer.BACKGROUND;
    }

    @Override
    protected void drawForeground(MatrixStack matrixStack, int mouseX, int mouseY) {
        // The ordering of these gui elements is bottom right first to top left last.
        super.drawForeground(matrixStack, mouseX, mouseY);
        final Layer layer = Layer.FOREGROUND;

        builder.drawText(matrixStack, this, new LiteralText("Not Meant To Be Used Right Now!!!"), mouseX - this.x, mouseY - this.y, Color.RED.getColor());
    }
}
