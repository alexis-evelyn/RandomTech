package me.alexisevelyn.randomtech.guis;

import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.blockentities.BasicComputerBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import reborncore.client.gui.builder.GuiBase;
import reborncore.client.gui.guibuilder.GuiBuilder;
import reborncore.client.screen.builder.BuiltScreenHandler;
import reborncore.common.util.Color;

/**
 * The type Basic computer gui.
 */
@Environment(EnvType.CLIENT)
public class BasicComputerGui extends GuiBase<BuiltScreenHandler> {
    // @SuppressWarnings("FieldCanBeLocal") private final BasicComputerBlockEntity blockEntity;
    public GuiBuilder builder = new GuiBuilder(new Identifier(Main.MODID, "textures/gui/guielements.png"));
    //private TextFieldWidget nameField;

    // Background
    public static final int backgroundCenterX = 176 / 2; // backgroundWidth = 176
    public static final int backgroundCenterY = 166 / 2; // backgroundHeight = 166

    // Slot Info
    public static final int inputSlotX = 18;
    public static final int inputSlotY = 18;

    public static final int outputSlotX = 26;
    public static final int outputSlotY = 26;

    /**
     * Instantiates a new Basic computer gui.
     *
     * @param syncID      the sync id
     * @param player      the player
     * @param blockEntity the block entity
     */
    public BasicComputerGui(int syncID, PlayerEntity player, BasicComputerBlockEntity blockEntity) {
        super(player, blockEntity, blockEntity.createScreenHandler(syncID, player));
        // this.blockEntity = blockEntity;
//        setup();
    }

//    protected void setup() {
//        int i = (this.width - this.backgroundWidth) / 2;
//        int j = (this.height - this.backgroundHeight) / 2;
//
//        this.nameField = new TextFieldWidget(this.textRenderer, i + 62, j + 24, 103, 12, new TranslatableText("container.repair"));
//        this.nameField.setFocusUnlocked(false);
//        this.nameField.setEditableColor(-1);
//        this.nameField.setUneditableColor(-1);
//        this.nameField.setHasBorder(false);
//        this.nameField.setMaxLength(35);
//        // this.nameField.setChangedListener(this::onRenamed);
//        this.children.add(this.nameField);
//        this.setInitialFocus(this.nameField);
//    }

    /**
     * Draw background.
     *
     * @param matrixStack       the matrix stack
     * @param lastFrameDuration the last frame duration
     * @param mouseX            the mouse x
     * @param mouseY            the mouse y
     */
    @Override
    protected void drawBackground(MatrixStack matrixStack, float lastFrameDuration, int mouseX, int mouseY) {
        super.drawBackground(matrixStack, lastFrameDuration, mouseX, mouseY);
        // final Layer layer = Layer.BACKGROUND;
    }

    /**
     * Draw foreground.
     *
     * @param matrixStack the matrix stack
     * @param mouseX      the mouse x
     * @param mouseY      the mouse y
     */
    @Override
    protected void drawForeground(MatrixStack matrixStack, int mouseX, int mouseY) {
        // The ordering of these gui elements is bottom right first to top left last.
        super.drawForeground(matrixStack, mouseX, mouseY);
        // final Layer layer = Layer.FOREGROUND;

        builder.drawText(matrixStack, this, new LiteralText("Not Meant To Be Used Right Now!!!"), mouseX - this.x, mouseY - this.y, Color.RED.getColor());
        //this.nameField.render(matrixStack, this.x, this.y, -1);
    }
}
