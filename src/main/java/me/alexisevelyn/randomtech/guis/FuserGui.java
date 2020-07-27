package me.alexisevelyn.randomtech.guis;

import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.blockentities.FuserBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import reborncore.client.gui.builder.GuiBase;
import reborncore.client.gui.guibuilder.GuiBuilder;
import reborncore.client.screen.builder.BuiltScreenHandler;

@Environment(EnvType.CLIENT)
public class FuserGui extends GuiBase<BuiltScreenHandler> {
    final FuserBlockEntity blockEntity;
    public GuiBuilder builder = new GuiBuilder(new Identifier(Main.MODID, "textures/gui/guielements.png"));

    // Background
    public static final int backgroundCenterX = 176 / 2; // backgroundWidth = 176
    public static final int backgroundCenterY = 166 / 2; // backgroundHeight = 166

    // The 22 and 56 were pulled from builder.
    // They are hardcoded in builder.
    public static final int tankCenterX = 22 / 2;
    public static final int tankCenterY = 56 / 2;

    // Tank
    public static final int tankX = backgroundCenterX + 2;
    public static final int tankY = 72 - (backgroundCenterY/2); // playerInventoryTitleY = backgroundHeight - 94 = 72

    // Energy Bar
    public static final int multiEnergyBarX = 9;
    public static final int multiEnergyBarY = 19;

    // Slot Info
    public static final int inputSlotX = 18;
    public static final int inputSlotY = 18;

//    public static final int outputSlotX = 26;
//    public static final int outputSlotY = 26;

    // Slots - Give 2-4 Pixels of Room
    public static final int ingredientSlotX = tankX - (inputSlotX * 3);
    public static final int ingredientSlotY = tankY + inputSlotY;

    public static final int byproductSlotX = ingredientSlotX + (inputSlotX + (inputSlotX/2));
    public static final int byproductSlotY = ingredientSlotY + inputSlotY;

    public static final int emptyFluidContainerSlotX = tankX + (tankCenterX * 2) + 4;
    public static final int emptyFluidContainerSlotY = tankY + (tankCenterY - inputSlotY);

    public static final int fullFluidContainerSlotX = emptyFluidContainerSlotX;
    public static final int fullFluidContainerSlotY = emptyFluidContainerSlotY + inputSlotY + 2;

    public FuserGui(int syncID, PlayerEntity player, FuserBlockEntity blockEntity) {
        super(player, blockEntity, blockEntity.createScreenHandler(syncID, player));
        this.blockEntity = blockEntity;
    }

    @Override
    protected void drawBackground(MatrixStack matrixStack, float lastFrameDuration, int mouseX, int mouseY) {
        super.drawBackground(matrixStack, lastFrameDuration, mouseX, mouseY);
        final Layer layer = Layer.BACKGROUND;

        // Item Slots
        drawSlot(matrixStack, ingredientSlotX, ingredientSlotY, layer); // Ingredient Input
        drawSlot(matrixStack, byproductSlotX, byproductSlotY, layer); // Byproduct Output

        // Fluid Slots
        drawSlot(matrixStack, emptyFluidContainerSlotX, emptyFluidContainerSlotY, layer); // Empty Fluid Container (Input)
        drawSlot(matrixStack, fullFluidContainerSlotX, fullFluidContainerSlotY, layer); // Full Fluid Container (Output)
    }

    @Override
    protected void drawForeground(MatrixStack matrixStack, int mouseX, int mouseY) {
        // The ordering of these gui elements is bottom right first to top left last.
        super.drawForeground(matrixStack, mouseX, mouseY);
        final Layer layer = Layer.FOREGROUND;

        // Fluid
        builder.drawTank(matrixStack, this, tankX,tankY, mouseX, mouseY, blockEntity.getFluid(), blockEntity.getMaxFluidLevel(), blockEntity.isEmpty(), layer);

        // Energy Bar
        builder.drawMultiEnergyBar(matrixStack, this, multiEnergyBarX, multiEnergyBarY, (int) blockEntity.getEnergy(), (int) blockEntity.getMaxPower(), mouseX, mouseY, 0, layer);
    }
}
