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

@Environment(EnvType.CLIENT)
public interface GuiFactory extends ScreenRegistry.Factory<BuiltScreenHandler, HandledScreen<BuiltScreenHandler>> {
    HandledScreen<BuiltScreenHandler> create(int syncId, PlayerEntity playerEntity, BlockEntity blockEntity);

    @Override
    default HandledScreen<BuiltScreenHandler> create(BuiltScreenHandler builtScreenHandler, PlayerInventory playerInventory, Text text) {
        PlayerEntity playerEntity = playerInventory.player;

        BlockEntity blockEntity = builtScreenHandler.getBlockEntity();
        return create(builtScreenHandler.syncId, playerEntity, blockEntity);
    }
}
