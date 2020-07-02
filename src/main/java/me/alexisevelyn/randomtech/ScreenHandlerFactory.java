package me.alexisevelyn.randomtech;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.screen.ScreenHandlerType;
import reborncore.client.screen.BuiltScreenHandlerProvider;
import reborncore.client.screen.builder.BuiltScreenHandler;

@Environment(EnvType.CLIENT)
public class ScreenHandlerFactory {
    public ScreenHandlerRegistry.ExtendedClientHandlerFactory<BuiltScreenHandler> getScreenHandlerFactory() {
        return (syncID, playerInventory, packetByteBuf) -> {
            final BlockEntity blockEntity = playerInventory.player.world.getBlockEntity(packetByteBuf.readBlockPos());

            if (blockEntity == null) {
                return null;
            }

            //screenHandler.setType(screenHandlerType);

            return ((BuiltScreenHandlerProvider) blockEntity).createScreenHandler(syncID, playerInventory.player);
        };
    }
}
