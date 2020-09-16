package me.alexisevelyn.randomtech.api.utilities;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import org.apiguardian.api.API;
import reborncore.client.screen.builder.BuiltScreenHandler;

/**
 * The interface Gui factory.
 */
@Environment(EnvType.CLIENT)
public interface GuiFactory extends ScreenRegistry.Factory<BuiltScreenHandler, HandledScreen<BuiltScreenHandler>> {
    /**
     * Override to create a handled screen.
     *
     * Meant to be passed into {@link ScreenRegistry#register(ScreenHandlerType, ScreenRegistry.Factory)}
     *
     * Example:
     * <code>
     * // Register GuiFactory with Fabric
     * if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
     *      ScreenRegistry.register(screenHandlerType, getGuiFactory());
     * </code>
     *
     * Also:
     * <code>
     * @SuppressWarnings("unchecked")
     * private GuiFactory getGuiFactory() {
     *      // Responsible For Allowing The Gui to Be Linked to The Block Entity
     *      return (syncId, playerEntity, blockEntity) -> {
     *          BasicComputerGui basicComputerGui = (BasicComputerGui) new me.alexisevelyn.randomtech.guis.BasicComputerGui(syncId, playerEntity, (BasicComputerBlockEntity) blockEntity);
     *
     *          return (HandledScreen<BuiltScreenHandler>) basicComputerGui;
     *      };
     * }
     * </code>
     *
     * @param syncId       the current screen's syncID (for syncing which screenhandler is open)
     * @param playerEntity the player
     * @param blockEntity  the block's Block Entity
     * @return the handled screen
     */
    @API(status = API.Status.EXPERIMENTAL)
    HandledScreen<BuiltScreenHandler> create(int syncId, PlayerEntity playerEntity, BlockEntity blockEntity);

    /**
     * Override to create a handled screen
     *
     * Unused by me, so good luck!
     *
     * @param builtScreenHandler the built screen handler (used to retrieve syncID and block entity)
     * @param playerInventory    the player inventory (used to retrieve player)
     * @param text               the text (ignored)
     * @return the handled screen
     */
    @API(status = API.Status.EXPERIMENTAL)
    @Override
    default HandledScreen<BuiltScreenHandler> create(BuiltScreenHandler builtScreenHandler, PlayerInventory playerInventory, Text text) {
        PlayerEntity playerEntity = playerInventory.player;

        BlockEntity blockEntity = builtScreenHandler.getBlockEntity();
        return create(builtScreenHandler.syncId, playerEntity, blockEntity);
    }
}
