package me.alexisevelyn.randomtech.api.utilities;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import reborncore.client.screen.builder.BuiltScreenHandler;

/**
 * The interface Gui factory.
 */
@Environment(EnvType.CLIENT)
public interface GuiFactory extends ScreenRegistry.Factory<BuiltScreenHandler, HandledScreen<BuiltScreenHandler>> {
    /**
     * Create handled screen.
     *
     * @param syncId       the sync id
     * @param playerEntity the player entity
     * @param blockEntity  the block entity
     * @return the handled screen
     */
    HandledScreen<BuiltScreenHandler> create(int syncId, PlayerEntity playerEntity, BlockEntity blockEntity);

    /**
     * Create handled screen.
     *
     * @param builtScreenHandler the built screen handler
     * @param playerInventory    the player inventory
     * @param text               the text
     * @return the handled screen
     */
    @Override
    default HandledScreen<BuiltScreenHandler> create(BuiltScreenHandler builtScreenHandler, PlayerInventory playerInventory, Text text) {
        PlayerEntity playerEntity = playerInventory.player;

        BlockEntity blockEntity = builtScreenHandler.getBlockEntity();
        return create(builtScreenHandler.syncId, playerEntity, blockEntity);
    }
}
